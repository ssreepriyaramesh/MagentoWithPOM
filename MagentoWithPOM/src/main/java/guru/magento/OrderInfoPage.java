package guru.magento;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import pageutil.BasePage;

public class OrderInfoPage extends BasePage{
	
	OrderInfoPage(WebDriver driver) {
		super(driver);
		logger.info("In the OrderInfoPage");
	}
	
	private By loc_ptxtOrderMsg = By.xpath("//div[@class='page-title']/h1[contains(text(),'Your order has been received.')]");
	private By loc_lnkOrderNum = By.xpath("//div[@class='col-main']/descendant::p[contains(text(),'Your order # is: ')]/a");
	
	public HashMap<String,String> getOrderNumAndMsg()
	{
		HashMap<String,String> hmapOrderNumAndMsg = new HashMap<String,String>();
		hmapOrderNumAndMsg.put("OrderMsg", driver.findElement(loc_ptxtOrderMsg).getText());
		hmapOrderNumAndMsg.put("OrderNum", driver.findElement(loc_lnkOrderNum).getText());
		
		return hmapOrderNumAndMsg;
	}
}
