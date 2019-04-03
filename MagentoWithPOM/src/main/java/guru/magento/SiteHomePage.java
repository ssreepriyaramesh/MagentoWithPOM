package guru.magento;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pageutil.BasePage;

public class SiteHomePage extends BasePage{
	
	SiteHomePage(WebDriver driver)
	{
		super(driver);
		logger.info("In the SiteHomePage");
	}
	
	private By loc_lnkMobilePg = By.xpath("//a[contains(text(),'Mobile')]");
	private By loc_mnuAccount = By.xpath("//span[@class='label'][contains(text(),'Account')]");
	private By loc_mnuitMyAcct = By.xpath("//div[@id='header-account']//a[@title='My Account'][contains(text(),'My Account')]");
	
	public String getTitle()
	{
		logger.info("The title of the page: " + driver.getTitle());
		return driver.getTitle();
	}
	
	public MobilePage clickMobiLink()
	{
		
		WebElement mobiLink = driver.findElement(loc_lnkMobilePg);
		mobiLink.click();
		return new MobilePage(driver);
	}
	
	public SiteAcctPage goToAccountPage()
	{
		driver.findElement(loc_mnuAccount).click();
		driver.findElement(loc_mnuitMyAcct).click();
		
		return new SiteAcctPage(driver);
	}
}
