package sauselab.sauselab.tests;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.*;



import sauselab.sauselab.pagefactory.HomePage;
import sauselab.sauselab.pagefactory.LoginPage;

public class HomeTest 
{
	WebDriver Driver;
	HomePage Home;
	LoginPage Login;
	
	@BeforeTest
	public void SetUp()
	{
		System.setProperty("webdriver.chrome.driver", "\\C:\\Users\\Ahmed Mohammed\\eclipse-workspace\\sauselab\\src\\test\\java\\sauselab\\sauselab\\resources\\chromedriver-win64\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		Map<String, Object> prefs = new HashMap<>();
		prefs.put("profile.password_manager_leak_detection", false);
		prefs.put("profile.password_manager_enabled", false);
		options.setExperimentalOption("prefs", prefs);
		Driver = new ChromeDriver(options);
		Driver.get("https://www.saucedemo.com/");
		Driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
	}
	
	
	@Test
	public void Homepage_PositiveTestScenario()
	{
		Login = new LoginPage(Driver);
		Home = new HomePage(Driver);
		
		Login.Type_Username("standard_user");
		Login.Verify_Username("standard_user");
		
		Login.Type_Password("secret_sauce");
		Login.Verify_Password("secret_sauce");
		
		Login.Click_LoginButton();
		Login.Verify_Login("https://www.saucedemo.com/inventory.html");
		//Driver.switchTo().alert().accept();
		Home.Click_AddToCart();
		Home.Verifiy_AddToCart();
		
		Home.Click_ShoppingCartBtn();
	}
	
	
	@AfterTest
	public void Terminate()
	{
		Driver.quit();
	}
}

