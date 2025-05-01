package Learnings.PageObjects;


import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import Learnings.AbstractComponents.AbstractComponent;

public class PaymentAndPlaceOrderPage extends AbstractComponent {
	WebDriver driver;
	
	public PaymentAndPlaceOrderPage(WebDriver driver) {
		//initialization
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//a[text()='Place Order ']")
	WebElement placeOrderButton;
	
	@FindBy(css = "input[value*='4542 9931 9292 2293']")
	WebElement cardNumber;
	
	@FindBy(css = "select[class*='input ddl']:nth-child(2)")
	WebElement staticDropdown_1;
	
	@FindBy(css = "select[class*='input ddl']:nth-child(3)")
	WebElement staticDropdown_2;
	
	@FindBy(xpath = "(//input[@class='input txt'])[1]")
	WebElement cvvCode;
	
	@FindBy(xpath = "(//input[@class='input txt'])[2]")
	WebElement cardHolderName;
	
	
	@FindBy (xpath = "//input[@name='coupon']")
	WebElement couponElement;
	
	@FindBy (css = "button[type='submit']")
	WebElement applyCouponButton;
	
	@FindBy (css = "span[class*='ng-star-inserted']")
	List<WebElement> selectCountryDropDownElements;

	@FindBy (xpath = "//input[@placeholder='Select Country']")
	WebElement selectCountryElement;
	
	@FindBy (css = ".mt-1.ng-star-inserted")
	WebElement couponConfirmationMessage;
	
	@FindBy(css = "h1[class*='hero-primary']")
	WebElement successfulOrderPlacedMesssage;
	
	@FindBy(css = "tbody tr:nth-child(4) button")
	WebElement exportOrderDetails;
	
	By couponConfirmationMessageBy = By.cssSelector(".mt-1.ng-star-inserted");
	By countryDropDownBy = By.cssSelector("span[class*='ng-star-inserted']");
	By successfulOrderPlacedMesssageBy = By.cssSelector("h1[class*='hero-primary']");

	
	public void personalInformation(long cardnumber, int expirymonth, int expiryyear, String cvvcode, String cardholderName) {
		
		JavascriptExecutor js = (JavascriptExecutor)driver;
		
		//scroll at the buttom of the page
		//js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
		
		//scrolls the page so the element appears at the bottom of the viewport.
		//arguments[0]: refers to the first argument passed to the script (your placeOrderButton element).
		//scrollIntoView(false): scrolls the element into view aligning it to the bottom of the page.

		js.executeScript("arguments[0].scrollIntoView(false);", placeOrderButton);
		
		cardNumber.clear();
		cardNumber.sendKeys(String.valueOf(cardnumber));
		
		Select expiryMonthDropDown = new Select(staticDropdown_1);
		expiryMonthDropDown.selectByVisibleText(String.valueOf(expirymonth));
		
		Select expiryYearDropDown = new Select(staticDropdown_2);
		expiryYearDropDown.selectByVisibleText(String.valueOf(expiryyear));
		
		cvvCode.sendKeys(cvvcode);
		
		cardHolderName.sendKeys(cardholderName);
	}
	
	public void shippingInformation(String counteyname) {
		
		selectCountryElement.sendKeys(counteyname);
		
		waitUntilElementAppears(countryDropDownBy);

		WebElement element = selectCountryDropDownElements.stream().filter(s -> s.getText().equals(counteyname)).findFirst().orElse(null);
		element.click();
	}
	
	public String applyCouponCode(String coupencode) {
		couponElement.sendKeys(coupencode);
		
		applyCouponButton.click();
		
		waitUntilElementAppears(couponConfirmationMessageBy);
		
		return couponConfirmationMessage.getText();

	}
	
	public String placeOrder() {
		
		placeOrderButton.click();
		
		waitUntilElementAppears(successfulOrderPlacedMesssageBy);
		
		String orderSuccess = successfulOrderPlacedMesssage.getText();
		return orderSuccess;
		
	}
	
	public void ExportOrderDetails() {
		exportOrderDetails.click();
	}
	
	
}
