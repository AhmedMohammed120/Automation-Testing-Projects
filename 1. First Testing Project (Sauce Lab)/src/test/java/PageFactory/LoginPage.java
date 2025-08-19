package PageFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class LoginPage
{
    WebDriver Driver;
    By Username_TxtField = By.id("user-name");
    By Password_TxtField = By.id("password");
    By Login_Btn  = By.id("login-button");
    By Logout_Btn = By.id("logout_sidebar_link");

    public LoginPage(WebDriver Driver_Cpy)
    {
        Driver = Driver_Cpy;
    }
    public void TypeUsername(String UsernameCpy)
    {
        Driver.findElement(Username_TxtField).clear();
        Driver.findElement(Username_TxtField).sendKeys(UsernameCpy);
    }

    public void TypePassword(String PasswordCpy)
    {
        Driver.findElement(Password_TxtField).clear();
        Driver.findElement(Password_TxtField).sendKeys(PasswordCpy);
    }

    public void Click_Login()
    {
        Driver.findElement(Login_Btn).click();
    }

    public void Click_Logout() throws InterruptedException {

    }

    public void VerifyUsername(String UsernameCpy)
    {
        Assert.assertEquals(Driver.findElement(Username_TxtField).getAttribute("value"),UsernameCpy,"Error, Username Verification failed");
    }

    public void VerifyPassword(String PasswordCpy)
    {
        Assert.assertEquals(Driver.findElement(Password_TxtField).getAttribute("value"),PasswordCpy,"Error, Password Verification failed");
    }

    public void VerifyNextURL(String RedirectedURL)
    {
        Assert.assertTrue(Driver.getCurrentUrl().contains(RedirectedURL),"Error, Login URL Verification Failed");
    }

    public void VerifyLogout(String RedirectedURL)
    {
        Assert.assertTrue(Driver.getCurrentUrl().contains(RedirectedURL),"Error, Logout URL Verification Failed");
    }
}