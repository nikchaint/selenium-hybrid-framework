package StepDefinationImplementation;

import Learnings.PageObjects.CartPage;
import Learnings.PageObjects.LoginPage;
import Learnings.PageObjects.PaymentAndPlaceOrderPage;
import Learnings.PageObjects.ProductCatalogue;
import Learnings.TestComponents.BaseTest;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.io.IOException;

public class stepDefination extends BaseTest {

    LoginPage loginPage;
    ProductCatalogue productCatelogue;
    CartPage cartPage;
    String successfulOrderPlaced;
    PaymentAndPlaceOrderPage paymentAndPlaceOrderPage;

    @Given("I landed on the Login Page")
    public void login_ToThe_Website() throws IOException {
        loginPage = lounchApplication();
    }

    @Given("^Logged with the username (.+) and password (.+)$")
    public void logged_with_credentials(String username, String password) {
        productCatelogue = loginPage.loginApplication(username, password); // assuming loginPage has a method login()
    }

    @When("^I add (.+) to Cart$")
    public void add_Product_To_Cart(String prodName) throws InterruptedException {
        cartPage = productCatelogue.addProductToCart(prodName);
    }

    @And("^Checkout the (.+) and Submit the Order$")
    public void checkout_Prod_And_SubmitOrder(String prodName){
        paymentAndPlaceOrderPage = cartPage.selectTheProductfromCart(prodName);
        paymentAndPlaceOrderPage.personalInformation(4542993192922294L, 10, 30, "016", "Ajay Atul");
        paymentAndPlaceOrderPage.shippingInformation("India");
        String couponConfirmationMessage = paymentAndPlaceOrderPage.applyCouponCode("rahulshettyacademy");
        Assert.assertEquals(couponConfirmationMessage, "* Coupon Applied");
        successfulOrderPlaced = paymentAndPlaceOrderPage.placeOrder();
    }

    @Then("{string} message is displayed on ConfirmationPage")
    public void confirmation_Of_Order(String string){
        Assert.assertEquals(successfulOrderPlaced.trim(), string);
    }

    @Then ("{string} message is displayed.")
    public void login_Error_Validations(String string){
        Assert.assertEquals(string, "Incorrect email or password.");
    }


}
