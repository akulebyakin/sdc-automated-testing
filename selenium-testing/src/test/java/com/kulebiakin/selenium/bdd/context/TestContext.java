package com.kulebiakin.selenium.bdd.context;

import com.kulebiakin.selenium.pages.PricingPage;
import org.openqa.selenium.WebDriver;

public class TestContext {

    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<PricingPage> pricingPageThreadLocal = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    public static void setDriver(WebDriver driver) {
        driverThreadLocal.set(driver);
    }

    public static PricingPage getPricingPage() {
        return pricingPageThreadLocal.get();
    }

    public static void setPricingPage(PricingPage pricingPage) {
        pricingPageThreadLocal.set(pricingPage);
    }

    public static void clear() {
        driverThreadLocal.remove();
        pricingPageThreadLocal.remove();
    }
}
