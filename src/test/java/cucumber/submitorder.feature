Feature: Purchase the order from the e-commernce Website

  Background:
    Given I landed on the Login Page

    @Regression
  Scenario Outline: Positive Test of Submit Order
    Given Logged with the username <userName> and password <passWord>
    When I add <productName> to Cart
    And Checkout the <productName> and Submit the Order
    Then "THANKYOU FOR THE ORDER." message is displayed on ConfirmationPage
    Examples:
      | userName             | passWord  | productName      |
      | nikhil1997@gmail.com | !123weSDF | ADIDAS ORIGINAL  |