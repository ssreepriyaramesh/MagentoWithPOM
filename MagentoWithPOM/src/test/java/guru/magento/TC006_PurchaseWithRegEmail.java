package guru.magento;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.Random;

import org.testng.annotations.Test;

import testutil.CustomSoftAssert;
import testutil.TestBaseClass;
import testutil.TestURL;
import testutil.TestVal;


/*Test Case:
6. Verify user is able to purchase product using registered email id(Use Chrome Browser)

Test Steps:
1. Go to http://live.guru99.com/
2. Click on my account link. 
3. Login to application by creating credentials. 
4. Click on MY WISHLIST link. 
5. In next page, click Add To Cart link. 
6. Click Proceed to Checkout. //Site requirements are different
7. Enter Shipping information. Shipping information: Country = United States,
State = New York, Zip = 542896, Address = ABC, City = New York, Telephone = 12345678.
8. Click Estimate. 
9. Verify Shipping cost generated. 
10. Select Shipping Cost, Update Total. 
11. Verify shipping cost is added to total. 
12. Click "Proceed to Checkout".
13. Enter billing information. 
14. In shipping method, click Continue. 
15. In payment information, select 'Check/Money Order radio button. Click Continue.
16. Click 'Place Order' button. 
17. Verify order is generated. Note the order number. 

Expected Results:
1. Flat Rate Shipping of $5 is generated. 
2. Shipping cost is added to total product cost. 
3. Order is placed. Order number is generated. */



public class TC006_PurchaseWithRegEmail extends TestBaseClass{

		CustomSoftAssert csa = new CustomSoftAssert();
		HashMap<String, String> hmap_UserInfo = null;
		HashMap<String,String> hmapOrderNumAndMsg = null;
		MyAcctPage myAcctPg = null;
		MyWishListPage myWLPg = null;
		
		@Test(priority = 1, description = "create an account", groups = { "functional testing" })
		public void registerNewUser() {
			
			logger.info("In the TC006_PurchaseWithRegEmail Class - registerNewUser");

			driver.get(TestURL.ECOM_SITE);

			SiteHomePage siteHmPg = new SiteHomePage(driver);
			
			hmap_UserInfo = new HashMap<String, String>();
			
			Random rdm = new Random();
			int num = rdm.nextInt(10000);
			String strUserFN = "UserFN" + num;
			String strUserLN = "UserLN" + num;
			String strUserEmail = "UserFN" + num + "@abcdmail.com";
			
			hmap_UserInfo.put("UserFN", strUserFN);
			hmap_UserInfo.put("UserLN", strUserLN);
			hmap_UserInfo.put("UserEmail", strUserEmail);
			hmap_UserInfo.put("UserPasswd", "password123");
			
			SiteAcctPage siteAcctPg = siteHmPg.goToAccountPage();
			CreateAcctPage crAcctPg = siteAcctPg.goToCreateAcctPage();
			myAcctPg  = crAcctPg.createNewUser(hmap_UserInfo);
			
			String strAcctPgTitle = myAcctPg.verifyTitle();
			String strWelcMsg = myAcctPg.verifyWelcomeMsg();
			
			logger.info("Account page title: " + strAcctPgTitle);
			logger.info("Account page welcome message: " + strWelcMsg);
			csa.assertEquals(strAcctPgTitle, TestVal.MY_ACCOUNT_TITLE);
			csa.assertAll();
			
			boolean bWelcName = false;
			String strExpWelcMsg = "Hello, " + hmap_UserInfo.get("UserFN") + " " + hmap_UserInfo.get("UserLN") + "!";
			logger.info("Expected Welcome Message: " + strExpWelcMsg);
			if(strWelcMsg.contains(strExpWelcMsg))
			{
				bWelcName = true;
				logger.info("The Account page welcome message has the user name and is registered!");
			}
			csa.assertTrue(bWelcName);
			csa.assertAll();
		}
		
		@Test(priority = 2, dependsOnMethods = "registerNewUser", description = "create wish list", groups = { "functional testing" })
		public void createWishList() 
		{
			logger.info("In the TC006_NewAcctAndMsg Class - createWishList");
			
			TVPage tvPg = myAcctPg.goToTVPage();
			MyWishListPage myWLPg = tvPg.addProdToWishList("LG LCD");
			myWLPg.acctLogOut();
			
		}
		
		@Test(priority = 3, dependsOnMethods="createWishList", description = "Purchase wishlisted product with registered userEmail", groups = { "functional testing" })
		public void purchaseWLProdWithRegEmail() throws InterruptedException 
		{
			logger.info("In the TC006_NewAcctAndMsg Class - purchaseWLProdWithRegEmail");
			
			driver.get(TestURL.ECOM_SITE);

			SiteHomePage siteHmPg = new SiteHomePage(driver);
			SiteAcctPage siteAcctPg = siteHmPg.goToAccountPage();
			MyAcctPage myAcctPg = siteAcctPg.logIntoAccount(hmap_UserInfo);
			
			MyWishListPage myWLPg = myAcctPg.goToMyWishListPage();
			ShoppingCartPage shopCartPg = myWLPg.addProdToCart();
			
			hmap_UserInfo.put("Zip", "542896");
			hmap_UserInfo.put("Country", "United States");
			hmap_UserInfo.put("State", "New York");
			HashMap<String,String> hmapUpdateTotal = shopCartPg.estimateShipping(hmap_UserInfo);
			boolean bUpdateTot = checkUpdateTotal(hmapUpdateTotal);
			csa.assertTrue(bUpdateTot);
			csa.assertAll();
			
			CheckOutPage chkOutPg = shopCartPg.proceedToCheckOut();
			
			hmap_UserInfo.put("Address", "ABC");
			hmap_UserInfo.put("City", "New York");
			hmap_UserInfo.put("Telephone", "12345678");
			chkOutPg.enterBillingInfo(hmap_UserInfo);
			String strFlatRate = chkOutPg.continueShippingMethod();
			double dblFlatRate = Double.parseDouble(strFlatRate.substring(1));
			csa.assertEquals(dblFlatRate, 5.00);
			csa.assertAll();
			if(dblFlatRate == 5.00)
			{
				logger.info("The flat rate of $5 has been verified!");
			}
			chkOutPg.continuePaymentInfo("Check/MoneyOrder");
			HashMap<String,String> hmapOrderDet = chkOutPg.reviewOrder();
			boolean bPriceCheck = checkOrderPrice(hmapOrderDet);
			csa.assertTrue(bPriceCheck);
			csa.assertAll();
			
			OrderInfoPage orderInfoPg = chkOutPg.placeOrder();
			
			hmapOrderNumAndMsg = orderInfoPg.getOrderNumAndMsg();
			logger.info("The Actual Order Message is: " + hmapOrderNumAndMsg.get("OrderMsg").toUpperCase());
			logger.info("The Order Number is: " + hmapOrderNumAndMsg.get("OrderNum"));
			csa.assertEquals(hmapOrderNumAndMsg.get("OrderMsg").toUpperCase(), TestVal.ORDER_SUCCESS_MSG);
			csa.assertAll();
			
		}
		
		@Test(priority = 4, dependsOnMethods="purchaseWLProdWithRegEmail", description = "save user credentials and order number as a file", groups = { "functional testing" })
		public void generateUserAndOrderInfo() throws IOException  
		{
			logger.info("In the TC006_NewAcctAndMsg Class - generateUserAndOrderInfo");
			
			Properties prop = new Properties();
			prop.setProperty("UserEmail", hmap_UserInfo.get("UserEmail"));
			prop.setProperty("UserPasswd", hmap_UserInfo.get("UserPasswd"));
			prop.setProperty("OrderNum", hmapOrderNumAndMsg.get("OrderNum"));

			File f = new File(System.getProperty("user.dir") + "\\UserOrderInfo.properties");
			FileOutputStream fileOut = new FileOutputStream(f);
			prop.store(fileOut, "UserOrderInfo");
			fileOut.close();
		}
		
		boolean checkOrderPrice(HashMap<String,String> hmapOrderDet)
		{
			String strSubTotal = hmapOrderDet.get("SubTotal");
			String strShipCost = hmapOrderDet.get("ShipCost");
			String strGrandTotal = hmapOrderDet.get("GrandTotal");
			
			double dblSubTotal = Double.parseDouble(strSubTotal.substring(1));
			double dblShipTotal = Double.parseDouble(strShipCost.substring(1));
			double dblGrandTotal = Double.parseDouble(strGrandTotal.substring(1));
			
			boolean bPriceCheck = false;
			if(dblSubTotal+dblShipTotal == dblGrandTotal)
			{
				bPriceCheck = true;
				logger.info("Grand Total was verified successfully!");
			}
			
			return bPriceCheck;
		}
		
		boolean checkUpdateTotal(HashMap<String,String> hmapUpdateTot)
		{
			String strSubTotal = hmapUpdateTot.get("Subtotal");
			String strShipCost = hmapUpdateTot.get("Ship Cost");
			String strGrandTotal = hmapUpdateTot.get("Grand Total");
			
			double dblSubTotal = Double.parseDouble(strSubTotal.substring(1));
			double dblShipTotal = Double.parseDouble(strShipCost.substring(1));
			double dblGrandTotal = Double.parseDouble(strGrandTotal.substring(1));
			
			boolean bPriceCheck = false;
			if(dblSubTotal+dblShipTotal == dblGrandTotal)
			{
				bPriceCheck = true;
				logger.info("Update Total was verified successfully!");
			}
			
			return bPriceCheck;
			
		}
		
}
