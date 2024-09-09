Feature: Product Page Functionality

Background: the user logged into the application
    Given The Browser is open
    And user is on the login page
    When user enter the valid credentials "standard_user" and "secret_sauce"
    And clicks on the login button

  Scenario: Verify Product Visibility
    When the user navigates to the product page
    Then the product list is displayed
    And all expected products are visible
     

      
   Scenario: Verify Product Details
    When the user clicks on product "Sauce Labs Backpack"
    Then the product details should be displayed with name "Sauce Labs Backpack", description "carry.allTheThings() with the sleek, streamlined Sly Pack that melds uncompromising style with unequaled laptop and tablet protection.", and price "$29.99"
    
    
  Scenario: Verify Add to Cart
    When user click the Add to Cart button for the product "Sauce Labs Bike Light"
    Then the cart count should be "1"
    And the product "Sauce Labs Bike Light" should be listed in the cart
    
    