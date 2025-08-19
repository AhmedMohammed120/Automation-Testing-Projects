package Tests;

import PageFactory.LoginPage;
import com.google.gson.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class LoginTests
{
    WebDriver Driver;
    LoginPage LoginPageObj;
    ChromeOptions Options;
    JsonArray TestData;

    @BeforeTest
    void Setup() throws IOException
    {
        System.setProperty("webdriver.chrome.driver","D:\\Automation Testing\\IntelliJ Projects\\SauceDemo\\src\\test\\java\\Resources\\chromedriver.exe");
        Options = new ChromeOptions();
        Options.addArguments("--incognito");
        Options.addArguments("user-data-dir=C:/temp/chrome-profile");
        Driver = new ChromeDriver(Options);
        Driver.navigate().to("https://www.saucedemo.com/");

        // Read JSON file
        FileReader Reader = new FileReader("D:\\Automation Testing\\IntelliJ Projects\\SauceDemo\\src\\test\\java\\Resources\\LoginDatabase.json");
        //FileWriter Writer = new FileWriter("D:\\Automation Testing\\IntelliJ Projects\\SauceDemo\\src\\test\\java\\Resources\\Login_TCs_Database.json");

        TestData = JsonParser.parseReader(Reader).getAsJsonArray();


    }

    @Test
    void LoginTests() throws InterruptedException, IOException {

        LoginPageObj = new LoginPage(Driver);
        var LoginsArray = TestData;
        String Username;
        String Password;
        String ExpectedResult;
        String TC_Result;

        for(var Login_TC:LoginsArray)
        {
            TC_Result = "Fail";
            Login_TC.getAsJsonObject().addProperty("LastTestResult",TC_Result);
            FileWriter Writer = new FileWriter("D:\\Automation Testing\\IntelliJ Projects\\SauceDemo\\src\\test\\java\\Resources\\LoginDatabase.json");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(TestData, Writer);
            Writer.close();

            Username = Login_TC.getAsJsonObject().get("Username").getAsString();
            Password = Login_TC.getAsJsonObject().get("Password").getAsString();
            ExpectedResult = Login_TC.getAsJsonObject().get("ExpectedURL").getAsString();
            TC_Result = "Failed";


            LoginPageObj.TypeUsername(Username);
            LoginPageObj.VerifyUsername(Username);

            LoginPageObj.TypePassword(Password);
            LoginPageObj.VerifyPassword(Password);
            Thread.sleep(2000);

            LoginPageObj.Click_Login();
            LoginPageObj.VerifyNextURL(ExpectedResult);

            if(Driver.getCurrentUrl().contains("inventory.html"))
            {
                Driver.findElement(By.id("react-burger-menu-btn")).click();
                Thread.sleep(2000);
                Driver.findElement(By.id("logout_sidebar_link")).click();
                LoginPageObj.VerifyLogout("https://www.saucedemo.com/");
            }
            else
            {
                //To be done.
            }
            TC_Result = "Passed";
            Login_TC.getAsJsonObject().addProperty("LastTestResult",TC_Result);
             Writer = new FileWriter("D:\\Automation Testing\\IntelliJ Projects\\SauceDemo\\src\\test\\java\\Resources\\LoginDatabase.json");
             gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(TestData, Writer);
            Writer.close();
        }
        System.out.println("Test Case Passed");


    }
    @AfterTest
    void CloseBrowser()
    {
        //Driver.quit();
    }
}
