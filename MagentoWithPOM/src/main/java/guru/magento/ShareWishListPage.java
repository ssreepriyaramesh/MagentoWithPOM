package guru.magento;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pageutil.BasePage;

public class ShareWishListPage extends BasePage{

	ShareWishListPage(WebDriver driver)
	{
		super(driver);
		logger.info("In the ShareWishListPage");
	}
	
	private By loc_txtaEmailAddrs = By.id("email_address");
	private By loc_txtaEmailMsg = By.id("message");
	private By loc_btnShareWList = By.xpath("//span[contains(text(),'Share Wishlist')]");
	
	public MyWishListPage shareWishList(String strEmailAddrs, String strEmailMsg)
	{
		WebElement txtaEmailAddrs = driver.findElement(loc_txtaEmailAddrs);
		txtaEmailAddrs.clear();
		txtaEmailAddrs.sendKeys(strEmailAddrs);
		
		WebElement txtaEmailMsg = driver.findElement(loc_txtaEmailMsg);
		txtaEmailMsg.clear();
		txtaEmailMsg.sendKeys(strEmailMsg);
		
		driver.findElement(loc_btnShareWList).click();
		
		return new MyWishListPage(driver);
	}
	
}
