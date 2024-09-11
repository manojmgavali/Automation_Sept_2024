Feature: Test login functionality

  Scenario: check login is sucessful with valid credentials
    Given Browser is open
    And user is on login page
    When user enter valid credentials "standard_user" and "secret_sauce"
    And clicks on login button
    Then user is redirected to the product page

  Scenario: check login with invalid credentials
    Given Browser is open
    And user is on login page
    When user enter invalid credentials "kim_user" and "kim_123"
    And clicks on login button
    Then user should get the error message

  Scenario: check login with empty fields
    Given Browser is open
    And user is on login page
    When user leaves both username and password fileds empty
    Then An error message is displayed for empty credentials
