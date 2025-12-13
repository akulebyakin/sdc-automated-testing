# Selenium UI Testing Module

Selenium WebDriver test framework using **Page Object + Factory pattern** for smoke testing the InMotion Hosting pricing
page.

## Project Structure

```
selenium-testing/
├── pom.xml
├── README.md
└── src/
    ├── main/java/com/kulebiakin/selenium/
    │   ├── config/
    │   │   └── WebDriverConfig.java      # WebDriver setup and configuration
    │   └── pages/
    │       ├── BasePage.java             # Abstract base page with explicit waits
    │       └── PricingPage.java          # Pricing page object with @FindBy and @Step annotations
    └── test/
        ├── java/com/kulebiakin/selenium/tests/
        │   └── PricingSmokeTest.java     # Smoke tests with Allure annotations
        └── resources/
            ├── junit-platform.properties # Parallel test execution config
            ├── logging.properties        # JUL config (suppress CDP warnings)
            └── simplelogger.properties   # SLF4J logging config
```

## Technologies

- **Selenium WebDriver 4.27.0** - Browser automation
- **WebDriverManager** - Automatic driver management
- **JUnit 5** - Testing framework with parallel execution
- **AssertJ** - Fluent assertions
- **Allure 2.25.0** - Test reporting
- **SLF4J + Lombok @Slf4j** - Logging

## Test Categories

| Test                                   | Description                                   |
|----------------------------------------|-----------------------------------------------|
| verifyPageLoadsCorrectly               | Verify page loads with heading and all 5 tabs |
| verifyVpsHostingPlans                  | Verify VPS plans, pricing, and checkout links |
| verifyDedicatedServerPlans             | Verify Dedicated Server plans                 |
| verifyWordPressHostingPlans            | Verify WordPress hosting options              |
| verifySharedHostingPlans               | Verify Shared hosting plans                   |
| verifyResellerHostingPlans             | Verify Reseller hosting plans                 |
| verifyCrossCategoryNavigation          | Navigate between all hosting categories       |
| verifyPricingTableStructure            | Verify pricing table structure and links      |
| verifySelectProductNavigatesToCheckout | Verify Select button navigates to checkout    |
| verifyShoppingCartLinksToCheckout      | Verify shopping cart link points to checkout  |

## Running Tests

```bash
# Run all Selenium tests
mvn test -pl selenium-testing

# Run specific test class
mvn test -pl selenium-testing -Dtest=PricingSmokeTest

# Run specific test method
mvn test -pl selenium-testing -Dtest=PricingSmokeTest#verifyVpsHostingPlans
```

## Allure Reports

```bash
# Run tests (generates allure-results)
mvn clean test

# Generate static HTML report
mvn allure:report

# Serve report in browser (auto-opens)
mvn allure:serve

# Open generated report manually
open target/site/allure-maven-plugin/index.html
```

## Parallel Test Execution

Tests run in parallel using JUnit 5's native parallelism. Configuration in `junit-platform.properties`:

```properties
junit.jupiter.execution.parallel.enabled=true
junit.jupiter.execution.parallel.mode.default=concurrent
junit.jupiter.execution.parallel.config.strategy=fixed
junit.jupiter.execution.parallel.config.fixed.parallelism=4
```
