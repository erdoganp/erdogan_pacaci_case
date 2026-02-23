package com.insiderOne.base;

import com.insiderOne.utilities.Log;
import com.insiderOne.utilities.UtilityMethod;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.time.Duration;

import static org.testng.Assert.assertEquals;


public class BasePage extends UtilityMethod {


    /**
     * @param env : choose environment
     * @param url : choose url for environment (example: NoAdditionalQueues) or full url (example: /empty)
     */
    public void navigateToUrl(String env, String url) {

        var baseUrl = Environment.getBaseUrl(env);
        Assert.assertNotNull(baseUrl, "Base URL is null! Check environment value: " + env);

        if (url == null) {
            url = "";
        }

        if (url.startsWith("/")) {
            url = url.substring(1);
        }

        var fullUrl = baseUrl + "/" + url;

        try {
            Driver.getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(40));
            Driver.getDriver().get(fullUrl);
            Log.pass("Application launched! URL: " + fullUrl);
            waitForPageToLoad(15);
            waitMs(2000);
            acceptCookies();
            waitMs(1500);
        } catch (Exception e) {
            Log.fail("Navigation failed: " + e.getMessage());
        }
    }

    @Step("Navigate to URL")
    public void navigateToUrl() {
        var env = config.env();
        String baseUrl;
        try {
            baseUrl = config.baseUrl();
            if (baseUrl == null || baseUrl.isEmpty()) {
                Log.warning("Invalid or missing base URL for environment: " + env);
                return;
            }
            Driver.getDriver().get(baseUrl);
            Driver.getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(40));
            Log.pass("Application launched! URL: " + baseUrl);
            waitForPageToLoad(15);
            waitMs(2000);
            acceptCookies();
            waitMs(1500);
        } catch (Exception e) {
            Log.error("Error navigating to URL: " + e.getMessage());
        }
    }

    public class Environment {

        public static String getBaseUrl(String env) {
            if (env == null || env.isEmpty()) {
                Log.warning("Environment cannot be null or empty!");
                return null;
            }

            return switch (env.toLowerCase()) {
                case "prod" -> "https://insiderone.com";
                case "preprod" -> "https://preprod.insiderone.com";
                case "cloud" -> "https://cloud.insiderone.com";
                case "test" -> "https://test.insiderone.com";
                default -> {
                    Log.warning("Invalid environment provided: " + env);
                    yield null;
                }
            };
        }
    }


    /**
     * This method is used to accept cookies.
     * If it is not displayed, it will print a message on the console.
     * If it is displayed, it will click on the 'Accept All Cookies' button.
     */
    @Step("Accept browser cookies.")
    public void acceptCookies() {
        try {
            var acceptCookiesButton = Driver.getDriver().findElement(By.cssSelector("#cookie-law-info-bar #wt-cli-accept-all-btn"));
            if (isDisplayElement(acceptCookiesButton)) {
                Log.pass("Accept Cookies button found.");
                clickWithJS(acceptCookiesButton, "Accept all browser cookies!");
                waitMs(1000);
            }
        } catch (org.openqa.selenium.NoSuchElementException e) {
            Log.pass("Cookie banner not displayed (may already be accepted or not present).");
        }
    }

    /**
     * @param webElement : get Images size like : 203x203
     */
    public String checkElementSize(WebElement webElement) {
        WebElement element = waitVisibleByLocator(webElement);
        return (element.getSize().getHeight() + "x" + element.getSize().getWidth());

    }

    @Step("Check Web Element Size")
    public String checkWebElementSize(WebElement webElement) {
        return (webElement.getSize().getHeight() + "x" + webElement.getSize().getWidth());
    }

    public void verifyElementDisplayed(WebElement element, String elementName) {
        try {
            scrollToElementBlockCenter(element);
            boolean isDisplayed = isDisplayElement(element);
            if (isDisplayed) {
                Log.pass(elementName + " is displayed.");
            } else {
                Log.fail(elementName + " is NOT displayed.");
                Assert.fail(elementName + " is NOT displayed.");
            }
        } catch (Exception e) {
            Log.fail(elementName + " is NOT found on the page. Exception: " + e.getMessage());
            Assert.fail(elementName + " is NOT found on the page.");
        }
    }

    @Step("Verify redirected URL contains: {url}")
    public void redirectControl(String url) {
        waitForUrlContains(url, 3);
        Log.pass("Redirected URL: " + url);
    }


}
