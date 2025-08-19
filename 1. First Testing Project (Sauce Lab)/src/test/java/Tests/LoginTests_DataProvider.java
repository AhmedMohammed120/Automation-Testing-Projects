/**
 * File: LoginTests_DataProvider.java
 * Author: Ahmed Mohammed Arafa Mohammed
 * Date: 15/8/2025
 *
 * Brief:
 * This file contains automated login test cases for the Sauce Lab website.
 * It uses TestNG with a DataProvider to run multiple login scenarios from a JSON test data file.
 *
 * Description:
 * - Reads test data from a JSON array.
 * - Runs login tests with valid and invalid credentials.
 * - Records pass/fail results for each test case.
 * - Record passed & failed results in a separate database
 * - Closes the browser and summarizes results after tests finish.
 */
/*****************************************************************
* Imported Libraries
*****************************************************************/
package Tests;
import PageFactory.LoginPage;
import com.google.gson.*;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Automation Testing Class.
 */

public class LoginTests_DataProvider
{
    /**
     * Global Data.
     */
    LoginPage LoginPage_Obj;
    ChromeDriver Driver;
    ChromeOptions Options;
    JsonArray TestData;

    /**
     * Setup method that runs before tests.
     * Loads the JSON test data and initializes the browser driver.
     */

    @BeforeClass
    void Setup() throws FileNotFoundException
    {
        //1. Init Web Driver Options
        Options = new ChromeOptions();
        Options.addArguments("--incognito");
        Options.addArguments("user-data-dir=C:/temp/chrome-profile");

        //2. Set System Property
        System.setProperty("webdriver.chrome.driver","D:\\Automation Testing\\IntelliJ Projects\\SauceDemo\\src\\test\\java\\Resources\\BrowserDriver\\chromedriver.exe");
        Driver = new ChromeDriver(Options);

        //3. Navigate to required website
        Driver.navigate().to("https://www.saucedemo.com/");

        //4.  Read TCs from Json Database
        FileReader Reader = new FileReader("D:\\Automation Testing\\IntelliJ Projects\\SauceDemo\\src\\test\\java\\Resources\\Input_TCs_Database\\Login_TCs_Database.json");
        TestData = JsonParser.parseReader(Reader).getAsJsonArray();
    }

    /**
     * Data provider method.
     * This used to run each test case separately & continue with next even it fails.
     */
    @DataProvider(name = "logindata")
    public Object[][] ReadJson()
    {
        //1. Init Data Object.
        Object[][] Data = new Object[TestData.size()][1];
        for(int Counter = 0;Counter<TestData.size();Counter++)
        {
            Data[Counter][0] = Counter;
        }
        return Data;
    }

    /**
     * Test Login Method
     * This used to run test cases.
     */
    @Test(dataProvider ="logindata")
    public void TestLogin(int Index) throws InterruptedException, IOException
    {
        //1. Init Variables
        LoginPage_Obj = new LoginPage(Driver);
        JsonObject Login_TC = TestData.get(Index).getAsJsonObject();
        String Username = Login_TC.get("Username").getAsString();
        String Password = Login_TC.get("Password").getAsString();
        String ExpectedURL = Login_TC.get("ExpectedURL").getAsString();
        String TC_Result = "Failed";
        Login_TC.addProperty("LastTestResult",TC_Result);

        //2. Fill & Verify Data
        LoginPage_Obj.TypeUsername(Username);
        LoginPage_Obj.VerifyUsername(Username);

        LoginPage_Obj.TypePassword(Password);
        LoginPage_Obj.VerifyPassword(Password);

        LoginPage_Obj.Click_Login();
        LoginPage_Obj.VerifyNextURL(ExpectedURL);
        Thread.sleep(2000);

        //3. Check if ExpectedURL Still at login or not.
        if(Driver.getCurrentUrl().contains("inventory.html"))
        {
            //4. Logout.
            Driver.findElement(By.id("react-burger-menu-btn")).click();
            Thread.sleep(2000);
            Driver.findElement(By.id("logout_sidebar_link")).click();

            //5. Verify success logout
            LoginPage_Obj.VerifyLogout("https://www.saucedemo.com/");
        }

        //6. Update TC_Result to Pass
        TC_Result = "Passed";

        //7. Update TestData & Write it in Database
        Login_TC.addProperty("LastTestResult",TC_Result);
        FileWriter Database_Writer = new FileWriter("D:\\Automation Testing\\IntelliJ Projects\\SauceDemo\\src\\test\\java\\Resources\\Input_TCs_Database\\Login_TCs_Database.json");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        gson.toJson(TestData, Database_Writer);

        //8. Close Database
        Database_Writer.close();

    }

    @AfterTest
    void EndTests() throws IOException {
        //1. Init Variables
        JsonArray Passed_TCs = new JsonArray();
        JsonArray Failed_TCs = new JsonArray();

        //2. Close Web Driver
        Driver.quit();

        //3. Write Passed & Failed TCs into their Json Arrays
        for(var TC:TestData)
        {
            String TC_Result = TC.getAsJsonObject().get("LastTestResult").getAsString();
            if(TC_Result.equals("Passed"))
            {
                Passed_TCs.add(TC);
            }
            else if(TC_Result.equals("Failed"))
            {
                Failed_TCs.add(TC);
            }
        }

        //4. Write Arrays into their Json Arrays
        FileWriter Passed_TCs_Writer = new FileWriter("D:\\Automation Testing\\IntelliJ Projects\\SauceDemo\\src\\test\\java\\Resources\\Output_TCs_Database\\Passed_TCs_Database.json");
        FileWriter Failed_TCs_Writer = new FileWriter("D:\\Automation Testing\\IntelliJ Projects\\SauceDemo\\src\\test\\java\\Resources\\Output_TCs_Database\\Failed_TCs_Database.json");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        gson.toJson(Passed_TCs, Passed_TCs_Writer);
        gson.toJson(Failed_TCs,Failed_TCs_Writer);

        Passed_TCs_Writer.close();
        Failed_TCs_Writer.close();
    }
}