package Learnings.Tests;

import Learnings.TestComponents.Retry_FlackyTests;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Learnings.PageObjects.CartPage;
import Learnings.PageObjects.LoginPage;
import Learnings.PageObjects.OrderPage;
import Learnings.PageObjects.PaymentAndPlaceOrderPage;
import Learnings.PageObjects.ProductCatalogue;
import Learnings.TestComponents.BaseTest;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;


public class SubmitOrderTest extends BaseTest {
	
	String productName = "ADIDAS ORIGINAL";
	
	//login credentials
	String userName = "nikhil1997@gmail.com";
	String password = "!123weSDF";

	@Test (groups = {"E2E Testing"}, dataProvider = "getData", retryAnalyzer = Retry_FlackyTests.class)
	public void submitOrder(HashMap<String, String> input) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		
		//Card Details
		long cardNumber = 4542993192922294L;
		int expiryMonth = 10;
		int expiryYear = 30;
		String cvvCode = "016";
		String cardHolderName = "Ajay Atul";
		
		//Coupon Details
		String coupenCode = "rahulshettyacademy";
		
		//Shipping Information
		String country = "India";
		
		//Product Name  
		//String productName = "ADIDAS ORIGINAL";
	
//Login Page
		
//		LoginPage loginPage = new LoginPage(driver);
//		loginPage.goTo();
		//LoginPage loginPage = lounchApplication();
		ProductCatalogue productCatelogue = loginPage.loginApplication(input.get("userName"), input.get("passWord"));
//Product Catalouge Page 
		
		//ProductCatalogue productCatelogue = new ProductCatalogue(driver);
		CartPage cartPage = productCatelogue.addProductToCart(input.get("productName"));
			
		
// Cart
		//CartPage cartPage = new CartPage(driver);
		boolean itemAvailabilityInCart = cartPage.validateTheItemAvailabilityInCart(input.get("productName"));
		Assert.assertTrue(itemAvailabilityInCart);
		
		PaymentAndPlaceOrderPage paymentAndPlaceOrderPage = cartPage.selectTheProductfromCart(input.get("productName"));
		
//PlaceOrder Page
		
		//PaymentAndPlaceOrderPage paymentAndPlaceOrderPage = new PaymentAndPlaceOrderPage(driver);
		paymentAndPlaceOrderPage.personalInformation(cardNumber, expiryMonth, expiryYear, cvvCode, cardHolderName);
		paymentAndPlaceOrderPage.shippingInformation(country);
		String couponConfirmationMessage = paymentAndPlaceOrderPage.applyCouponCode(coupenCode);
		Assert.assertEquals(couponConfirmationMessage, "* Coupon Applied");
		String successfulOrderPlaced = paymentAndPlaceOrderPage.placeOrder();
		Assert.assertEquals(successfulOrderPlaced.trim(), "THANKYOU FOR THE ORDER.");
		
		paymentAndPlaceOrderPage.ExportOrderDetails();
		
//tearDown
		//tearDown();
			
	}
	
	@Test (dependsOnMethods = {"submitOrder"})
	public void MyOrders() throws IOException {
		//LoginPage loginPage = lounchApplication();
		ProductCatalogue productCatelogue = loginPage.loginApplication(userName, password);
		OrderPage orderpage = productCatelogue.goToOders();
		boolean bool = orderpage.ProductInOrderPageValidation(productName);
		Assert.assertTrue(bool);
	}
	
	@DataProvider
	public Object[][] getData() throws IOException {
//		HashMap<String, String> map = new HashMap<String, String>();
//		map.put("userName", "nikhil1997@gmail.com");
//		map.put("passWord","!123weSDF");
//		map.put("productName", "ADIDAS ORIGINAL");
//		
//		HashMap<String, String> map1 = new HashMap<String, String>();
//		map1.put("userName", "mayur1995@gmail.com");
//		map1.put("passWord","#345rtFGH");
//		map1.put("productName", "IPHONE 13 PRO");
		
		//getJsonDataToMap() define in Base class to access the json data which returns the List of HashMap
		List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir")+"\\src\\test\\java\\Learnings\\Data\\PurchaseOrder.json");
		
		return new Object[][] {{data.get(0)},{data.get(1)}};
	}
	
//	@DataProvider
//	public Object[][] getData() {
//		return new Object[][]{
//			{"nikhil1997@gmail.com","!123weSDF","ADIDAS ORIGINAL"},
//			{"mayur1995@gmail.com","#345rtFGH", "IPHONE 13 PRO"}
//			}; 
//		
//	}

}
