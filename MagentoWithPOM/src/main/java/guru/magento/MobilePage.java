package guru.magento;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import testutil.BasePage;

public class MobilePage extends BasePage{
	
	MobilePage(WebDriver driver) {
		super(driver);
		logger.info("In the MobilePage");
		
	}

	private By loc_headerTxt = By.xpath("//h1[contains(text(),'Mobile')]");
	private By loc_selSortBy = By.xpath(
			"//div[@class='category-products']/child::div[@class='toolbar']/child::div[@class='sorter']/child::div[@class='sort-by']/child::select[contains(@title,'Sort By')]");
	private By loc_lstProductsOnPg = By.className("product-name");
    private By loc_btnCompare = By.xpath("//button[@title='Compare']//span//span[contains(text(),'Compare')]");
    private By loc_lstCompProd = By.xpath("//tr[@class='product-shop-row top first odd']/descendant::h2[@class='product-name']");
	

	public String getTitle() {
		logger.info("The title of the current page is: " + driver.getTitle());
		return driver.getTitle();
	}

	public String getHeaderText() {
		String strHeaderTxt = driver.findElement(loc_headerTxt).getText();
		logger.info("The header text of the current page is: " + strHeaderTxt);
		return strHeaderTxt;
	}

	public void sortProdBy(String strSortBy) {
		Select selSortBy = new Select(driver.findElement(loc_selSortBy));
		selSortBy.selectByVisibleText(strSortBy);
	}

	public ArrayList<String> getProdListNames() {
		List<WebElement> products = driver.findElements(loc_lstProductsOnPg);
		logger.info("The number of products on the page is :" + products.size());
		ArrayList<String> prodNames = new ArrayList<String>();

		for (WebElement product : products) {
			prodNames.add(product.getText());
			logger.info("The product is: " + product.getText());
		}

		return prodNames;
	}

	public String getMobileLstPrice(String strPhoneName) {
		logger.info("Mobile Name: " + strPhoneName);
		String strPhonePriceXpath = "//a[@title='" + strPhoneName
				+ "']/parent::h2/following-sibling::div/span[starts-with(@id, 'product-price-')]";
		logger.info("The phone price xpath is: " + strPhonePriceXpath);
		WebElement lnk_PhoneLstPrice = driver.findElement(By.xpath(strPhonePriceXpath));
		String strPhoneLstPrice = lnk_PhoneLstPrice.getText();
		logger.info("Mobile List Price: " + strPhoneLstPrice);

		return strPhoneLstPrice;
	}

	public MobileDetPage clickMobileName(String strPhoneName) {
		String strPhoneXpath = "//a[@title='" + strPhoneName + "']";
		logger.info("The phone name xpath: " + strPhoneXpath);
		driver.findElement(By.xpath(strPhoneXpath)).click();
		return new MobileDetPage(driver);
	}
	
	public ShoppingCartPage addProdToCart(String strProdName)
	{
		String strAddProdXpath = "//a[@title='" + strProdName + "'][contains(text(),'" + strProdName + "')]/ancestor::div[@class='product-info']/child::div[@class='actions']/button[contains(@title,'Add to Cart')]";
		driver.findElement(By.xpath(strAddProdXpath)).click();
		
		return new ShoppingCartPage(driver);
	}
	
	public ArrayList<String> compareProducts(String strProdName1, String strProdName2) throws InterruptedException
	{
		String strParentWindowId = driver.getWindowHandle();
		logger.info("The parent window id: " + strParentWindowId);
		
		String strProd1Xpath = "//a[contains(text(),'" + strProdName1 + "')]/ancestor::div[@class='product-info']/child::div[@class='actions']/child::ul/li[2]/a[@class='link-compare' and contains(text(),'Add to Compare')]";
		logger.info("The product1 xpath: " + strProd1Xpath);
		driver.findElement(By.xpath(strProd1Xpath)).click();
		
		String strProd2Xpath = "//a[contains(text(),'" + strProdName2 + "')]/ancestor::div[@class='product-info']/child::div[@class='actions']/child::ul/li[2]/a[@class='link-compare' and contains(text(),'Add to Compare')]";
		logger.info("The product2 xpath: " + strProd2Xpath);
		driver.findElement(By.xpath(strProd2Xpath)).click();	
		
		driver.findElement(loc_btnCompare).click();
		
		List<WebElement> lst_ProdToCompare = null;
		ArrayList<String> actlst_CompProd = new ArrayList<String>();
		String strChildWindowTitle = null;
		String strCompProd = null;
		try
		{
			for (String windowId : driver.getWindowHandles())
			{
				strChildWindowTitle = driver.switchTo().window(windowId).getTitle();
				logger.info("The pop up window title is: " + strChildWindowTitle );
				Thread.sleep(3000);
				if(strChildWindowTitle.contains("Products Comparison List - Magento Commerce"))
				{
					lst_ProdToCompare = driver.findElements(loc_lstCompProd);
				}
			}
			
			logger.info("The products to compare in the actual list are: ");
			for (WebElement we_prod : lst_ProdToCompare)
			{
				strCompProd = we_prod.getText();
				actlst_CompProd.add(strCompProd);
				logger.info(strCompProd);
			}
		}
		finally
		{
			driver.switchTo().window(strParentWindowId);
		}
		
		return actlst_CompProd;
	}

	
	public ArrayList<String> compareProducts(ArrayList<String> lst_strProdNames) throws InterruptedException
	{
		String strParentWindowId = driver.getWindowHandle();
		logger.info("The parent window id: " + strParentWindowId);
		
		String strProdName = null;
		String strProdXpath = null;
		for (int i = 0; i < lst_strProdNames.size(); i++)
		{
			strProdName = lst_strProdNames.get(i);	
			strProdXpath = "//a[contains(text(),'" + strProdName + "')]/ancestor::div[@class='product-info']/child::div[@class='actions']/child::ul/li[2]/a[@class='link-compare' and contains(text(),'Add to Compare')]";
			driver.findElement(By.xpath(strProdXpath)).click();
		}
		
		driver.findElement(loc_btnCompare).click();
	
		List<WebElement> lst_ProdToCompare = null;
		ArrayList<String> actlst_CompProd = new ArrayList<String>();
		String strChildWindowTitle = null;
		String strCompProd = null;
		try
		{
			for (String windowId : driver.getWindowHandles())
			{
				driver.switchTo().window(windowId);
				strChildWindowTitle = driver.getTitle();
				logger.info("The pop up window title is: " + strChildWindowTitle );
				Thread.sleep(3000);
				if(strChildWindowTitle.contains("Products Comparison List - Magento Commerce"))
				{
					lst_ProdToCompare = driver.findElements(loc_lstCompProd);
				}
			}
			
			logger.info("The products to compare in the actual list are: ");
			for (int i = 0; i < lst_ProdToCompare.size(); i++)
			{
				strCompProd = lst_ProdToCompare.get(i).getText();
				actlst_CompProd.add(strCompProd);
				logger.info(strCompProd);
			}
		}
		finally
		{
			driver.switchTo().window(strParentWindowId);
		}
		
		return actlst_CompProd;
	
	}
}
