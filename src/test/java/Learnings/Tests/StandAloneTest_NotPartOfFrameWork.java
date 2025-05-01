package Learnings.Tests;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.idealized.Javascript;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;


import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import io.github.bonigarcia.wdm.WebDriverManager;

import static org.openqa.selenium.support.locators.RelativeLocator.*;

public class StandAloneTest_NotPartOfFrameWork {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//login credentials
		String userName = "nikhil1997@gmail.com";
		String password = "!123weSDF";
		
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
		String productName = "ADIDAS ORIGINAL";
		
		//Setting Up WebDriverManager
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		
		driver.manage().window().maximize();
		
//Login Page
		
		driver.findElement(By.xpath("//input[@id='userEmail']")).sendKeys(userName);
		driver.findElement(By.xpath("//div/label[@for='password']/following-sibling::input[@id='userPassword']")).sendKeys(password);
		driver.findElement(By.xpath("//input[contains(@type,'submit')]")).click();
		
//Home Page 
		
//		List<WebElement> products = driver.findElements(By.xpath("//div[@class='card-body']/h5"));
//		for(WebElement product : products) {
//			System.out.println(product.getText());
//			String productName = product.getText();
//			if(productName.equalsIgnoreCase("ADIDAS ORIGINAL")) {
//				product.findElement(By.xpath("./following-sibling::button[text()=' Add To Cart']")).click();
//			}
//		}
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
		
		List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));
		
		WebElement prod = products.stream().filter(product-> product.findElement(By.cssSelector("b")).getText()
				.equalsIgnoreCase(productName)).findFirst().orElse(null);
		
		prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();
		
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[id='toast-container']"))); 
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ng-animating")));
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[routerlink*='/dashboard/cart']")));
		driver.findElement(By.cssSelector("button[routerlink*='/dashboard/cart']")).click();
		
		
// Cart
		List<WebElement> itemsInCart = driver.findElements(By.xpath("//li[@class='items even ng-star-inserted']"));
		
		//verification of product availability
		boolean itemAvailabilityInCart = itemsInCart.stream().anyMatch(itemInCart -> itemInCart.findElement(By.xpath(".//h3")).getText()
				.equals(productName));
		Assert.assertTrue(itemAvailabilityInCart);
		
		//Select and Buy desire Product from the Cart
		WebElement cartProd = itemsInCart.stream().filter(itemInCart -> itemInCart.findElement(By.xpath(".//h3")).getText()
				.equals(productName)).findFirst().orElse(null);
		if(cartProd != null) {
			cartProd.findElement(By.xpath(".//button[text()='Buy Now']")).click();
		}else {
			System.out.println("Product not found in the Cart");
		}
		
		//Checkout all the product from the cart
		//driver.findElement(By.xpath("//button[text()='Checkout']")).click();
	
//PlaceOrder Page
		WebElement placeOrderButton = driver.findElement(By.xpath("//a[text()='Place Order ']"));
		JavascriptExecutor js = (JavascriptExecutor)driver;
		
		//scroll at the buttom of the page
		//js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
		
		//scrolls the page so the element appears at the bottom of the viewport.
		//arguments[0]: refers to the first argument passed to the script (your placeOrderButton element).
		//scrollIntoView(false): scrolls the element into view aligning it to the bottom of the page.
		js.executeScript("arguments[0].scrollIntoView(false);", placeOrderButton);
		
		WebElement ele = driver.findElement(By.xpath("//div[text()='Credit Card Number ']"));		
		driver.findElement(with(By.tagName("input")).below(ele)).clear();
		driver.findElement(with(By.tagName("input")).below(ele)).sendKeys(String.valueOf(cardNumber));
		
		WebElement staticDropdown_1 = driver.findElement(By.cssSelector("select[class*='input ddl']:nth-child(2)"));
		Select expiryMonthDropDown = new Select(staticDropdown_1);
		expiryMonthDropDown.selectByVisibleText(String.valueOf(expiryMonth));
		
		WebElement staticDropdown_2 =  driver.findElement(By.cssSelector("select[class*='input ddl']:nth-child(3)"));
		Select expiryYearDropDown = new Select(staticDropdown_2);
		expiryYearDropDown.selectByVisibleText(String.valueOf(expiryYear));
		
		driver.findElement(with(By.tagName("input")).toRightOf(staticDropdown_2)).sendKeys(cvvCode);
		
		WebElement ele1  = driver.findElement(By.xpath("//div[text()='Apply Coupon ']"));
		driver.findElement(with(By.tagName("input")).above(ele1)).sendKeys(cardHolderName);
		
		driver.findElement(with(By.tagName("input")).below(ele1)).sendKeys(coupenCode);
		
		driver.findElement(By.cssSelector("button[type='submit']")).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mt-1.ng-star-inserted")));
		String confirmationString = driver.findElement(By.cssSelector(".mt-1.ng-star-inserted")).getText();
		
		System.out.println(confirmationString);
		Assert.assertEquals(confirmationString, "* Coupon Applied");
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[placeholder*='Select Country']")));
		
		driver.findElement(By.cssSelector("input[placeholder*='Select Country']")).sendKeys("Ind");
		List<WebElement> selCountryElements =  driver.findElements(By.cssSelector("span[class*='ng-star-inserted']"));
		WebElement element = selCountryElements.stream().filter(s -> s.getText().equals("India")).findFirst().orElse(null);
		element.click();
		
		driver.findElement(By.xpath("//a[text()='Place Order ']")).click();
		
		String orderSuccess = driver.findElement(By.cssSelector("h1[class*='hero-primary']")).getText();
		System.out.println(orderSuccess);
		Assert.assertEquals(orderSuccess.trim(), "THANKYOU FOR THE ORDER.");
		
		driver.findElement(By.cssSelector("tbody tr:nth-child(4) button")).click();
	}

}
