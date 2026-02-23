package tests.homepage;

import com.insiderOne.base.BaseTest;
import com.insiderOne.pages.home.HomePage;
import com.insiderOne.utilities.PageInit;
import io.qameta.allure.Description;
import org.testng.annotations.Test;

public class HomePageTests extends BaseTest {

    @Description("Check the Home Page items showing correctly.")
    @Test(testName = "Check the Home Page items showing correctly - InsiderOne")
    public void checkTheHomePage_Case_1() {
        navigateToUrl();

        PageInit.get(HomePage.class)
                .verifyHomePageSections();
    }

}
