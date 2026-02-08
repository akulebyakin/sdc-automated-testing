package com.kulebiakin.selenium.pages;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

@Slf4j
public class VpsPage extends BasePage {

    private static final String VPS_PAGE_URL = "https://www.inmotionhosting.com/vps-hosting";

    @FindBy(xpath = "//span[@class='wglanguage-name']")
    private WebElement languageSelectorCheckbox;

    @FindBy(xpath = "//a[contains(@class, 'weglot-language-de')]")
    private WebElement germanLanguageOption;

    @FindBy(xpath = "//a[contains(@class, 'weglot-language-en')]")
    private WebElement englishLanguageOption;

    @FindBy(xpath = "//h2[contains(@class, 'hero-title')]")
    private WebElement heroTitle;

    @FindBy(xpath = "//a[contains(@class, 'ppb-button') and contains(@class, 'btn-primary-chat') and contains(@class, 'chat-btn-popup')]")
    private WebElement chatButton;

    @FindBy(xpath = "//button[contains(text(), 'Accept All Cookies')]")
    private WebElement acceptCookiesButton;

    @FindBy(xpath = "//input[@id='customer-name']")
    private WebElement customerNameInput;

    @FindBy(xpath = "//input[@id='customer-email']")
    private WebElement customerEmailInput;

    @FindBy(xpath = "//button[@id='sales-chat-submit' and @type='submit']")
    private WebElement salesChatSubmitButton;

    private String originalWindowHandle;

    public VpsPage(WebDriver driver) {
        super(driver);
    }

    @Step("Open VPS hosting page")
    public VpsPage open() {
        log.info("Opening VPS page: {}", VPS_PAGE_URL);
        driver.get(VPS_PAGE_URL);
        waitForPageLoad();
        handleCookieConsent();
        log.info("VPS page loaded successfully");
        return this;
    }

    private void handleCookieConsent() {
        try {
            if (waitForElementVisible(acceptCookiesButton, 5)) {
                log.debug("Accepting cookie consent");
                jsClick(acceptCookiesButton);
                wait.until(ExpectedConditions.invisibilityOf(acceptCookiesButton));
            }
        } catch (Exception e) {
            log.debug("Cookie banner not present or already dismissed");
        }
    }

    @Step("Scroll to language selector")
    public VpsPage scrollToLanguageSelector() {
        log.info("Scrolling to language selector checkbox");
        scrollToElement(languageSelectorCheckbox);
        return this;
    }

    @Step("Hover over language selector")
    public VpsPage hoverOverLanguageSelector() {
        log.info("Hovering over language selector checkbox");
        scrollToLanguageSelector();
        hoverOver(languageSelectorCheckbox);
        return this;
    }

    @Step("Switch language to Deutsch")
    public VpsPage switchToGerman() {
        log.info("Switching language to Deutsch");
        hoverOverLanguageSelector();
        click(germanLanguageOption);
        waitForUrlContains("/de/vps-hosting");
        waitForPageLoad();
        log.info("Language switched to Deutsch");
        return this;
    }

    @Step("Switch language to English")
    public VpsPage switchToEnglish() {
        log.info("Switching language to English");
        hoverOverLanguageSelector();
        click(englishLanguageOption);
        waitForPageLoad();
        log.info("Language switched to English");
        return this;
    }

    @Step("Get hero title text")
    public String getHeroTitleText() {
        waitForVisibility(heroTitle);
        return heroTitle.getText();
    }

    @Step("Store original window handle")
    public VpsPage storeOriginalWindowHandle() {
        originalWindowHandle = getWindowHandle();
        log.debug("Stored original window handle: {}", originalWindowHandle);
        return this;
    }

    @Step("Click chat button")
    public VpsPage clickChatButton() {
        log.info("Clicking chat button");
        scrollToElement(chatButton);
        click(chatButton);
        return this;
    }

    @Step("Switch to chat tab")
    public VpsPage switchToChatTab() {
        log.info("Switching to chat tab");
        switchToNewTab(originalWindowHandle);
        return this;
    }

    @Step("Verify chat page title contains expected text")
    public boolean isChatPageTitleCorrect(String expectedTitle) {
        String title = getPageTitle();
        log.debug("Chat page title: {}", title);
        return title.contains(expectedTitle);
    }

    @Step("Verify chat page URL contains expected text")
    public boolean isChatPageUrlCorrect(String expectedUrlPart) {
        String url = getCurrentUrl();
        log.debug("Chat page URL: {}", url);
        return url.contains(expectedUrlPart);
    }

    @Step("Check if customer name input is visible and enabled")
    public boolean isCustomerNameInputDisplayed() {
        try {
            waitForVisibility(customerNameInput);
            return customerNameInput.isDisplayed() && customerNameInput.isEnabled();
        } catch (NoSuchElementException | TimeoutException e) {
            log.debug("Customer name input not found: {}", e.getMessage());
            return false;
        }
    }

    @Step("Check if customer email input is visible and enabled")
    public boolean isCustomerEmailInputDisplayed() {
        try {
            waitForVisibility(customerEmailInput);
            return customerEmailInput.isDisplayed() && customerEmailInput.isEnabled();
        } catch (NoSuchElementException | TimeoutException e) {
            log.debug("Customer email input not found: {}", e.getMessage());
            return false;
        }
    }

    @Step("Check if sales chat submit button is visible and enabled")
    public boolean isSalesChatSubmitButtonDisplayed() {
        try {
            waitForVisibility(salesChatSubmitButton);
            return salesChatSubmitButton.isDisplayed() && salesChatSubmitButton.isEnabled();
        } catch (NoSuchElementException | TimeoutException e) {
            log.debug("Sales chat submit button not found: {}", e.getMessage());
            return false;
        }
    }

    @Step("Close chat tab and switch back to VPS page")
    public VpsPage closeChatTabAndSwitchBack() {
        log.info("Closing chat tab and switching back to VPS page");
        closeCurrentTabAndSwitchTo(originalWindowHandle);
        handleAlert();
        waitForPageLoad();
        return this;
    }

    @Step("Verify VPS page is still open")
    public boolean isVpsPageOpen() {
        String url = getCurrentUrl();
        return url.contains("vps-hosting");
    }

    @Step("Get VPS page title")
    public String getVpsPageTitle() {
        return getPageTitle();
    }
}
