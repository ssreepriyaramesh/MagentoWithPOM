package guru.magento;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import pageutil.BasePage;

public class TVPage extends BasePage{
	
	TVPage(WebDriver driver)
	{
		super(driver);
		logger.info("In the TVPage");
	}
	
	public MyWishListPage addProdToWishList(String strProdName)
	{
		String strProdXpath = "//a[contains(text(),'" + strProdName + "')]/ancestor::div[@class='product-info']/descendant::div[@class='actions']/ul/li/a[contains(text(),'Add to Wishlist')]";
		driver.findElement(By.xpath(strProdXpath)).click();

		return new MyWishListPage(driver);
	}
	
}
