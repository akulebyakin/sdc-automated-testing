@smoke @individual @vps
Feature: VPS Page Language Switching and Chat Functionality
  As a user
  I want to change the language on the VPS hosting page
  And I want to be able to contact support via chat
  So that I can view content in my preferred language and get assistance

  Background:
    Given the user is on the VPS hosting page

  @functional @language
  Scenario: Change website language to German and back to English
    When I scroll to the language selector
    And I hover over the language selector
    And I switch the language to Deutsch
    Then the VPS page URL should contain "/de/vps-hosting"
    And the hero title should display "Skalieren ohne Grenzen mit Hosting, das sich so schnell bewegt wie du"
    When I switch the language to English
    Then the VPS page URL should contain "vps-hosting"
    And the hero title should contain "Scale Without Limits"

  @functional @chat
  Scenario: Open Contact Us chat and verify chat page elements
    When I click on the Contact Us chat button
    Then a new browser tab should open with the chat page
    And the chat page title should contain "InMotion Hosting Live Chat"
    And the chat page URL should contain "secure1.inmotionhosting.com/amp/chat/survey"
    And the customer name input should be visible and enabled
    And the customer email input should be visible and enabled
    And the sales chat submit button should be visible and enabled
    When I close the chat tab and switch back to VPS page
    Then the VPS page should still be open
