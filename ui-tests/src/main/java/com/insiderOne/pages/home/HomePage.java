package com.insiderOne.pages.home;

import com.insiderOne.base.BasePage;
import com.insiderOne.base.Driver;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class HomePage extends BasePage {


    // ================= HEADER =================

    @FindBy(css = "nav, [id='navigation']")
    private WebElement navigationBar;

    @FindBy(css = "main h1")
    private WebElement heroTitle;


// ================= TRUST & SOCIAL =================

    @FindBy(css = ".homepage-logo-reel-row")
    private WebElement logoReelSection;

    @FindBy(css = ".homepage-social-proof-marquee-item")
    private WebElement socialProofSection;


// ================= CORE CONTENT =================

    @FindBy(css = ".homepage-core-differentiators-wrapper")
    private WebElement coreDifferentiatorsSection;

    @FindBy(css = ".homepage-capabilities-main")
    private WebElement capabilitiesSection;

    @FindBy(css = ".homepage-insider-one-ai-wrapper")
    private WebElement siriusAiSection;

    @FindBy(css = ".homepage-channels-bg")
    private WebElement channelsSection;


// ================= CASE STUDY & ANALYST =================

    @FindBy(css = ".homepage-case-study-head")
    private WebElement caseStudySection;

    @FindBy(css = ".homepage-analyst-main")
    private WebElement analystSection;


// ================= INTEGRATIONS & RESOURCES =================

    @FindBy(css = ".homepage-integrations-content")
    private WebElement integrationsSection;

    @FindBy(css = ".homepage-resources-wrapper")
    private WebElement resourcesSection;


// ================= FOOTER =================

    @FindBy(css = ".footer-main")
    private WebElement footerSection;




    public HomePage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @Step("Verify all required sections are displayed on the Home Page")
    public HomePage verifyHomePageSections() {
        verifyElementDisplayed(navigationBar, "Navigation");
        verifyElementDisplayed(heroTitle, "Desktop Hero");
        verifyElementDisplayed(logoReelSection, "Home Logo Reel");
        verifyElementDisplayed(socialProofSection, "Social Proof Marquee Item");
        verifyElementDisplayed(coreDifferentiatorsSection, "Image Background");
        verifyElementDisplayed(capabilitiesSection, "Capabilities Main");
        verifyElementDisplayed(siriusAiSection, "Sirius AI");
        verifyElementDisplayed(channelsSection, "Tabbed Content Home");
        verifyElementDisplayed(caseStudySection, "Case Study Head");
        verifyElementDisplayed(analystSection, "Home Analyse");
        verifyElementDisplayed(integrationsSection, "Home Integrations Content");
        verifyElementDisplayed(resourcesSection, "Home Resources Wrapper");
        verifyElementDisplayed(footerSection, "Footer");
        return this;
    }
}
