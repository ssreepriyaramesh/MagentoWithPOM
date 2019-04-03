package guru.magento;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import testutil.BasePage;

public class AcctDashBrdPage extends BasePage{

	AcctDashBrdPage(WebDriver driver)
	{
		super(driver);
		logger.info("In the AcctDashBrdPage");
	}
	
	public ShoppingCartPage reOrderProduct(String strOrderNum)
	{
		//table[@id='my-orders-table']/tbody/tr/td[contains(text(),'100009059')]/following-sibling::td/span/a[contains(text(),'Reorder')]
		String strReOrderXpath = "//table[@id='my-orders-table']/tbody/tr/td[contains(text(),'" + strOrderNum + "')]/following-sibling::td/span/a[contains(text(),'Reorder')]";
		logger.info("The ReOrder Xpath: " + strReOrderXpath );
		driver.findElement(By.xpath(strReOrderXpath)).click();
		
		return new ShoppingCartPage(driver);
		
	}
	
}
