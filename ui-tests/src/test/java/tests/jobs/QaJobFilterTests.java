package tests.jobs;

import com.insiderOne.base.BaseTest;
import com.insiderOne.pages.jobs.JobsPage;
import com.insiderOne.utilities.PageInit;

import io.qameta.allure.Description;
import org.testng.annotations.Test;


public class QaJobFilterTests extends BaseTest {

    @Description("Navigate to insiderone.com QA jobs page, click 'See all QA jobs', filter by Location Istanbul Turkey and Department Quality Assurance, verify that the job cards displayed match the selected filters.")
    @Test(testName = "Check the QA filtering by location and department - Istanbul Turkey - InsiderOne")
    public void checkTheJobFiltering_Case_2() {

        var departmentUri = "department=qualityassurance";
        var location = "Istanbul, Turkiye";
        var department = "Quality Assurance";

        navigateToUrl("prod", "careers/quality-assurance/");

        PageInit.get(JobsPage.class)
                .navigateToAllJobs()
                .redirectUrlControl(departmentUri)
                .selectLocation(location)
                .selectDepartment(department)
                .verifyJobsListAppears()
                .verifyJobCardsMatchFilters(location, department);
    }



}
