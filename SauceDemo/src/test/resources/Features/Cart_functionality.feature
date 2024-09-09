Feature: Cart Functionality on Sauce Demo Website

  Background: 
    Given User on the login page
    When user login with valid credentials "standard_user" and "secret_sauce"
    Then user should be redirected to the product page
    And user add "Sauce Labs Backpack" to the cart

  Scenario: Verify View Cart
    When user click on the cart icon
    Then user should see the following product in the cart:
      | Sauce Labs Backpack |

  Scenario: Verify Remove Item from Cart
    When user remove "Sauce Labs Backpack" from the cart
    Then cart count should be "0"
    And the product "Sauce Labs Backpack" should not be listed in the cart
    
   Scenario: Verify Checkout Process
    When user click on the "Checkout" button
    And enter checkout information:
      | Name        | John Doe        |
      | Address     | 123 Test Street |
      | Postal Code | 12345           |
    And user complete the checkout process
    Then the order confirmation page should be displayed
