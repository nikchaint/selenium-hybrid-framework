package Learnings.PageObjects;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CartPage {
	WebDriver driver;
	
	public CartPage(WebDriver driver) {
		//initialization
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy (xpath = "//li[@class='items even ng-star-inserted']")
	List<WebElement> prodctList;
	
	By buyNowButton = By.xpath(".//button[text()='Buy Now']");
	
	public List<WebElement> getTheListoItemFromCart() {
		return prodctList;
	}
	
	public boolean validateTheItemAvailabilityInCart(String productName) {
		return getTheListoItemFromCart().stream().anyMatch(itemInCart -> itemInCart.findElement(By.xpath(".//h3")).getText()
				.equals(productName));
	}
	
	public PaymentAndPlaceOrderPage selectTheProductfromCart(String productName) {
		WebElement cartProd = getTheListoItemFromCart().stream().filter(itemInCart -> itemInCart.findElement(By.xpath(".//h3")).getText()
				.equals(productName)).findFirst().orElse(null);
		if(cartProd != null) {
			cartProd.findElement(buyNowButton).click();
		}else {
			System.out.println("Product not found in the Cart");
		}
		
		return new PaymentAndPlaceOrderPage(driver);
	}
	
	
}
