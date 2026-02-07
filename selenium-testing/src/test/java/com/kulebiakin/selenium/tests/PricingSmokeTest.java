package com.kulebiakin.selenium.tests;

import com.kulebiakin.selenium.config.WebDriverConfig;
import com.kulebiakin.selenium.pages.PricingPage;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("InMotion Hosting Pricing Page Tests")
@Epic("InMotion Hosting")
@Feature("Pricing Page")
class PricingSmokeTest {

    private WebDriver driver;
    private PricingPage pricingPage;

    @BeforeEach
    void navigateToPricingPage() {
        driver = WebDriverConfig.createDriver(WebDriverConfig.Browser.CHROME, true);
        pricingPage = new PricingPage(driver);
        pricingPage.open();
    }

    @AfterEach
    void tearDown() {
        WebDriverConfig.quitDriver(driver);
    }

    @Test
    @DisplayName("Verify pricing page loads with heading and tabs")
    @Story("Page Loading")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verifies that the pricing page loads correctly with all expected elements")
    void verifyPageLoadsCorrectly() {
        // Verify page loaded with correct URL
        assertThat(pricingPage.getCurrentUrl())
            .as("Should navigate to pricing page")
            .contains("inmotionhosting.com/pricing");

        // Verify page title
        assertThat(pricingPage.getPageTitle())
            .as("Page title should mention pricing")
            .containsIgnoringCase("Pricing");

        // Verify main heading is visible
        assertThat(pricingPage.isPageHeadingDisplayed())
            .as("Page heading should be visible")
            .isTrue();

        // Verify hosting tabs are available
        List<String> tabNames = pricingPage.getHostingTabNames();
        assertThat(tabNames)
            .as("Should have 5 hosting category tabs")
            .hasSize(5)
            .as("Tabs should include VPS and Dedicated options")
            .anyMatch(name -> name.contains("VPS"))
            .anyMatch(name -> name.contains("Dedicated"));
    }

    @Test
    @DisplayName("Verify VPS hosting plans are displayed correctly")
    @Story("VPS Hosting")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verifies VPS hosting plans display with correct pricing and purchase options")
    void verifyVpsHostingPlans() {
        // Step 1: Click on VPS Hosting tab
        pricingPage.selectVpsHostingTab();

        // Step 2: Verify VPS plans are displayed
        assertThat(pricingPage.isPlanVisible("VPS 4 vCPU"))
            .as("VPS 4 vCPU plan should be visible")
            .isTrue();

        assertThat(pricingPage.isPlanVisible("VPS 8 vCPU"))
            .as("VPS 8 vCPU plan should be visible")
            .isTrue();

        // Step 3: Verify pricing is displayed
        String price = pricingPage.getFirstVisiblePrice();
        assertThat(price)
            .as("VPS pricing should be visible with dollar amount")
            .contains("$")
            .contains("/mo");

        // Step 4: Verify Select buttons are available for purchasing
        int selectButtonCount = pricingPage.getSelectButtonCount();
        assertThat(selectButtonCount)
            .as("Should have Select buttons for VPS plans")
            .isGreaterThanOrEqualTo(4);

        // Step 5: Verify checkout link is valid
        String checkoutUrl = pricingPage.getFirstSelectButtonHref();
        assertThat(checkoutUrl)
            .as("Select button should link to checkout")
            .contains("checkout");
    }

    @Test
    @DisplayName("Verify Dedicated Server plans are displayed correctly")
    void verifyDedicatedServerPlans() {
        // Step 1: Navigate to Dedicated Servers
        pricingPage.selectDedicatedServersTab();

        // Step 2: Verify dedicated server plans are shown
        assertThat(pricingPage.isPlanVisible("Aspire"))
            .as("Aspire plan should be visible")
            .isTrue();

        assertThat(pricingPage.isPlanVisible("Essential"))
            .as("Essential plan should be visible")
            .isTrue();

        // Step 3: Verify multiple plans are available
        List<String> planNames = pricingPage.getVisiblePlanNames();
        assertThat(planNames)
            .as("Should have at least 5 dedicated server plans")
            .hasSizeGreaterThanOrEqualTo(5);
    }

    @Test
    @DisplayName("Verify WordPress hosting plans are displayed correctly")
    void verifyWordPressHostingPlans() {
        // Step 1: Navigate to WordPress Hosting
        pricingPage.selectWordPressHostingTab();

        // Step 2: Verify WordPress plans are shown
        assertThat(pricingPage.isPlanVisible("WP Core"))
            .as("WP Core plan should be visible")
            .isTrue();

        assertThat(pricingPage.isPlanVisible("WP Pro"))
            .as("WP Pro plan should be visible")
            .isTrue();

        // Step 3: Verify pricing and checkout available
        assertThat(pricingPage.getSelectButtonCount())
            .as("Should have Select buttons for WordPress plans")
            .isGreaterThanOrEqualTo(4);
    }

    @Test
    @DisplayName("Verify navigation between all hosting categories")
    @Story("Tab Navigation")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verifies navigation between all hosting category tabs works correctly")
    void verifyCrossCategoryNavigation() {
        // Navigate through all tabs and verify each loads correctly

        // VPS Hosting
        pricingPage.selectVpsHostingTab();
        assertThat(pricingPage.isPlanVisible("VPS"))
            .as("VPS section should be visible after clicking VPS tab")
            .isTrue();

        // Dedicated Servers
        pricingPage.selectDedicatedServersTab();
        assertThat(pricingPage.isPlanVisible("Aspire"))
            .as("Dedicated section should be visible after clicking Dedicated tab")
            .isTrue();

        // WordPress Hosting
        pricingPage.selectWordPressHostingTab();
        assertThat(pricingPage.isPlanVisible("WP Core"))
            .as("WordPress section should be visible after clicking WordPress tab")
            .isTrue();

        // Shared Hosting
        pricingPage.selectSharedHostingTab();
        assertThat(pricingPage.isPlanVisible("Core"))
            .as("Shared section should be visible after clicking Shared tab")
            .isTrue();

        // Reseller Hosting
        pricingPage.selectResellerHostingTab();
        assertThat(pricingPage.isPlanVisible("R-1000N"))
            .as("Reseller section should be visible after clicking Reseller tab")
            .isTrue();
    }

    @Test
    @DisplayName("Verify pricing tables have proper structure")
    void verifyPricingTableStructure() {
        // Navigate to VPS and scroll to comparison table
        pricingPage.selectVpsHostingTab();
        pricingPage.scrollToCompareAllPlans();

        // Verify tables are present
        int tableCount = pricingPage.getPricingTableCount();
        assertThat(tableCount)
            .as("Page should have pricing comparison tables")
            .isGreaterThan(0);

        // Verify plans have pricing information
        List<String> planNames = pricingPage.getVisiblePlanNames();
        assertThat(planNames)
            .as("Table should display plan names")
            .isNotEmpty();

        // Verify Select buttons are actionable
        String firstButtonHref = pricingPage.getFirstSelectButtonHref();
        assertThat(firstButtonHref)
            .as("Select buttons should have valid checkout URLs")
            .isNotEmpty()
            .contains("inmotionhosting.com");
    }

    @Test
    @DisplayName("Verify Shared hosting plans are displayed correctly")
    void verifySharedHostingPlans() {
        // Step 1: Navigate to Shared Hosting
        pricingPage.selectSharedHostingTab();

        // Step 2: Verify Shared hosting plans are displayed
        // Note: Shared hosting uses simple names like "Core", "Launch", "Power", "Pro"
        assertThat(pricingPage.isPlanVisible("Core"))
            .as("Core plan should be visible")
            .isTrue();

        // Step 3: Verify Select buttons are available for purchasing
        int selectButtonCount = pricingPage.getSelectButtonCount();
        assertThat(selectButtonCount)
            .as("Should have Select buttons for Shared plans")
            .isGreaterThanOrEqualTo(4);

        // Step 4: Verify checkout link is valid
        String checkoutUrl = pricingPage.getFirstSelectButtonHref();
        assertThat(checkoutUrl)
            .as("Shared hosting Select button should link to checkout")
            .contains("checkout");
    }

    @Test
    @DisplayName("Verify Reseller hosting plans are displayed correctly")
    void verifyResellerHostingPlans() {
        // Step 1: Navigate to Reseller Hosting
        pricingPage.selectResellerHostingTab();

        // Step 2: Verify Reseller plans are displayed (R-1000N, R-2000N, etc.)
        assertThat(pricingPage.isPlanVisible("R-1000N"))
            .as("R-1000N plan should be visible")
            .isTrue();

        assertThat(pricingPage.isPlanVisible("R-2000N"))
            .as("R-2000N plan should be visible")
            .isTrue();

        // Step 3: Verify checkout is available
        String checkoutUrl = pricingPage.getFirstSelectButtonHref();
        assertThat(checkoutUrl)
            .as("Reseller Select button should link to checkout")
            .contains("checkout");
    }

    @Test
    @DisplayName("Verify selecting product leads to checkout page")
    @Story("Checkout Flow")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verifies that clicking Select button navigates to checkout page")
    void verifySelectProductNavigatesToCheckout() {
        // Navigate to VPS Hosting tab
        pricingPage.selectVpsHostingTab();

        // Click the first Select button
        pricingPage.clickFirstSelectButtonAndWaitForCheckout();

        // Verify navigation to checkout page
        assertThat(pricingPage.getCurrentUrl())
            .as("Should navigate to checkout page after selecting product")
            .contains("checkout");
    }

    @Test
    @DisplayName("Verify shopping cart link points to checkout page")
    @Story("Checkout Flow")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifies that shopping cart link contains valid checkout URL")
    void verifyShoppingCartLinksToCheckout() {
        // Verify shopping cart href points to checkout
        String cartHref = pricingPage.getShoppingCartHref();
        assertThat(cartHref)
            .as("Shopping cart link should point to checkout")
            .contains("checkout")
            .contains("inmotionhosting.com");
    }
}
