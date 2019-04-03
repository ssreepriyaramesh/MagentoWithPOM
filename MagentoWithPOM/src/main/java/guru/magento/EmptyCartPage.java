package guru.magento;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pageutil.BasePage;

public class EmptyCartPage extends BasePage{
	
	EmptyCartPage(WebDriver driver) {
		super(driver);
		logger.info("In the EmptyCartPage");
	}
	
	private By loc_ptxtEmpCartMsg = By.xpath("//h1[contains(text(),'Shopping Cart is Empty')]");
	
	public String getCartMsg()
	{
		WebElement ptxtEmpCartMsg = driver.findElement(loc_ptxtEmpCartMsg);
		logger.info("The Empty Cart Msg: " + ptxtEmpCartMsg.getText());
		return ptxtEmpCartMsg.getText();
	}
	
	
	
	
}
