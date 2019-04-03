package guru.magento;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import testutil.BasePage;

public class MyWishListPage extends BasePage{

	MyWishListPage(WebDriver driver)
	{
		super(driver);
		logger.info("In the MyWishListPage");
	}
	
	private By loc_btnShareWList = By.xpath("//span[contains(text(),'Share Wishlist')]");
	private By loc_ptxtShareWLMsg = By.xpath("//li[@class='success-msg']");
	private By loc_mnuAccount = By.xpath("//span[@class='label'][contains(text(),'Account')]");
	private By loc_mnuitLogOut = By.xpath("//a[@title='Log Out']");
	private By loc_btnAddToCart1 = By.xpath("//table[@id='wishlist-table']/tbody/tr[1]/td[5]/child::div[@class='cart-cell']/child::button[@title='Add to Cart']");
	
	public ShareWishListPage goToShareWishListPage()
	{
		driver.findElement(loc_btnShareWList).click();;
		return new ShareWishListPage(driver);
	}
	
	public String getShareWishListMsg()
	{
		return driver.findElement(loc_ptxtShareWLMsg).getText();		
	}
	
	public void acctLogOut()
	{
		driver.findElement(loc_mnuAccount).click();
		driver.findElement(loc_mnuitLogOut).click();
	}
	
	public ShoppingCartPage addProdToCart()
	{
		driver.findElement(loc_btnAddToCart1).click();;
		return new ShoppingCartPage(driver);
	}

}
