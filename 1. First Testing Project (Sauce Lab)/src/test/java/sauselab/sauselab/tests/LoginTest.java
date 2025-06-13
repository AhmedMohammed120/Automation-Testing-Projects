package sauselab.sauselab.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*; 
import sauselab.sauselab.pagefactory.LoginPage;



public class LoginTest 
{
	WebDriver Driver;
	LoginPage Login;
	
	@BeforeClass
    public void setUp() 
	{
      System.setProperty("webdriver.chrome.driver", "C:\\Users\\Ahmed Mohammed\\eclipse-workspace\\sauselab\\src\\test\\java\\sauselab\\sauselab\\resources\\chromedriver-win64\\chromedriver.exe");
      Driver = new ChromeDriver();
      Driver.get("https://www.saucedemo.com/");
      Login = new LoginPage(Driver);
	}
	
	@Test
	void Positive_Test_Senario()
	{
		Login.Type_Username("standard_user");
		Login.Verify_Username("standard_user");
		
		Login.Type_Password("secret_sauce");
		Login.Verify_Password("secret_sauce");
		
		Login.Click_LoginButton();
		Login.Verify_Login("https://www.saucedemo.com/inventory.html");
	}
	
	
	@AfterClass
	void Terminate()
	{
		//Driver.quit(); 
	}
	
	
}
