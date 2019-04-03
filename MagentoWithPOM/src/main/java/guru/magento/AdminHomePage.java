package guru.magento;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pageutil.BasePage;

public class AdminHomePage extends BasePage{

	AdminHomePage(WebDriver driver) {
		super(driver);
		logger.info("In the AdminHomePage");
	}
	
	private By loc_popupMsg = By.xpath("//div[@id='message-popup-window']");
	private By loc_btnPopupClose = By.xpath("//span[contains(text(),'close')]");
	
	private By loc_mnuSales = By.xpath("//span[contains(text(),'Sales')]");
	private By loc_mnuitOrders = By.xpath("//span[contains(text(),'Orders')]");
	
	
	public void closeAnyMessages()
	{
		try
		{
			WebElement popupMsg = driver.findElement(loc_popupMsg);
			popupMsg.findElement(loc_btnPopupClose).click();
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public AdminOrdersPage goToOrdersPage()
	{
		
		WebElement mnuSales = driver.findElement(loc_mnuSales);
		new Actions(driver).moveToElement(mnuSales).perform();
		
		WebElement mnuitOrders = new WebDriverWait(driver,5).until(ExpectedConditions.elementToBeClickable(loc_mnuitOrders));
		new Actions(driver).moveToElement(mnuitOrders).click().perform();
		
		return new AdminOrdersPage(driver);
	}
	
	
	
	
	
}
