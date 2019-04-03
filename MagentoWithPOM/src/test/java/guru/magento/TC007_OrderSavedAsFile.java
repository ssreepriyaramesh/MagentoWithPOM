package guru.magento;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;

import org.testng.annotations.Test;

import testutil.CustomSoftAssert;
import testutil.TestBaseClass;
import testutil.TestURL;

/*Test Case:
7. Verify that you will be able to save previously placed order as a PDF file. 

Test Steps:
1. Go to http://live.guru99.com/ 
2. Click on My Account link. 
3. Login to application using previously created credential. 
4. Click on 'My Orders'. 
5. Click on 'View Order'. 
6. Verify the previously created order is displayed in 'RECENT ORDERS' table and status is Pending. 
7. Click on 'Print Order' link. 
8. Verify order is saved as PDF. 

Expected Results:
1. Previously created order is displayed in 'RECENT ORDERS' table and status is Pending. 
2. Order is saved as PDF. 

Note:
Testcases 6 and 7 are related to flow one after the other. 
*
*/

public class TC007_OrderSavedAsFile extends TestBaseClass{
	
	CustomSoftAssert csa = new CustomSoftAssert();
	HashMap<String, String> hmapUserOrderInfo = new HashMap<String,String>();
	OrderDetPage orderDetPg = null;
	
	@Test(priority = 1, description = "read the previous login credential", groups = { "functional testing" })
	public void readAndGetCredentials() throws IOException {
		
		logger.info("In the TC007_OrderSavedAsFile Class - readAndGetCredentials");
		
		Properties prop = new Properties();
		File f = new File(System.getProperty("user.dir") + "\\UserOrderInfo.properties");
		FileInputStream fis = new FileInputStream(f);
		prop.load(fis);
		
		hmapUserOrderInfo.put("UserEmail", prop.getProperty("UserEmail"));
		hmapUserOrderInfo.put("UserPasswd", prop.getProperty("UserPasswd"));
		hmapUserOrderInfo.put("OrderNum", prop.getProperty("OrderNum"));
		
		Set<String> ks = hmapUserOrderInfo.keySet();
		for(String key : ks)
		{
			logger.info(key + " " + hmapUserOrderInfo.get(key));
		}
	}
	
	@Test(priority = 2, dependsOnMethods = "readAndGetCredentials", description = "verify previous order", groups = { "functional testing" })
	public void verifyOrder(){
		
		logger.info("In the TC007_OrderSavedAsFile Class - verifyOrderAndSaveFile");

		driver.get(TestURL.ECOM_SITE);

		SiteHomePage siteHmPg = new SiteHomePage(driver);
		SiteAcctPage siteAcctPg = siteHmPg.goToAccountPage();
		MyAcctPage myAcctPg = siteAcctPg.logIntoAccount(hmapUserOrderInfo);
		MyOrdersPage myOrdersPg = myAcctPg.goToMyOrdersPage();
		orderDetPg = myOrdersPg.viewOrder(hmapUserOrderInfo);
		String strOrderStatMsg = orderDetPg.getOrderStatusMsg();
		logger.info("The Order Status Msg: " + strOrderStatMsg);
		
		boolean bOrderStat = false;
		if(strOrderStatMsg.contains(hmapUserOrderInfo.get("OrderNum")) && strOrderStatMsg.contains("PENDING"))
		{
			bOrderStat = true;
			logger.info("The Order Number and the Order Status have been verified as Pending!");
		}
		
		csa.assertTrue(bOrderStat);
		csa.assertAll();
	}
	
	@Test(priority = 3, dependsOnMethods = "verifyOrder", description = "save order as file", groups = { "functional testing" })
	public void saveOrderAsFile() throws InterruptedException, IOException{
		
		logger.info("In the TC007_OrderSavedAsFile Class - saveOrderAsFile");
		orderDetPg.printOrderAndSaveFile();
		logger.info("Orders file saved to the folder: C:\\JavaProjects\\MagentoWithPOM");
	}
}
