package guru.magento;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import pageutil.BasePage;

public class CheckOutPage extends BasePage{
	
	CheckOutPage(WebDriver driver) {
		super(driver);
		logger.info("In the CheckOutPage");
	}
	
	private By loc_txtUserFN = By.id("billing:firstname");
	private By loc_txtUserLN = By.id("billing:lastname");
	private By loc_txtUserAddr = By.id("billing:street1");
	private By loc_txtUserCity = By.id("billing:city");
	private By loc_selUserState = By.id("billing:region_id");
	private By loc_txtUserZip = By.id("billing:postcode");
	private By loc_selUserCountry = By.id("billing:country_id");
	private By loc_txtUserTelephone = By.id("billing:telephone");
	private By loc_btnBillContinue = By.xpath("//div[@id='billing-buttons-container']//button[@title='Continue']//span//span[contains(text(),'Continue')]");
	
	private By loc_ptxtFlatRate = By.xpath("//div[@id='checkout-shipping-method-load']/descendant::label[@for='s_method_flatrate_flatrate']/span[@class='price']");
	private By loc_btnShipMtdContinue = By.xpath("//div[@id='shipping-method-buttons-container']//button[@type='button']//span//span[contains(text(),'Continue')]");
	
	private By loc_rbtnCheckMO = By.xpath("//div[@id='checkout-step-payment']/descendant::label[@for='p_method_checkmo'][contains(text(),'Check / Money order')]");
	private By loc_rbtnCreditCard = By.xpath("//div[@id='checkout-step-payment']/descendant::label[@for='p_method_ccsave'][contains(text(),'Credit Card (saved) ')]");
	private By loc_btnPaymntInfoContinue = By.xpath("//div[@id='payment-buttons-container']//button[@type='button']//span//span[contains(text(),'Continue')]");
	
	private By loc_ptxtSubTotal = By.xpath("//table[@id='checkout-review-table']//tfoot/tr[1]/td[contains(text(),'Subtotal')]/parent::tr[1]/td[2]/span[@class='price']");
	private By loc_ptxtShipCost = By.xpath("//table[@id='checkout-review-table']//tfoot/tr[2]/td[contains(text(),'Shipping & Handling (Flat Rate - Fixed)')]/parent::tr/td[2]/span[@class='price']");
	private By loc_ptxtGrandTotal = By.xpath("//table[@id='checkout-review-table']//tfoot/tr[3]/td[1]/strong[contains(text(),'Grand Total')]/parent::td/parent::tr/td[2]/strong/span[@class='price']");
	
	private By loc_btnPlaceOrder = By.xpath("//div[@id='checkout-review-load']/descendant::button[@title='Place Order']/span/span[contains(text(),'Place Order')]");
	
	
	
	public void enterBillingInfo(HashMap<String,String> hmap_UserInfo)
	{
		WebElement txtUserFN = driver.findElement(loc_txtUserFN);
		txtUserFN.clear();
		txtUserFN.sendKeys(hmap_UserInfo.get("UserFN"));
		
		WebElement txtUserLN = driver.findElement(loc_txtUserLN);
		txtUserLN.clear();
		txtUserLN.sendKeys(hmap_UserInfo.get("UserLN"));
		
		WebElement txtUserAddr = driver.findElement(loc_txtUserAddr);
		txtUserAddr.clear();
		txtUserAddr.sendKeys(hmap_UserInfo.get("Address"));
		
		WebElement txtUserCity = driver.findElement(loc_txtUserCity);
		txtUserCity.clear();
		txtUserCity.sendKeys(hmap_UserInfo.get("City"));
		
		Select selUserState = new Select(driver.findElement(loc_selUserState));
		selUserState.selectByVisibleText(hmap_UserInfo.get("State"));
		
		WebElement txtUserZip = driver.findElement(loc_txtUserZip);
		txtUserZip.clear();
		txtUserZip.sendKeys(hmap_UserInfo.get("Zip"));
		
		Select selUserCountry = new Select(driver.findElement(loc_selUserCountry));
		selUserCountry.selectByVisibleText(hmap_UserInfo.get("Country"));
		
		WebElement txtUserTelephone = driver.findElement(loc_txtUserTelephone);
		txtUserTelephone.clear();
		txtUserTelephone.sendKeys(hmap_UserInfo.get("Telephone"));
		
		driver.findElement(loc_btnBillContinue).click();
	}
	
	public void continueBillingInfo()
	{
		driver.findElement(loc_btnBillContinue).click();
	}
	
	public String continueShippingMethod()  
	{
		String strFlatRate = driver.findElement(loc_ptxtFlatRate).getText();
	
		WebDriverWait w = new WebDriverWait(driver,3);
		WebElement we = w.until(ExpectedConditions.elementToBeClickable(loc_btnShipMtdContinue));
		driver.findElement(loc_btnShipMtdContinue).click();
		
		return strFlatRate;
	}
	
	public void continuePaymentInfo(String strPaymntType)
	{
		if(strPaymntType.equals("CreditCard"))
		{
			WebElement rbtnCreditCard = driver.findElement(loc_rbtnCreditCard);
			if(!rbtnCreditCard.isSelected())
			{
				rbtnCreditCard.click();
			}
		}
		else if(strPaymntType.equals("Check/MoneyOrder"))
		{
			WebElement rbtnCheckMO = driver.findElement(loc_rbtnCheckMO);
			if(!rbtnCheckMO.isSelected())
			{
				rbtnCheckMO.click();
			}
		}
		
		driver.findElement(loc_btnPaymntInfoContinue).click();
	}
	
	public HashMap<String,String> reviewOrder()
	{
		String strSubTotal = driver.findElement(loc_ptxtSubTotal).getText();
		String strShipCost = driver.findElement(loc_ptxtShipCost).getText();
		String strGrandTotal = driver.findElement(loc_ptxtGrandTotal).getText();
		
		HashMap<String,String> hmapOrderDet = new HashMap<String,String>();
		hmapOrderDet.put("SubTotal", strSubTotal);
		hmapOrderDet.put("ShipCost", strShipCost);
		hmapOrderDet.put("GrandTotal", strGrandTotal);
		
		return hmapOrderDet;

	}
	
	public OrderInfoPage placeOrder()
	{
		driver.findElement(loc_btnPlaceOrder).click();
		
		return new OrderInfoPage(driver);
	}
	
}
