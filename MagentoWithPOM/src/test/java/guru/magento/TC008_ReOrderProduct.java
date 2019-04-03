package guru.magento;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;

import org.testng.annotations.Test;

import testutil.CustomSoftAssert;
import testutil.TestBaseClass;
import testutil.TestURL;
import testutil.TestVal;

/*Test Case:
8. Verify you are able to change or reorder previously added product. 

Test Steps:
1. Go to http:live.guru99.com/
2. Click on my account link. 
3. Login to application using previously created credential.
4. Click on 'REORDER' link, change QTY and click Update. QTY = 10.
5. Verify Grand Total is changed.
6. Complete Billing and Shipping information.
7. Verify order is generated and note the order number.

Expected Results:
1. Grand Total is changed.
2. Order number is generated.*/

public class TC008_ReOrderProduct extends TestBaseClass{
	
	CustomSoftAssert csa = new CustomSoftAssert();
	HashMap<String, String> hmapUserOrderInfo = new HashMap<String,String>();
	OrderDetPage orderDetPg = null;
	
	@Test(priority = 1, description = "read the previous login credential", groups = { "functional testing" })
	public void readAndGetCredentials() throws IOException {
		
		logger.info("In the TC008_ReOrderProduct Class - readAndGetCredentials");
		
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
	
	@Test(priority = 2, dependsOnMethods = "readAndGetCredentials", description = "re-order product", groups = { "functional testing" })
	public void reOrderProduct(){
		
		logger.info("In the TC008_ReOrderProduct Class - reOrderProduct");

		driver.get(TestURL.ECOM_SITE);

		SiteHomePage siteHmPg = new SiteHomePage(driver);
		SiteAcctPage siteAcctPg = siteHmPg.goToAccountPage();
		MyAcctPage myAcctPg = siteAcctPg.logIntoAccount(hmapUserOrderInfo);
		AcctDashBrdPage acctDashBrdPg = myAcctPg.getAcctDashBrdPage();
		
		ShoppingCartPage shopCartPg = acctDashBrdPg.reOrderProduct(hmapUserOrderInfo.get("OrderNum"));
		String strIGrandTotal = shopCartPg.getGrandTotal();
		shopCartPg.updateProdQty("10");
		String strUpGrandTotal = shopCartPg.getGrandTotal();
		
		boolean bGrandTotalUpdated = false;
		if(!(strIGrandTotal == strUpGrandTotal))
		{
			bGrandTotalUpdated = true;
			logger.info("Grand Total is Updated. Grand Total: " + strUpGrandTotal);
		}
		csa.assertTrue(bGrandTotalUpdated);
		csa.assertAll();
		
		CheckOutPage chkOutPg = shopCartPg.proceedToCheckOut();
		chkOutPg.continueBillingInfo();
		chkOutPg.continueShippingMethod();
		chkOutPg.continuePaymentInfo("Check/MoneyOrder");
		OrderInfoPage orderInfoPg = chkOutPg.placeOrder();
		
		HashMap<String,String> hmapOrderNumAndMsg = orderInfoPg.getOrderNumAndMsg();
		logger.info("The Actual Order Message is: " + hmapOrderNumAndMsg.get("OrderMsg").toUpperCase());
		logger.info("The Order Number is: " + hmapOrderNumAndMsg.get("OrderNum"));
		csa.assertEquals(hmapOrderNumAndMsg.get("OrderMsg").toUpperCase(), TestVal.ORDER_SUCCESS_MSG);
		csa.assertAll();

	}
}
