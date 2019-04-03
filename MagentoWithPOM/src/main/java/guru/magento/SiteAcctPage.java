package guru.magento;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import testutil.BasePage;

public class SiteAcctPage extends BasePage {

	SiteAcctPage(WebDriver driver)
	{
		super(driver);
		logger.info("In the SiteAccountPage");
	}
	
	private By loc_btnCreateAcct = By.xpath("//span[contains(text(),'Create an Account')]");
	private By loc_txtUserEmail = By.id("email");
	private By loc_txtUserPasswd = By.id("pass");
	private By loc_btnLogin = By.xpath("//span[contains(text(),'Login')]");
	
	public CreateAcctPage goToCreateAcctPage()
	{
		driver.findElement(loc_btnCreateAcct).click();
		
		return new CreateAcctPage(driver);
	}
	
	public MyAcctPage logIntoAccount(HashMap<String, String> hmap_UserInfo)
	{
		WebElement txtUserEmail = driver.findElement(loc_txtUserEmail);
		txtUserEmail.clear();
		txtUserEmail.sendKeys(hmap_UserInfo.get("UserEmail"));
		
		WebElement txtUserPasswd = driver.findElement(loc_txtUserPasswd);
		txtUserPasswd.clear();
		txtUserPasswd.sendKeys(hmap_UserInfo.get("UserPasswd"));
		
		driver.findElement(loc_btnLogin).click();
	
		return new MyAcctPage(driver);
	}
	
	
	
	
	
}
