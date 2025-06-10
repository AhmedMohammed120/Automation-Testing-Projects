package sauselab.sauselab.pagefactory;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class LoginPage 
{
WebDriver Driver;

By Username_TextField = By.id("user-name");
By Password_TextField = By.id("password");
By LoginButton = By.id("login-button");


public LoginPage(WebDriver DriverCpy)
{
	Driver = DriverCpy;
}

public void Type_Username(String Username)
{
	Driver.findElement(Username_TextField).sendKeys(Username);
}

public void Type_Password(String Password)
{
	Driver.findElement(Password_TextField).sendKeys(Password);
}

public void Click_LoginButton()
{
	Driver.findElement(LoginButton).click();
}

public void Verify_Username(String Username)
{
	Assert.assertEquals(Driver.findElement(Username_TextField).getAttribute("value"), Username);
}

public void Verify_Password(String Password)
{
	Assert.assertEquals(Driver.findElement(Password_TextField).getAttribute("value"), Password);
}

public void Verify_Login(String RedirectedURL)
{
	Assert.assertEquals(Driver.getCurrentUrl(), RedirectedURL);
}


}
