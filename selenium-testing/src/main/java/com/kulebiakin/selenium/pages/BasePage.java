package com.kulebiakin.selenium.pages;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Objects;
import java.util.Set;

@Slf4j
public abstract class BasePage {

    private static final Duration DEFAULT_WAIT_TIMEOUT = Duration.ofSeconds(10);
    private static final Duration POLL_INTERVAL = Duration.ofMillis(500);

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, DEFAULT_WAIT_TIMEOUT, POLL_INTERVAL);
        PageFactory.initElements(driver, this);
        log.debug("Initialized page: {}", getClass().getSimpleName());
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    protected WebElement waitForVisibility(WebElement element) {
        log.debug("Waiting for element visibility");
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected WebElement waitForClickable(WebElement element) {
        log.debug("Waiting for element to be clickable");
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected void waitForUrlContains(String urlPart) {
        log.debug("Waiting for URL to contain: {}", urlPart);
        wait.until(ExpectedConditions.urlContains(urlPart));
    }

    protected void click(WebElement element) {
        log.debug("Clicking element");
        scrollToElement(element);
        waitForClickable(element).click();
    }

    protected void jsClick(WebElement element) {
        log.debug("JavaScript clicking element");
        scrollToElement(element);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    protected void scrollToElement(WebElement element) {
        log.debug("Scrolling to element");
        ((JavascriptExecutor) driver).executeScript(
            "arguments[0].scrollIntoView({behavior: 'instant', block: 'center'});",
            element
        );
    }

    protected boolean isDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Custom wait for element visibility with timeout (for example for cookies consent banners)
    protected boolean waitForElementVisible(WebElement element, int timeoutSeconds) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                .until(ExpectedConditions.visibilityOf(element));
            return true;
        } catch (Exception e) {
            // Don't fail, just return false
            return false;
        }
    }

    protected void waitForPageLoad() {
        log.debug("Waiting for page to fully load");
        wait.until(webDriver ->
            Objects.equals(((JavascriptExecutor) webDriver).executeScript("return document.readyState"),
                "complete")
        );
    }

    protected void hoverOver(WebElement element) {
        log.debug("Hovering over element");
        Actions actions = new Actions(driver);
        actions.moveToElement(waitForVisibility(element)).perform();
    }

    protected String getWindowHandle() {
        return driver.getWindowHandle();
    }

    protected void switchToNewTab(String originalHandle) {
        log.debug("Switching to new tab");
        wait.until(d -> d.getWindowHandles().size() > 1);
        Set<String> handles = driver.getWindowHandles();
        for (String handle : handles) {
            if (!handle.equals(originalHandle)) {
                driver.switchTo().window(handle);
                waitForPageLoad();
                break;
            }
        }
    }

    protected void closeCurrentTabAndSwitchTo(String targetHandle) {
        log.debug("Closing current tab and switching to target handle");
        driver.close();
        driver.switchTo().window(targetHandle);
    }

    protected void handleAlert() {
        log.debug("Attempting to handle alert");
        try {
            WebDriverWait alertWait = new WebDriverWait(driver, Duration.ofSeconds(3));
            alertWait.until(ExpectedConditions.alertIsPresent());
            driver.switchTo().alert().accept();
            log.debug("Alert accepted");
        } catch (TimeoutException e) {
            log.debug("No alert present");
        }
    }
}
