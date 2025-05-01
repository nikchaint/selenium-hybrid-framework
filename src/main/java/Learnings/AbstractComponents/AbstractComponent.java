package Learnings.AbstractComponents;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Learnings.PageObjects.OrderPage;

public class AbstractComponent {
	
	WebDriver driver;
	WebDriverWait wait;
	
	public AbstractComponent(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css = "button[routerlink*='/dashboard/myorders']")
	WebElement orderPageElement;
	
	By orderButtonBy = By.cssSelector("button[routerlink*='/dashboard/myorders']");
	
	public void waitUntilElementAppears(By findBy) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));
	}
	
	public void waitUntilElementDisappears(By findBy) throws InterruptedException {
		Thread.sleep(1000);
		//wait.until(ExpectedConditions.invisibilityOfElementLocated(findBy));
	}
	
	public OrderPage goToOders() {
		waitUntilElementAppears(orderButtonBy);
		orderPageElement.click();
		OrderPage orderPage = new OrderPage(driver);
		return orderPage;
	}
	

}
