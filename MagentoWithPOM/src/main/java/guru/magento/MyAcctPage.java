package guru.magento;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import testutil.BasePage;

public class MyAcctPage extends BasePage{

	MyAcctPage(WebDriver driver)
	{
		super(driver);
		logger.info("In the MyAccountPage");
	}
	
	private By loc_ptxtWelcMsg = By.xpath("//p[@class='hello']");
	private By loc_lnkTVpage = By.xpath("//a[contains(text(),'TV')]");
	private By loc_lnkMyWishList = By.xpath("//div[@class='block-content']//a[contains(text(),'My Wishlist')]");
	private By loc_lnkMyOrders = By.xpath("//div[@class='block-content']/descendant::a[contains(text(),'My Orders')]");
			
	public String verifyTitle()
	{
		return driver.getTitle();
	}
	
	public String verifyWelcomeMsg()
	{
		return driver.findElement(loc_ptxtWelcMsg).getText();
	}
	
	public TVPage goToTVPage()
	{
		driver.findElement(loc_lnkTVpage).click();
		return new TVPage(driver);
	}
	
	public MyWishListPage goToMyWishListPage()
	{
		driver.findElement(loc_lnkMyWishList).click();
		return new MyWishListPage(driver);
	}
	
	public MyOrdersPage goToMyOrdersPage() 
	{
		driver.findElement(loc_lnkMyOrders).click();
		return new MyOrdersPage(driver);
	}
	
	public AcctDashBrdPage getAcctDashBrdPage()
	{
		return new AcctDashBrdPage(driver);
	}
	
}
