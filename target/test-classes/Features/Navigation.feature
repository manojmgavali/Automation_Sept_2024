Feature: Navigation and Logout Functionality
  

  Background:
    Given user is on the Sauce Demo login page
    And user logs in with valid credentials

  @navigation
  Scenario: Verify Navigation Between Pages
    When user navigates to the product page
    And user navigates to the cart page
    And user navigates to the checkout page
    Then the page should load correctly

  @logout
  Scenario: Verify Logout Functionality
    When user clicks on the "Logout" button
    Then user should be redirected to the login page
    And user should be logged out
