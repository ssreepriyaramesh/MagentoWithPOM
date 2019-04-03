package guru.magento;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import pageutil.BasePage;

public class AdminOrdersPage extends BasePage{

	AdminOrdersPage(WebDriver driver) {
		super(driver);
		logger.info("In the AdminOrdersPage");
	}
	
	private By loc_selOrdDLFileFmt = By.xpath("//td[@class='export a-right']/descendant::select[@id='sales_order_grid_export']");
	private By loc_btnExportOrder = By.xpath("//td[@class='export a-right']/descendant::button[@title='Export']");
	
	public void downloadOrdersFile(String strFileFormat) throws InterruptedException
	{
		//WebDriverWait w = new WebDriverWait(driver,5);
		//w.until(ExpectedConditions.presenceOfElementLocated(loc_selOrdDLFileFmt));
				
		Select selOrdDLFileFmt = new Select(driver.findElement(loc_selOrdDLFileFmt));
		selOrdDLFileFmt.selectByVisibleText(strFileFormat);
		
		driver.findElement(loc_btnExportOrder).click();
		
	}
	
	
	
}
