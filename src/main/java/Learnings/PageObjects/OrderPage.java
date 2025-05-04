package Learnings.PageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import Learnings.AbstractComponents.AbstractComponent;

public class OrderPage extends AbstractComponent {
	WebDriver driver;
	
	public OrderPage(WebDriver driver) {
		//initialization
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css = "tr td:nth-child(3)")
	List<WebElement> productsInOrderPage;

	By myOrderPageTitleBy = By.xpath("//h1[contains(text(),'Your Orders')]");
	
	public boolean ProductInOrderPageValidation(String productName) {
		waitUntilElementAppears(myOrderPageTitleBy);
		boolean result = productsInOrderPage.stream().anyMatch(s -> s.getText().equalsIgnoreCase(productName));
		return result;
	}
}
