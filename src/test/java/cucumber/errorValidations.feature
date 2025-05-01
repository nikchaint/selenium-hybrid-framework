Feature: Login Page Validations

  Background:
    Given I landed on the Login Page

    @ErrorValidation
  Scenario Outline: Positive Test of Submit Order
    Given Logged with the username <userName> and password <passWord>
    Then "Incorrect email or password." message is displayed.
    Examples:
      | userName             | passWord  | productName      |
      | nikhil1997@gmail.com | !123weS | ADIDAS ORIGINAL  |