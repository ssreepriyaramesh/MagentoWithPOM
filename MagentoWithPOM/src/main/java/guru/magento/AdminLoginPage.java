package guru.magento;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import testutil.BasePage;

public class AdminLoginPage extends BasePage{

	AdminLoginPage(WebDriver driver) {
		super(driver);
		logger.info("In the AdminLoginPage");
	}
	
	private By loc_txtUserName = By.id("username");
	private By loc_txtPasswd = By.id("login");
	private By loc_btnLogin = By.xpath("//input[@value='Login']");
	
	public AdminHomePage logIntoAccount(String strUserName, String strPasswd)
	{
		WebElement txtUserName = driver.findElement(loc_txtUserName);
		txtUserName.clear();
		txtUserName.sendKeys(strUserName);
		
		WebElement txtPasswd = driver.findElement(loc_txtPasswd);
		txtPasswd.clear();
		txtPasswd.sendKeys(strPasswd);
		
		driver.findElement(loc_btnLogin).click();
		
		return new AdminHomePage(driver);
	}
	
	
	
	
	
}
