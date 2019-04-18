package guru.magento;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import pageutil.BasePage;

public class OrderDetPage extends BasePage{

	OrderDetPage(WebDriver driver)
	{
		super(driver);
		logger.info("In the OrderDetPage");
	}
	
	private By loc_ptxtOrdStatMsg = By.xpath("//div[@class='page-title title-buttons']/h1");
	private By loc_lnkPrintOrder = By.xpath("//a[@class='link-print'][contains(text(),'Print Order')]");
	
	public String getOrderStatusMsg()
	{
		return driver.findElement(loc_ptxtOrdStatMsg).getText();
	}
	
	public void printOrderAndSaveFile() throws InterruptedException, IOException
	{
		String strParentWindowID = driver.getWindowHandle();
		logger.info("The window title: " + driver.getTitle());
		driver.findElement(loc_lnkPrintOrder).click();
		
		Thread.sleep(3000);
		Runtime.getRuntime().exec(System.getProperty("user.dir") + "//PrintOrderScript.exe");
		Thread.sleep(20000);
	}
	
	
}
