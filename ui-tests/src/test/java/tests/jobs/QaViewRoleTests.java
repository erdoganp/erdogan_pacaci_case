package tests.jobs;

import com.insiderOne.base.BaseTest;
import com.insiderOne.pages.jobs.JobsPage;
import com.insiderOne.utilities.PageInit;
import io.qameta.allure.Description;
import org.testng.annotations.Test;


public class QaViewRoleTests extends BaseTest {

    @Description("check The View Role buttons by filtering and clicking the buttons in loop")
    @Test(testName = "check the view role buttons by filtering and clicking the buttons in loop - InsiderOne")
    public void checkQaJobFilteringViewRoleActions_Case_3() {

        var departmentUri = "department=qualityassurance";
        var location = "Istanbul, Turkiye";
        var department = "Quality Assurance";

        navigateToUrl("prod", "careers/quality-assurance/");

        PageInit.get(JobsPage.class)
                .navigateToAllJobs()
                .redirectUrlControl(departmentUri)
                .selectLocation(location)
                .selectDepartment(department)
                .clickViewRoleButtonsInLoop();
    }
}
