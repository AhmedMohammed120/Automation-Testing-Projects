package sauselab.sauselab.pagefactory;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class HomePage 
{
	WebDriver Driver;
	By AddToCart_Btn = By.id("add-to-cart-sauce-labs-backpack");
	By RemoveFromCart_Btn = By.id("remove-sauce-labs-backpack");
	By ShoppingCartLink_Btn = By.className("shopping_cart_badge");
	By ShoppingCartLink_Lnk = By.xpath("//a[@Class=\"shopping_cart_link\"]");
	
	public HomePage(WebDriver DriverCpy)
	{
		Driver = DriverCpy;
	}
	
	public void Click_AddToCart()
	{
		Driver.findElement(AddToCart_Btn).click();
	}
	
	public void Verifiy_AddToCart()
	{
		Driver.findElement(RemoveFromCart_Btn).isDisplayed();
	}
	
	public void Click_ShoppingCartBtn()
	{
		Driver.findElement(ShoppingCartLink_Lnk).click();
	}

}
