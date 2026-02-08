package com.kulebiakin.selenium.bdd.steps;

import com.kulebiakin.selenium.bdd.context.TestContext;
import com.kulebiakin.selenium.pages.PricingPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Allure;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class NavigationSteps {

    @Given("the user is on the InMotion Hosting pricing page")
    public void theUserIsOnThePricingPage() {
        WebDriver driver = TestContext.getDriver();
        PricingPage pricingPage = new PricingPage(driver);
        pricingPage.open();
        TestContext.setPricingPage(pricingPage);

        Allure.step("Opened InMotion Hosting pricing page");
    }

    @When("I click on each hosting category tab sequentially")
    public void iClickOnEachHostingCategoryTabSequentially() {
        PricingPage pricingPage = TestContext.getPricingPage();
        pricingPage.selectVpsHostingTab();
        pricingPage.selectDedicatedServersTab();
        pricingPage.selectWordPressHostingTab();
        pricingPage.selectSharedHostingTab();
        pricingPage.selectResellerHostingTab();
    }

    @Then("each tab should successfully display its pricing content")
    public void eachTabShouldSuccessfullyDisplayItsPricingContent() {
        PricingPage pricingPage = TestContext.getPricingPage();

        int tableCount = pricingPage.getPricingTableCount();
        assertThat(tableCount)
            .as("Pricing tables should be visible after clicking tabs")
            .isGreaterThan(0);

        Allure.step("Verified %d pricing tables are displayed".formatted(tableCount));
    }
}
