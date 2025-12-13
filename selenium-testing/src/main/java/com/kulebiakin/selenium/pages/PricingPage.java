package com.kulebiakin.selenium.pages;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

@Slf4j
public class PricingPage extends BasePage {

    private static final String PRICING_PAGE_URL = "https://www.inmotionhosting.com/pricing";

    @FindBy(xpath = "//button[contains(., 'VPS Hosting')]")
    private WebElement vpsHostingTab;

    @FindBy(xpath = "//button[contains(., 'Dedicated Servers')]")
    private WebElement dedicatedServersTab;

    @FindBy(xpath = "//button[contains(., 'Hosting for WordPress')]")
    private WebElement wordpressHostingTab;

    @FindBy(xpath = "//button[contains(., 'Shared Hosting')]")
    private WebElement sharedHostingTab;

    @FindBy(xpath = "//button[contains(., 'Reseller Hosting')]")
    private WebElement resellerHostingTab;

    @FindBy(xpath = "//button[contains(text(), 'Accept All Cookies')]")
    private WebElement acceptCookiesButton;

    @FindBy(xpath = "//h1[contains(., 'Pricing')]")
    private WebElement pageHeading;

    @FindBy(xpath = "//h2[contains(., 'Compare All Hosting Plans')]")
    private WebElement compareAllPlansHeading;

    @FindBy(xpath = "//table")
    private List<WebElement> pricingTables;

    @FindBy(xpath = "//table//a[contains(text(), 'Select')]")
    private List<WebElement> selectButtons;

    @FindBy(xpath = "//a[contains(@href, 'central.inmotionhosting.com/amp/checkout')]")
    private WebElement shoppingCartLink;

    public PricingPage(WebDriver driver) {
        super(driver);
    }

    @Step("Open pricing page")
    public PricingPage open() {
        log.info("Opening pricing page: {}", PRICING_PAGE_URL);
        driver.get(PRICING_PAGE_URL);
        waitForPageLoad();
        handleCookieConsent();
        waitForPageHeading();
        log.info("Pricing page loaded successfully");
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

    private void waitForPageHeading() {
        waitForVisibility(pageHeading);
    }

    @Step("Select VPS Hosting tab")
    public PricingPage selectVpsHostingTab() {
        log.info("Selecting VPS Hosting tab");
        click(vpsHostingTab);
        waitForTableWithText("VPS");
        return this;
    }

    @Step("Select Dedicated Servers tab")
    public PricingPage selectDedicatedServersTab() {
        log.info("Selecting Dedicated Servers tab");
        click(dedicatedServersTab);
        waitForTableWithText("Aspire");
        return this;
    }

    @Step("Select WordPress Hosting tab")
    public PricingPage selectWordPressHostingTab() {
        log.info("Selecting WordPress Hosting tab");
        click(wordpressHostingTab);
        waitForTableWithText("WP Core");
        return this;
    }

    @Step("Select Shared Hosting tab")
    public PricingPage selectSharedHostingTab() {
        log.info("Selecting Shared Hosting tab");
        click(sharedHostingTab);
        waitForTableWithText("Core");
        return this;
    }

    @Step("Select Reseller Hosting tab")
    public PricingPage selectResellerHostingTab() {
        log.info("Selecting Reseller Hosting tab");
        click(resellerHostingTab);
        waitForTableWithText("R-1000N");
        return this;
    }

    @Step("Check if page heading is displayed")
    public boolean isPageHeadingDisplayed() {
        return isDisplayed(pageHeading);
    }

    @Step("Get all hosting tab names")
    public List<String> getHostingTabNames() {
        List<WebElement> tabs = List.of(
            vpsHostingTab, dedicatedServersTab, wordpressHostingTab,
            sharedHostingTab, resellerHostingTab
        );
        return tabs.stream()
            .filter(this::isDisplayed)
            .map(WebElement::getText)
            .map(text -> text.split("\n")[0]) // Get first line (tab name)
            .toList();
    }

    @Step("Get pricing table count")
    public int getPricingTableCount() {
        return (int) pricingTables.stream()
            .filter(WebElement::isDisplayed)
            .count();
    }

    @Step("Get Select button count")
    public int getSelectButtonCount() {
        return (int) selectButtons.stream()
            .filter(WebElement::isDisplayed)
            .count();
    }

    @Step("Check if plan '{planName}' is visible")
    public boolean isPlanVisible(String planName) {
        try {
            List<WebElement> elements = driver.findElements(
                By.xpath("//table//*[contains(text(), '" + planName + "')]")
            );
            return elements.stream().anyMatch(WebElement::isDisplayed);
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Get visible plan names")
    public List<String> getVisiblePlanNames() {
        List<WebElement> headers = driver.findElements(By.xpath("//table//th"));
        return headers.stream()
            .filter(WebElement::isDisplayed)
            .map(WebElement::getText)
            .filter(text -> !text.isEmpty() && !text.equals("Primary Features"))
            .map(text -> text.split("\n")[0]) // Get plan name (first line)
            .toList();
    }

    @Step("Get first visible price")
    public String getFirstVisiblePrice() {
        try {
            // Try multiple selectors for price elements
            List<WebElement> priceElements = driver.findElements(
                By.xpath("//table//th//*[contains(text(), '$')]")
            );
            for (WebElement el : priceElements) {
                if (el.isDisplayed()) {
                    String text = el.getText();
                    if (text.contains("$") && text.contains("/mo")) {
                        return text;
                    }
                }
            }
            // Fallback: look for any price-like text
            priceElements = driver.findElements(By.xpath("//*[contains(text(), '/mo')]"));
            for (WebElement el : priceElements) {
                if (el.isDisplayed()) {
                    String text = el.getText();
                    if (text.contains("$")) {
                        return text;
                    }
                }
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }

    @Step("Get first Select button href")
    public String getFirstSelectButtonHref() {
        return selectButtons.stream()
            .filter(WebElement::isDisplayed)
            .findFirst()
            .map(btn -> btn.getDomProperty("href"))
            .orElse("");
    }

    @Step("Scroll to Compare All Plans section")
    public PricingPage scrollToCompareAllPlans() {
        scrollToElement(compareAllPlansHeading);
        waitForVisibility(compareAllPlansHeading);
        return this;
    }

    private void waitForTableWithText(String text) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//table//*[contains(text(), '" + text + "')]")
        ));
    }

    @Step("Get shopping cart href")
    public String getShoppingCartHref() {
        List<WebElement> cartLinks = driver.findElements(
            By.xpath("//a[contains(@href, 'central.inmotionhosting.com/amp/checkout')]")
        );
        return cartLinks.stream()
            .filter(WebElement::isDisplayed)
            .findFirst()
            .map(el -> el.getDomProperty("href"))
            .orElse("");
    }

    @Step("Click first Select button and wait for checkout")
    public void clickFirstSelectButtonAndWaitForCheckout() {
        log.info("Clicking first Select button to navigate to checkout");
        var firstButton = selectButtons.stream()
            .filter(WebElement::isDisplayed)
            .findFirst()
            .orElseThrow(() -> new RuntimeException("No Select button found"));
        click(firstButton);
        waitForUrlContains("checkout");
        log.info("Navigated to checkout page");
    }
}
