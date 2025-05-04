package Learnings.PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import Learnings.AbstractComponents.AbstractComponent;

public class LoginPage extends AbstractComponent {
	WebDriver driver;
	
	public LoginPage(WebDriver driver) {
		//initialization
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	//WebElement userEmail = driver.findElement(By.xpath("//input[@id='userEmail']"));
	@FindBy(xpath = "//input[@id='userEmail']")
	WebElement userName;
	
	@FindBy(xpath = "//div/label[@for='password']/following-sibling::input[@id='userPassword']")
	WebElement passWord;
	
	@FindBy(xpath = "//input[contains(@type,'submit')]")
	WebElement loginButton;
	
	@FindBy(css = "div[aria-label='Incorrect email or password.']")
	WebElement loginError;
	
	By loginErrMessageBy = By.cssSelector("div[aria-label='Incorrect email or password.']");
	
	public ProductCatalogue loginApplication(String username, String password) {
		userName.sendKeys(username);
		passWord.sendKeys(password);
		loginButton.click();
		return new ProductCatalogue(driver);
	}
	
	public String loginPassValidation() {
		waitUntilElementAppears(loginErrMessageBy);
		return loginError.getText();
	}
	
	public void goTo() {
		driver.get("https://rahulshettyacademy.com/client");
	}
	
	
}
