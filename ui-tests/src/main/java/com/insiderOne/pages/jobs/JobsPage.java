package com.insiderOne.pages.jobs;

import com.insiderOne.base.BasePage;
import com.insiderOne.base.Driver;
import com.insiderOne.utilities.Log;
import io.qameta.allure.Step;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class JobsPage extends BasePage {

    // ================= JOB LIST NAVIGATION =================

    @FindBy(xpath = "//a[normalize-space()='See all QA jobs']")
    WebElement seeAllQaJobsButton;

    // ================= FILTER AREA =================

    @FindBy(id = "filter-by-location")
    WebElement locationFilterContainer;

    @FindBy(id = "filter-by-department")
     WebElement departmentFilterContainer;

    @FindBy(css = "option.job-location.istanbulturkiye")
    List<WebElement> filterByContainerOption;

    @FindBy(css = "option.job-team.qualityassurance")
    //@FindBy(css = "option[selected='selected']")
    List<WebElement> jobDepartmentList;

    @FindBy(css = "*[class*='position-location']")
    List<WebElement> jobLocationList;

    @FindBy(id = "jobs-list")
    WebElement jobsListContainer;

    @FindBy(css = "#jobs-list p")
    WebElement noJobsFoundMessage;

    @FindBy(css = "#jobs-list a")
    List<WebElement> viewRoleButton;

    public JobsPage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @Step("Verify redirected URL contains: {url}")
    public JobsPage redirectUrlControl(String url) {
        redirectControl(url);
        return this;
    }

    public JobsPage navigateToAllJobs() {
        clickElement(seeAllQaJobsButton, "All Jobs button clicked.");
        return this;
    }

    public JobsPage selectLocation(String location) {
        return selectFilterOption(locationFilterContainer, filterByContainerOption, location, "Location");
    }

    public JobsPage selectDepartment(String department) {
        return selectFilterOption(departmentFilterContainer, jobDepartmentList, department, "Department");
    }


    @Step("Select filter option '{value}' from '{filterName}' dropdown")
    public JobsPage selectFilterOption(WebElement filterDropdown, List<WebElement> options, String value, String filterName) {

        waitMs(3000);
        clickElement(filterDropdown, "Click on " + filterName + " filter dropdown");

        options.stream()
                .filter(option -> {
                    scrollToElementBlockCenter(option);
                    return getTextOfElement(option).trim().equals(value);
                })
                .findFirst()
                .ifPresentOrElse(
                        option -> clickElement(option, "Select " + filterName + ": " + value),
                        () -> Log.fail("Option '" + value + "' not found in " + filterName + " filter!")
                );

        return this;
    }


    @Step("Verify that the jobs list is displayed")
    public JobsPage verifyJobsListAppears() {

        try {
            scrollToElementBlockCenter(jobsListContainer);
            Assert.assertTrue(jobsListContainer.isDisplayed(), "Jobs list is NOT displayed!");
            Log.pass("Jobs list is displayed.");
        } catch (Exception e) {
            Log.fail("Jobs list container not found!");
        }
        return this;
    }

    @Step("Verify that all job cards match location '{location}' and department '{department}'")
    public JobsPage verifyJobCardsMatchFilters(String location, String department) {

        if (jobDepartmentList.isEmpty() || jobLocationList.isEmpty()) {
            Assert.fail("No job cards found to verify!");
        }
        verifyListValues(jobDepartmentList, department, "Job department");
        verifyListValues(jobLocationList, location, "Job location");
        return this;
    }

    private void verifyListValues(List<WebElement> elements, String expectedValue, String fieldName) {
        for (WebElement element : elements) {
            var actualText = getTextOfElement(element).trim();
            Assert.assertEquals(actualText, expectedValue, fieldName + " mismatch! Expected: " + expectedValue + ", Found: " + actualText);
            Log.pass(fieldName + " matches: " + actualText);
        }
    }

    @Step("Verify 'No positions available.' message visibility")
    public JobsPage verifyNoPositionsAvailableMessage() {

        try {
            Assert.assertTrue(noJobsFoundMessage.isDisplayed(), "'No positions available.' element is NOT displayed!");
            var msg = getTextOfElement(noJobsFoundMessage).trim();
            Assert.assertEquals(msg, "No positions available.", "Message is displayed but text is incorrect!");
            Log.pass("'No positions available.' message is displayed correctly.");
        } catch (Exception e) {
            Assert.fail("'No positions available.' message element NOT FOUND!", e);
        }
        return this;
    }


    @Step("Click all 'View Role' buttons and open each job in a new tab")
    public JobsPage clickViewRoleButtonsInLoop() {

        for (int i = 0; i < viewRoleButton.size(); i++) {
            var btn = viewRoleButton.get(i);
            hoverElement(btn, 1);
            clickElement(btn, "Click on view role button: index " + i);
            waitMs(500);
            switchToNewWindow();
            waitForUrlContains("jobs.lever.co/insiderone", 3);
            closeAllTabsExceptMain();
        }

        return this;
    }



}
