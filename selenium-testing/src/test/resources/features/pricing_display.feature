@defect @usability @DEFECT-002
Feature: Pricing Display
  As a potential customer
  I want to clearly see both promotional and renewal prices
  So that I can make an informed purchasing decision

  Background:
    Given the user is on the InMotion Hosting pricing page

  @critical @smoke
  Scenario: Pricing should show both promotional and renewal rates clearly
    When I view the pricing plans for any hosting category
    Then promotional prices should be displayed prominently
    And renewal prices should be displayed

  @visual
  Scenario Outline: Price visibility should be consistent across hosting types
    When I navigate to the "<hosting_type>" tab
    And I examine the pricing display
    Then I should see the promotional price clearly
    And I should see the renewal price with similar visual weight
    And the price difference should be clearly communicated

    Examples:
      | hosting_type      |
      | VPS Hosting       |
      | Dedicated Servers |
      | WordPress Hosting |
      | Shared Hosting    |
      | Reseller Hosting  |

  @accessibility @visual
  Scenario: Renewal prices should not be visually de-emphasized
    Given I am viewing the "VPS Hosting" plans
    When I examine the pricing display
    Then the renewal information should not be visually hidden
    And the renewal price should have adequate color contrast
