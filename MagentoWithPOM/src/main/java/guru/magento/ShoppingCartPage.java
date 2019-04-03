package guru.magento;

import java.util.HashMap;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import testutil.BasePage;

public class ShoppingCartPage extends BasePage{

	ShoppingCartPage(WebDriver driver) {
		super(driver);
		logger.info("In the ShoppingCartPage");
	}

	private By loc_txtProdQty = By.xpath("//input[@title='Qty']");
	private By loc_btnUpdateQty = By
			.xpath("//button[@title='Update' and @value='update_qty']//span//span[contains(text(),'Update')]");
	private By loc_ptxtItemErrMsg = By.xpath("//p[@class='item-msg error']");
	private By loc_ptxtErrMsg = By.xpath(
			"//li[@class='error-msg']/ul/li/span");
	private By loc_btnEmptyCart = By
			.xpath("//button[@id='empty_cart_button']/span/span[contains(text(),'Empty Cart')]");
	private By loc_btnCheckOut = By.xpath("//ul[@class='checkout-types top']/li/button[@title='Proceed to Checkout']");
	
	private By loc_selCountry = By.xpath("//div[@class='shipping']/descendant::select[@id='country']");
	private By loc_selState = By.xpath("//div[@class='shipping']/descendant::select[@id='region_id']");
	private By loc_txtShipZip = By.xpath("//div[@class='shipping']/descendant::input[@id='postcode']");
	private By loc_btnEstimate = By.xpath("//div[@class='shipping']/descendant::button[@title='Estimate']");
	
	private By loc_rbtnFlatRate = By.xpath("//input[@id='s_method_flatrate_flatrate']");
	private By loc_btnUpdateTotal = By.xpath("//button[@title='Update Total']");
	
	private By loc_txtDisctCpCode = By.id("coupon_code");
	private By loc_btnApplyDisCount = By.xpath("//span[contains(text(),'Apply')]");
	
	private By loc_ptxtSubtotal = By.xpath("//table[@id='shopping-cart-totals-table']/tbody/tr/td[contains(text(),'Subtotal')]/following-sibling::td/span[@class='price']");
	private By loc_ptxtShipCost = By.xpath("//table[@id='shopping-cart-totals-table']/tbody/tr/td[contains(text(),'Shipping & Handling')]/parent::tr/td/span[@class='price']");
	private By loc_ptxtDiscount = By.xpath("//table[@id='shopping-cart-totals-table']/tbody/tr/td[contains(text(),'Discount')]/following-sibling::td/span[@class='price']");
	private By loc_ptxtGrandTotal = By.xpath("//table[@id='shopping-cart-totals-table']/tfoot/tr/td/strong[contains(text(),'Grand Total')]/parent::td/following-sibling::td/strong/span[@class='price']");
		
			
	public HashMap updateProdQty(String strProdQty) {
		HashMap<String, String> map_DisplayMsg = new HashMap<String, String>();

		WebElement txtProdQty = driver.findElement(loc_txtProdQty);
		txtProdQty.clear();
		txtProdQty.sendKeys(strProdQty);

		WebElement btnUpdateQty = driver.findElement(loc_btnUpdateQty);
		btnUpdateQty.click();

		if (Integer.parseInt(strProdQty) > 500) {
			
			WebElement ptxt_ItemErrMsg = driver.findElement(loc_ptxtItemErrMsg);
			logger.info("The ItemErrMsg: " + ptxt_ItemErrMsg.getText());
			map_DisplayMsg.put("ItemErrMsg", ptxt_ItemErrMsg.getText());

			WebElement ptxt_ErrMsg = driver.findElement(loc_ptxtErrMsg);
			logger.info("The ErrMsg: " + ptxt_ErrMsg.getText());
			map_DisplayMsg.put("ErrMsg", ptxt_ErrMsg.getText());
		}
		
		return map_DisplayMsg;
	}

	public EmptyCartPage emptyProdCart() {
		driver.findElement(loc_btnEmptyCart).click();
		return new EmptyCartPage(driver);
	}
	
	public CheckOutPage proceedToCheckOut()
	{
		driver.findElement(loc_btnCheckOut).click();
		return new CheckOutPage(driver);
	}
	
	public String getGrandTotal()
	{
		return driver.findElement(loc_ptxtGrandTotal).getText();
	}
	
	public void applyDiscountCode(String strDisctCode)
	{
		WebElement txtDisctCpCode = driver.findElement(loc_txtDisctCpCode);
		txtDisctCpCode.clear();
		txtDisctCpCode.sendKeys(strDisctCode);
		
		driver.findElement(loc_btnApplyDisCount).click();
	}
	
	public HashMap<String,String> getDiscountDetails()
	{
		WebDriverWait w = new WebDriverWait(driver,3);
		w.until(ExpectedConditions.visibilityOfElementLocated(loc_ptxtSubtotal));
		
		HashMap<String,String> hmapDisctDet = new HashMap<String,String>();
		hmapDisctDet.put("Subtotal", driver.findElement(loc_ptxtSubtotal).getText());
		hmapDisctDet.put("Discount", driver.findElement(loc_ptxtDiscount).getText());
		hmapDisctDet.put("Grand Total", driver.findElement(loc_ptxtGrandTotal).getText());
		
		Set<String> setDisctKeys = hmapDisctDet.keySet();
		for(String strKey : setDisctKeys)
		{
			logger.info("The Discount Details: " + strKey + " " + hmapDisctDet.get(strKey));
		}
		return hmapDisctDet;
	}
	
	public HashMap<String,String> estimateShipping(HashMap<String,String> hmapShipInfo) 
	{
		Select selCountry = new Select(driver.findElement(loc_selCountry));
		selCountry.selectByVisibleText(hmapShipInfo.get("Country"));
		
		Select selState  = new Select(driver.findElement(loc_selState));
		selState.selectByVisibleText(hmapShipInfo.get("State"));
		
		WebElement txtShipZip = driver.findElement(loc_txtShipZip);
		txtShipZip.clear();
		txtShipZip.sendKeys(hmapShipInfo.get("Zip"));
		
		driver.findElement(loc_btnEstimate).click();
		
		WebElement rbtnFlatRate = driver.findElement(loc_rbtnFlatRate);
		if(!rbtnFlatRate.isSelected())
		{
			rbtnFlatRate.click();
		}
		
		driver.findElement(loc_btnUpdateTotal).click();
		
		HashMap<String,String> hmapShipDet = new HashMap<String,String>();
		hmapShipDet.put("Subtotal", driver.findElement(loc_ptxtSubtotal).getText());
		hmapShipDet.put("Ship Cost", driver.findElement(loc_ptxtShipCost).getText());
		hmapShipDet.put("Grand Total", driver.findElement(loc_ptxtGrandTotal).getText());
		
		Set<String> setShipDetKeys = hmapShipDet.keySet();
		for(String strKey : setShipDetKeys)
		{
			logger.info("The Update Total Details: " + strKey + " " + hmapShipDet.get(strKey));
		}
		return hmapShipDet;
	}

}
