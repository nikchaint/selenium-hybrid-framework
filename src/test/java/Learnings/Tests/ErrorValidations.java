package Learnings.Tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import Learnings.PageObjects.CartPage;
import Learnings.PageObjects.ProductCatalogue;
import Learnings.TestComponents.BaseTest;
import Learnings.TestComponents.Retry_FlackyTests;

public class ErrorValidations extends BaseTest {

	@Test
	public void ErrorValidationsTest() {
		   //loginApplication("vaibhavi@gmail.com","Password@123");
		//String loginPassValidationErrorMeg = loginPage.loginPassValidation();
		
		loginPage.loginApplication("vaibhavi@gmail.com","Password@123");
		String loginPassValidationErrorMeg = loginPage.loginPassValidation();
		Assert.assertEquals(loginPassValidationErrorMeg, "Incorrect email or password."); //"Incorrect email or password."
	}
	
	@Test (retryAnalyzer = Retry_FlackyTests.class)
	public void productValidationCheck() throws InterruptedException {
		ProductCatalogue productCatelouge = loginPage.loginApplication("nikhil30@gmail.com","Password@123");
        
		 
        String productName = "ADIDAS ORIGINAL";
        productCatelouge.addProductToCart(productName);
        
        CartPage cartPage = new CartPage(driver);
        Boolean match =  cartPage.validateTheItemAvailabilityInCart("ADIDAS ORIGINAL123");
        Assert.assertTrue(match);
	}
}
