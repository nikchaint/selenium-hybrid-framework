package Learnings.TestComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import Learnings.PageObjects.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
	
	public WebDriver driver;
	public LoginPage loginPage;
	
	public WebDriver initialization() throws IOException {
		Properties prop = new Properties();
		FileInputStream file = new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\Learnings" +
				"\\Resources\\GlobalData" +
				".properties");
		prop.load(file);
		
		// This line checks if the system property "browser" is passed while running the test (e.g., mvn test -Dbrowser=Chrome)
		// If it is passed, it uses that value.
		// Otherwise, it falls back to the browser value defined in the config.properties file.
		String browserName =  System.getProperty("browser") != null ? System.getProperty("browser") : prop.getProperty("browser");
		
		
		if(browserName.contains("headless")) {
			ChromeOptions options = new ChromeOptions();
			WebDriverManager.chromedriver().setup();
			
			options.addArguments("headless"); // to run the tests in headless mode

			driver = new ChromeDriver(options);
			driver.manage().window().setSize(new Dimension (1440, 900)); // set standard dimensions for the screen
			
		} else if(browserName.contains("Chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			driver.manage().window().setSize(new Dimension (1440, 900)); // set standard dimensions for the screen
		} else if(browserName.equals("Edge")) {
			driver = new EdgeDriver();	
		} else if(browserName.equals("Firefox")) {
			driver = new FirefoxDriver();
		}
		
		driver.manage().window().maximize();	
		
		return driver;
	}
	
	@BeforeMethod (alwaysRun = true)
	public LoginPage lounchApplication() throws IOException {
		driver = initialization();
		loginPage = new LoginPage(driver);
		loginPage.goTo();
		return loginPage;
	}
	
	@AfterMethod (alwaysRun = true)
	public void tearDown() {
		driver.close();
	}
	
	public String getScreenShot(String testName, WebDriver driver) throws IOException {
		TakesScreenshot ts = (TakesScreenshot)driver;
		File src = ts.getScreenshotAs(OutputType.FILE);
		File dec = new File(System.getProperty("user.dir")+"//reports//"+testName+".png");
		
		FileUtils.copyFile(src, dec);
		
		return System.getProperty("user.dir")+"//reports//"+testName+".png";
	}
	
	public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException {
		//read json to string
		String jsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
		
		//String to HashMap  Jackson Databind
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>(){	
		});
		return data;
		
	}
}
