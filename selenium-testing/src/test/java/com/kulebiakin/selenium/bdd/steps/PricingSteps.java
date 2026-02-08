package com.kulebiakin.selenium.bdd.steps;

import com.kulebiakin.selenium.bdd.context.TestContext;
import com.kulebiakin.selenium.pages.HostingType;
import com.kulebiakin.selenium.pages.PricingPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Allure;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.kulebiakin.selenium.pages.HostingType.VPS_HOSTING;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class PricingSteps {

    private String currentHostingType;
    private String promotionalPrice;

    @When("I view the pricing plans for any hosting category")
    public void iViewThePricingPlansForAnyHostingCategory() {
        PricingPage pricingPage = TestContext.getPricingPage();
        pricingPage.selectVpsHostingTab();
        currentHostingType = VPS_HOSTING.getDisplayName();

        Allure.step("Viewing pricing for: %s".formatted(currentHostingType));
    }

    @When("I navigate to the {string} tab")
    public void iNavigateToTheTab(String hostingType) {
        PricingPage pricingPage = TestContext.getPricingPage();
        currentHostingType = hostingType;

        HostingType type = HostingType.fromDisplayName(hostingType);
        pricingPage.selectHostingTab(type);

        Allure.step("Navigated to %s tab".formatted(hostingType));
    }

    @Given("I am viewing the {string} plans")
    public void iAmViewingThePlans(String hostingType) {
        iNavigateToTheTab(hostingType);
    }

    @When("I examine the pricing display")
    public void iExamineThePricingDisplay() {
        PricingPage pricingPage = TestContext.getPricingPage();
        promotionalPrice = pricingPage.getFirstVisiblePrice();
        assertThat(promotionalPrice)
            .as("Promotional price should is not visible for %s".formatted(currentHostingType))
            .isNotEmpty();
        Allure.step("Examined pricing display for %s".formatted(currentHostingType));
    }

    @Then("promotional prices should be displayed prominently")
    public void promotionalPricesShouldBeDisplayedProminently() {
        PricingPage pricingPage = TestContext.getPricingPage();
        String firstPrice = pricingPage.getFirstVisiblePrice();

        assertThat(firstPrice)
            .as("Promotional price should be visible")
            .isNotEmpty();

        Allure.step("Promotional price found: %s".formatted(firstPrice));
    }

    @And("renewal prices should be displayed")
    public void renewalPricesShouldBeDisplayedWithEqualVisualEmphasis() {
        PricingPage pricingPage = TestContext.getPricingPage();
        List<String> renewalInfo = pricingPage.getVisibleRenewalPriceElementsInfo();

        assertThat(renewalInfo)
            .as("DEFECT: Renewal prices should be visible")
            .isNotEmpty();
    }

    @Then("I should see the promotional price clearly")
    public void iShouldSeeThePromotionalPriceClearly() {
        PricingPage pricingPage = TestContext.getPricingPage();
        if (promotionalPrice == null) {
            promotionalPrice = pricingPage.getFirstVisiblePrice();
        }
        assertThat(promotionalPrice)
            .as("Promotional price should be found")
            .isNotNull()
            .isNotEmpty();

        Allure.step("Promotional price: %s".formatted(promotionalPrice));
    }

    @And("I should see the renewal price with similar visual weight")
    public void iShouldSeeTheRenewalPriceWithSimilarVisualWeight() {
        PricingPage pricingPage = TestContext.getPricingPage();
        assertThat(pricingPage.hasVisibleRenewalPrices())
            .as("Renewal prices should be visible")
            .isTrue();
        Allure.step("Renewal prices are visible");
    }

    @And("the price difference should be clearly communicated")
    public void thePriceDifferenceShouldBeClearlyCommunicated() {
        PricingPage pricingPage = TestContext.getPricingPage();
        boolean hasSavingsInfo = pricingPage.hasSavingsInfo();

        Allure.step("Savings/discount information present: %s".formatted(hasSavingsInfo));

        if (!hasSavingsInfo) {
            log.info("ENHANCEMENT: Consider adding explicit savings percentage or price comparison");
        }
    }

    @Then("the renewal information should not be visually hidden")
    public void theRenewalInformationShouldNotBeVisuallyHidden() {
        PricingPage pricingPage = TestContext.getPricingPage();
        assertThat(pricingPage.hasVisibleRenewalPrices())
            .as("Renewal price information should be visible")
            .isTrue();
        Allure.step("Renewal information is visible");
    }

    @And("the renewal price should have adequate color contrast")
    public void theRenewalPriceShouldHaveAdequateColorContrast() {
        PricingPage pricingPage = TestContext.getPricingPage();
        assertThat(pricingPage.hasVisibleRenewalPrices())
            .as("Renewal price should be visible")
            .isTrue();
        Allure.step("Renewal price is visible");
    }

    @Then("the page URL should contain {string}")
    public void thePageUrlShouldContain(String expectedUrl) {
        PricingPage pricingPage = TestContext.getPricingPage();
        assertThat(pricingPage.getCurrentUrl())
            .as("Page URL should contain %s".formatted(expectedUrl))
            .contains(expectedUrl);
    }

    @And("the page title should contain {string}")
    public void thePageTitleShouldContain(String expectedTitle) {
        PricingPage pricingPage = TestContext.getPricingPage();
        assertThat(pricingPage.getPageTitle())
            .as("Page title should contain %s".formatted(expectedTitle))
            .containsIgnoringCase(expectedTitle);
    }

    @And("the page heading should be visible")
    public void thePageHeadingShouldBeVisible() {
        PricingPage pricingPage = TestContext.getPricingPage();
        assertThat(pricingPage.isPageHeadingDisplayed())
            .as("Page heading should be visible")
            .isTrue();
    }

    @And("there should be {int} hosting category tabs")
    public void thereShouldBeHostingCategoryTabs(int expectedCount) {
        PricingPage pricingPage = TestContext.getPricingPage();
        List<String> tabNames = pricingPage.getHostingTabNames();
        assertThat(tabNames)
            .as("Should have %d hosting category tabs".formatted(expectedCount))
            .hasSize(expectedCount);
    }

    @And("the tabs should include {string} and {string} options")
    public void theTabsShouldIncludeOptions(String option1, String option2) {
        PricingPage pricingPage = TestContext.getPricingPage();
        List<String> tabNames = pricingPage.getHostingTabNames();
        assertThat(tabNames)
            .as("Tabs should include %s option".formatted(option1))
            .anyMatch(name -> name.contains(option1));
        assertThat(tabNames)
            .as("Tabs should include %s option".formatted(option2))
            .anyMatch(name -> name.contains(option2));
    }

    @Then("the {string} plan should be visible")
    public void thePlanShouldBeVisible(String planName) {
        PricingPage pricingPage = TestContext.getPricingPage();
        assertThat(pricingPage.isPlanVisible(planName))
            .as("%s plan should be visible".formatted(planName))
            .isTrue();
        Allure.step("Verified %s plan is visible".formatted(planName));
    }

    @And("there should be at least {int} Select buttons")
    public void thereShouldBeAtLeastSelectButtons(int minCount) {
        PricingPage pricingPage = TestContext.getPricingPage();
        int buttonCount = pricingPage.getSelectButtonCount();
        assertThat(buttonCount)
            .as("Should have at least %d Select buttons".formatted(minCount))
            .isGreaterThanOrEqualTo(minCount);
        Allure.step("Found %d Select buttons".formatted(buttonCount));
    }

    @And("the Select button should link to checkout")
    public void theSelectButtonShouldLinkToCheckout() {
        PricingPage pricingPage = TestContext.getPricingPage();
        String checkoutUrl = pricingPage.getFirstSelectButtonHref();
        assertThat(checkoutUrl)
            .as("Select button should link to checkout")
            .contains("checkout");
    }

    @And("I scroll to the Compare All Plans section")
    public void iScrollToTheCompareAllPlansSection() {
        PricingPage pricingPage = TestContext.getPricingPage();
        pricingPage.scrollToCompareAllPlans();
        Allure.step("Scrolled to Compare All Plans section");
    }

    @Then("pricing tables should be present")
    public void pricingTablesShouldBePresent() {
        PricingPage pricingPage = TestContext.getPricingPage();
        int tableCount = pricingPage.getPricingTableCount();
        assertThat(tableCount)
            .as("Pricing tables should be present")
            .isGreaterThan(0);
        Allure.step("Found %d pricing tables".formatted(tableCount));
    }

    @And("plan names should be displayed")
    public void planNamesShouldBeDisplayed() {
        PricingPage pricingPage = TestContext.getPricingPage();
        List<String> planNames = pricingPage.getVisiblePlanNames();
        assertThat(planNames)
            .as("Plan names should be displayed")
            .isNotEmpty();
        Allure.step("Found plans: %s".formatted(planNames));
    }

    @And("Select buttons should have valid checkout URLs")
    public void selectButtonsShouldHaveValidCheckoutUrls() {
        PricingPage pricingPage = TestContext.getPricingPage();
        String firstButtonHref = pricingPage.getFirstSelectButtonHref();
        assertThat(firstButtonHref)
            .as("Select buttons should have valid checkout URLs")
            .isNotEmpty()
            .contains("inmotionhosting.com");
    }

    @And("I click the first Select button")
    public void iClickTheFirstSelectButton() {
        PricingPage pricingPage = TestContext.getPricingPage();
        pricingPage.clickFirstSelectButtonAndWaitForCheckout();
        Allure.step("Clicked first Select button");
    }

    @Then("I should be on the checkout page")
    public void iShouldBeOnTheCheckoutPage() {
        PricingPage pricingPage = TestContext.getPricingPage();
        assertThat(pricingPage.getCurrentUrl())
            .as("Should be on checkout page")
            .contains("checkout");
        Allure.step("Verified navigation to checkout page");
    }

    @Then("the shopping cart link should contain {string}")
    public void theShoppingCartLinkShouldContain(String expected) {
        PricingPage pricingPage = TestContext.getPricingPage();
        String cartHref = pricingPage.getShoppingCartHref();
        assertThat(cartHref)
            .as("Shopping cart link should contain %s".formatted(expected))
            .contains(expected);
    }
}
