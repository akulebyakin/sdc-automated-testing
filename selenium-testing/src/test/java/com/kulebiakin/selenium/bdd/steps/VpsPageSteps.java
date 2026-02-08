package com.kulebiakin.selenium.bdd.steps;

import com.kulebiakin.selenium.bdd.context.TestContext;
import com.kulebiakin.selenium.pages.VpsPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Allure;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class VpsPageSteps {

    @Given("the user is on the VPS hosting page")
    public void theUserIsOnTheVpsHostingPage() {
        WebDriver driver = TestContext.getDriver();
        VpsPage vpsPage = new VpsPage(driver);
        vpsPage.open();
        TestContext.setVpsPage(vpsPage);
        Allure.step("Opened InMotion Hosting VPS page");
    }

    @When("I scroll to the language selector")
    public void iScrollToTheLanguageSelector() {
        VpsPage vpsPage = TestContext.getVpsPage();
        vpsPage.scrollToLanguageSelector();
        Allure.step("Scrolled to language selector");
    }

    @When("I hover over the language selector")
    public void iHoverOverTheLanguageSelector() {
        VpsPage vpsPage = TestContext.getVpsPage();
        vpsPage.hoverOverLanguageSelector();
        Allure.step("Hovered over language selector");
    }

    @And("I switch the language to Deutsch")
    public void iSwitchTheLanguageToDeutsch() {
        VpsPage vpsPage = TestContext.getVpsPage();
        vpsPage.switchToGerman();
        Allure.step("Switched language to Deutsch");
    }

    @And("I switch the language to English")
    public void iSwitchTheLanguageToEnglish() {
        VpsPage vpsPage = TestContext.getVpsPage();
        vpsPage.switchToEnglish();
        Allure.step("Switched language to English");
    }

    @Then("the VPS page URL should change to {string}")
    public void theVpsPageUrlShouldChangeTo(String expectedUrl) {
        VpsPage vpsPage = TestContext.getVpsPage();
        assertThat(vpsPage.getCurrentUrl())
            .as("VPS page URL should be %s".formatted(expectedUrl))
            .isEqualTo(expectedUrl);
        Allure.step("Verified VPS page URL is: %s".formatted(expectedUrl));
    }

    @Then("the VPS page URL should contain {string}")
    public void theVpsPageUrlShouldContain(String expectedUrlPart) {
        VpsPage vpsPage = TestContext.getVpsPage();
        assertThat(vpsPage.getCurrentUrl())
            .as("VPS page URL should contain %s".formatted(expectedUrlPart))
            .contains(expectedUrlPart);
        Allure.step("Verified VPS page URL contains: %s".formatted(expectedUrlPart));
    }

    @Then("the hero title should display {string}")
    public void theHeroTitleShouldDisplay(String expectedTitle) {
        VpsPage vpsPage = TestContext.getVpsPage();
        String actualTitle = vpsPage.getHeroTitleText();
        assertThat(actualTitle)
            .as("Hero title should be %s".formatted(expectedTitle))
            .isEqualTo(expectedTitle);
        Allure.step("Verified hero title: %s".formatted(actualTitle));
    }

    @Then("the hero title should contain {string}")
    public void theHeroTitleShouldContain(String expectedText) {
        VpsPage vpsPage = TestContext.getVpsPage();
        String actualTitle = vpsPage.getHeroTitleText();
        assertThat(actualTitle)
            .as("Hero title should contain %s".formatted(expectedText))
            .contains(expectedText);
        Allure.step("Verified hero title contains: %s".formatted(expectedText));
    }

    @When("I click on the Contact Us chat button")
    public void iClickOnTheContactUsChatButton() {
        VpsPage vpsPage = TestContext.getVpsPage();
        vpsPage.storeOriginalWindowHandle();
        vpsPage.clickChatButton();
        Allure.step("Clicked Contact Us chat button");
    }

    @Then("a new browser tab should open with the chat page")
    public void aNewBrowserTabShouldOpenWithTheChatPage() {
        VpsPage vpsPage = TestContext.getVpsPage();
        vpsPage.switchToChatTab();
        Allure.step("Switched to chat tab");
    }

    @And("the chat page title should contain {string}")
    public void theChatPageTitleShouldContain(String expectedTitle) {
        VpsPage vpsPage = TestContext.getVpsPage();
        assertThat(vpsPage.isChatPageTitleCorrect(expectedTitle))
            .as("Chat page title should contain: %s".formatted(expectedTitle))
            .isTrue();
        Allure.step("Verified chat page title contains: %s".formatted(expectedTitle));
    }

    @And("the chat page URL should contain {string}")
    public void theChatPageUrlShouldContain(String expectedUrlPart) {
        VpsPage vpsPage = TestContext.getVpsPage();
        assertThat(vpsPage.isChatPageUrlCorrect(expectedUrlPart))
            .as("Chat page URL should contain: %s".formatted(expectedUrlPart))
            .isTrue();
        Allure.step("Verified chat page URL contains: %s".formatted(expectedUrlPart));
    }

    @And("the customer name input should be visible and enabled")
    public void theCustomerNameInputShouldBeVisibleAndEnabled() {
        VpsPage vpsPage = TestContext.getVpsPage();
        assertThat(vpsPage.isCustomerNameInputDisplayed())
            .as("Customer name input should be visible and enabled")
            .isTrue();
        Allure.step("Verified customer name input is visible and enabled");
    }

    @And("the customer email input should be visible and enabled")
    public void theCustomerEmailInputShouldBeVisibleAndEnabled() {
        VpsPage vpsPage = TestContext.getVpsPage();
        assertThat(vpsPage.isCustomerEmailInputDisplayed())
            .as("Customer email input should be visible and enabled")
            .isTrue();
        Allure.step("Verified customer email input is visible and enabled");
    }

    @And("the sales chat submit button should be visible and enabled")
    public void theSalesChatSubmitButtonShouldBeVisibleAndEnabled() {
        VpsPage vpsPage = TestContext.getVpsPage();
        assertThat(vpsPage.isSalesChatSubmitButtonDisplayed())
            .as("Sales chat submit button should be visible and enabled")
            .isTrue();
        Allure.step("Verified sales chat submit button is visible and enabled");
    }

    @When("I close the chat tab and switch back to VPS page")
    public void iCloseTheChatTabAndSwitchBackToVpsPage() {
        VpsPage vpsPage = TestContext.getVpsPage();
        vpsPage.closeChatTabAndSwitchBack();
        Allure.step("Closed chat tab and switched back to VPS page");
    }

    @Then("the VPS page should still be open")
    public void theVpsPageShouldStillBeOpen() {
        VpsPage vpsPage = TestContext.getVpsPage();
        assertThat(vpsPage.isVpsPageOpen())
            .as("VPS page should still be open")
            .isTrue();
        Allure.step("Verified VPS page is still open");
    }
}
