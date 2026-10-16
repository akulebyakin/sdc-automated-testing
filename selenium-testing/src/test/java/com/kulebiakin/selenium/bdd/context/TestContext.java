package com.kulebiakin.selenium.bdd.context;

import com.kulebiakin.selenium.pages.PricingPage;
import com.kulebiakin.selenium.pages.VpsPage;
import org.openqa.selenium.WebDriver;

public class TestContext {

    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<PricingPage> pricingPageThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<VpsPage> vpsPageThreadLocal = new ThreadLocal<>();

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

    public static VpsPage getVpsPage() {
        return vpsPageThreadLocal.get();
    }

    public static void setVpsPage(VpsPage vpsPage) {
        vpsPageThreadLocal.set(vpsPage);
    }

    public static void clear() {
        driverThreadLocal.remove();
        pricingPageThreadLocal.remove();
        vpsPageThreadLocal.remove();
    }
}
