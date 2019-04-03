package guru.magento;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import testutil.BasePage;

public class CreateAcctPage extends BasePage{

	CreateAcctPage(WebDriver driver)
	{
		super(driver);
		logger.info("In the CreateAccountPage");
	}
	
	By loc_txtUserFN = By.id("firstname");
	By loc_txtUserLN = By.id("lastname");
	By loc_txtUserEmail = By.id("email_address");
	By loc_txtUserPasswd = By.id("password");
	By loc_txtUserConfPasswd = By.id("confirmation");
	By loc_btnRegister = By.xpath("//span[contains(text(),'Register')]");
	
	
	public MyAcctPage createNewUser(HashMap<String, String> hmap_UserInfo)
	{
		WebElement txt_UserFN = driver.findElement(loc_txtUserFN);
		txt_UserFN.clear();
		txt_UserFN.sendKeys(hmap_UserInfo.get("UserFN"));
		
		WebElement txt_UserLN = driver.findElement(loc_txtUserLN);
		txt_UserLN.clear();
		txt_UserLN.sendKeys(hmap_UserInfo.get("UserLN"));
		
		WebElement txt_UserEmail = driver.findElement(loc_txtUserEmail);
		txt_UserEmail.clear();
		txt_UserEmail.sendKeys(hmap_UserInfo.get("UserEmail"));
		
		WebElement txt_UserPasswd = driver.findElement(loc_txtUserPasswd);
		txt_UserPasswd.clear();
		txt_UserPasswd.sendKeys(hmap_UserInfo.get("UserPasswd"));
		
		WebElement txt_UserConfPasswd = driver.findElement(loc_txtUserConfPasswd);
		txt_UserConfPasswd.clear();
		txt_UserConfPasswd.sendKeys(hmap_UserInfo.get("UserPasswd"));
		
		driver.findElement(loc_btnRegister).click();
		
		return new MyAcctPage(driver);
		
	}
	
	
	
	
	
	
	
	
}
