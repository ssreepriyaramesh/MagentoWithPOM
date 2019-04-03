package guru.magento;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import pageutil.BasePage;

public class MyOrdersPage extends BasePage{

	MyOrdersPage(WebDriver driver)
	{
		super(driver);
		logger.info("In the MyOrdersPage");
	}
	
	public OrderDetPage viewOrder(HashMap<String,String> hmapUserOrderInfo)
	{
		String strOrderNum = hmapUserOrderInfo.get("OrderNum");
		//table[@id='my-orders-table']/tbody/tr/td[contains(text(),'100009059')]/following-sibling::td/span/a[contains(text(),'View Order')]
		String strViewOrdXpath = "//table[@id='my-orders-table']/tbody/tr/td[contains(text(),'" + strOrderNum + "')]/following-sibling::td/span/a[contains(text(),'View Order')]";
		logger.info("View Order Xpath: " + strViewOrdXpath);
		
		driver.findElement(By.xpath(strViewOrdXpath)).click();
		
		return new OrderDetPage(driver);
		
	}
}
