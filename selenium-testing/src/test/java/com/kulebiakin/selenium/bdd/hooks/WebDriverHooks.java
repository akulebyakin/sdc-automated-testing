package com.kulebiakin.selenium.bdd.hooks;

import com.kulebiakin.selenium.bdd.context.TestContext;
import com.kulebiakin.selenium.config.WebDriverConfig;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.ByteArrayInputStream;

@Slf4j
public class WebDriverHooks {

    private static final boolean HEADLESS = Boolean.parseBoolean(
        System.getProperty("headless", "true")
    );

    private static final String BROWSER = System.getProperty("browser", "CHROME");

    @Before
    public void setupWebDriver(Scenario scenario) {
        log.info("Setting up WebDriver for scenario: {}", scenario.getName());

        WebDriverConfig.Browser browser = WebDriverConfig.Browser.valueOf(BROWSER.toUpperCase());
        WebDriver driver = WebDriverConfig.createDriver(browser, HEADLESS);

        TestContext.setDriver(driver);

        Allure.step("WebDriver initialized: %s (headless: %s)".formatted(browser, HEADLESS));
    }

    @After
    public void tearDownWebDriver(Scenario scenario) {
        WebDriver driver = TestContext.getDriver();

        try {
            if (scenario.isFailed() && driver != null) {
                captureScreenshot(scenario);
                capturePageSource(scenario);
            }
        } finally {
            if (driver != null) {
                log.info("Closing WebDriver for scenario: {}", scenario.getName());
                WebDriverConfig.quitDriver(driver);
                TestContext.clear();
            }
        }
    }

    private void captureScreenshot(Scenario scenario) {
        try {
            WebDriver driver = TestContext.getDriver();
            if (driver instanceof TakesScreenshot) {
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

                Allure.addAttachment(
                    "Screenshot - %s".formatted(scenario.getName()),
                    "image/png",
                    new ByteArrayInputStream(screenshot),
                    "png"
                );

                scenario.attach(screenshot, "image/png", "Screenshot");

                log.info("Screenshot captured for failed scenario: {}", scenario.getName());
            }
        } catch (Exception e) {
            log.warn("Failed to capture screenshot: {}", e.getMessage());
        }
    }

    private void capturePageSource(Scenario scenario) {
        try {
            WebDriver driver = TestContext.getDriver();
            if (driver != null) {
                String pageSource = driver.getPageSource();

                Allure.addAttachment(
                    "Page Source - %s".formatted(scenario.getName()),
                    "text/html",
                    pageSource
                );

                log.info("Page source captured for scenario: {}", scenario.getName());
            }
        } catch (Exception e) {
            log.warn("Failed to capture page source: {}", e.getMessage());
        }
    }
}
