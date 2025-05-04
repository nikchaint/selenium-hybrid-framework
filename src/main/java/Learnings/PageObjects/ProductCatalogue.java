package Learnings.PageObjects;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import Learnings.AbstractComponents.AbstractComponent;

public class ProductCatalogue extends AbstractComponent {
	WebDriver driver;
	
	public ProductCatalogue(WebDriver driver) {
		//initialization
		super(driver); // pass the driver to the Parent AbstractComponent class
		
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(css = ".mb-3")
	List<WebElement> productList;
	
	@FindBy(css = "button[routerlink*='/dashboard/cart']")
	WebElement cartButton;
	
	
	By productBy = By.cssSelector(".mb-3");
	By addTocartBy = By.cssSelector(".card-body button:last-of-type");
	By elementappearBy = By.cssSelector("div[id='toast-container']");
	By loadingElementBy = By.cssSelector(".ng-animating");
	By cartButtonBy = By.cssSelector("button[routerlink*='/dashboard/cart']");
	
	
	public List<WebElement> getProductList() {
		waitUntilElementAppears(productBy);
		return productList;
	}
	
	public WebElement getProductByName(String productName) {
		WebElement prod = getProductList().stream().filter(product-> product.findElement(By.cssSelector("b")).getText()
				.equalsIgnoreCase(productName)).findFirst().orElse(null);
		return prod;
	}
	
	public CartPage addProductToCart(String productName) throws InterruptedException {
		getProductByName(productName).findElement(addTocartBy).click();
		waitUntilElementAppears(elementappearBy);
		waitUntilElementDisappears(loadingElementBy);
		waitUntilElementAppears(cartButtonBy);
		cartButton.click();
		
		return new CartPage(driver);
		
			
	}
}
