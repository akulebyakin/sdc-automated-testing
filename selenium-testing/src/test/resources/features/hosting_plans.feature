@smoke @pricing
Feature: Hosting Plans Display and Navigation
  As a customer
  I want to see hosting plans displayed correctly
  So that I can compare options and make a purchase

  Background:
    Given the user is on the InMotion Hosting pricing page

  @blocker @page-load
  Scenario: Pricing page loads with heading and tabs
    Then the page URL should contain "inmotionhosting.com/pricing"
    And the page title should contain "Pricing"
    And the page heading should be visible
    And there should be 5 hosting category tabs
    And the tabs should include "VPS" and "Dedicated" options

  @critical @hosting-plans
  Scenario Outline: Hosting plans are displayed correctly for each category
    When I navigate to the "<hosting_type>" tab
    Then the "<plan1>" plan should be visible
    And the "<plan2>" plan should be visible
    And there should be at least <min_buttons> Select buttons
    And the Select button should link to checkout

    Examples:
      | hosting_type      | plan1      | plan2      | min_buttons |
      | VPS Hosting       | VPS 4 vCPU | VPS 8 vCPU | 4           |
      | Dedicated Servers | Aspire     | Essential  | 5           |
      | WordPress Hosting | WP Core    | WP Pro     | 4           |
      | Shared Hosting    | Core       | Launch     | 4           |
      | Reseller Hosting  | R-1000N    | R-2000N    | 4           |

  @critical @navigation
  Scenario: Navigation between all hosting categories works correctly
    When I navigate to the "VPS Hosting" tab
    Then the "VPS" plan should be visible
    When I navigate to the "Dedicated Servers" tab
    Then the "Aspire" plan should be visible
    When I navigate to the "WordPress Hosting" tab
    Then the "WP Core" plan should be visible
    When I navigate to the "Shared Hosting" tab
    Then the "Core" plan should be visible
    When I navigate to the "Reseller Hosting" tab
    Then the "R-1000N" plan should be visible

  @normal @pricing-table
  Scenario: Pricing tables have proper structure
    When I navigate to the "VPS Hosting" tab
    And I scroll to the Compare All Plans section
    Then pricing tables should be present
    And plan names should be displayed
    And Select buttons should have valid checkout URLs

  @blocker @checkout
  Scenario: Selecting a product navigates to checkout page
    When I navigate to the "VPS Hosting" tab
    And I click the first Select button
    Then I should be on the checkout page

  @normal @checkout
  Scenario: Shopping cart link points to checkout page
    Then the shopping cart link should contain "checkout"
    And the shopping cart link should contain "inmotionhosting.com"
