@smoke @functional
Feature: Navigation Links Accessibility
  As a user
  I want navigation tabs to work correctly
  So that I can browse different hosting options

  Background:
    Given the user is on the InMotion Hosting pricing page

  Scenario: All hosting category tabs should be interactive
    When I click on each hosting category tab sequentially
    Then each tab should successfully display its pricing content
