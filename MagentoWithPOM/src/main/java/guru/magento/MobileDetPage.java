package guru.magento;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import testutil.BasePage;

public class MobileDetPage extends BasePage{
	
	MobileDetPage(WebDriver driver)
	{
		super(driver);
		logger.info("In the MobileDetPage");
	}
	
	private By loc_PhoneDetPrice = By.xpath("//span[@class='price']");
	
	
	public String getMobileDetPrice()
	{
		
		WebElement txtPhoneDetPrice = driver.findElement(loc_PhoneDetPrice);
		String strPhoneDetPrice = txtPhoneDetPrice.getText();
		logger.info("Mobile Detailed Price: " + strPhoneDetPrice);
		return strPhoneDetPrice;
	}
	
	
}
