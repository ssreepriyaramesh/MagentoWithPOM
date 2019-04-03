package guru.magento;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import org.testng.annotations.Test;

import testutil.CustomSoftAssert;
import testutil.TestBaseClass;
import testutil.TestURL;
import testutil.TestVal;

/*Test Case:
5. Verify you can create account in E-commerce site and can share wish list to other people using email. 

Test Steps:
1. Go to http://live.guru99.com/
2. Click on my account link. 
3. Click Create Account link and fill New User information except email ID. 
4. Click Register
5. Verify Registration is done. 
6. Go to TV menu. 
7. Add product in your wish list (product = LG LCD)
8. Click SHARE WISHLIST. 
9. In next page enter email and a message and click SHARE WISHLIST. 
10. Check wish list is shared. 

Expected Results:
1. Account Registration done. 
2. Wish list shared successfully. */


public class TC005_NewAcctAndMsg extends TestBaseClass{

	CustomSoftAssert csa = new CustomSoftAssert();
	HashMap<String, String> hmap_UserInfo = null;
	MyAcctPage myAcctPg = null;
	MyWishListPage myWLPg = null;
	
	@Test(priority = 1, description = "create an account", groups = { "functional testing" })
	public void registerNewUser() {
		
		logger.info("In the TC005_NewAcctAndMsg Class - registerNewUser");

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
	}
	
	@Test(priority = 2, dependsOnMethods = "registerNewUser", description = "create wish list", groups = { "functional testing" })
	public void createWishList() 
	{
		logger.info("In the TC005_NewAcctAndMsg Class - createWishList");
		
		TVPage tvPg = myAcctPg.goToTVPage();
		myWLPg = tvPg.addProdToWishList("LG LCD");
		
	}
	
	@Test(priority = 3, dependsOnMethods = "createWishList", description = "email wish list", groups = { "functional testing" })
	public void EmailWishList() 
	{
		logger.info("In the TC005_NewAcctAndMsg Class - EmailWishList");
		ShareWishListPage shWLPg= myWLPg.goToShareWishListPage();
		
		String strEmailAddrs = "ssreepriyaramesh@gmail.com, spriyars@gmail.com";
		String strEmailMsg = "This is my magento wishlist! Thank you.";
		myWLPg = shWLPg.shareWishList(strEmailAddrs, strEmailMsg);
		String strShareWLMsg = myWLPg.getShareWishListMsg();
		
		logger.info("The Share WishList Message: " + strShareWLMsg);
		csa.assertEquals(strShareWLMsg, TestVal.SHARED_WISHLIST_MSG);
		csa.assertAll();
		
		myWLPg.acctLogOut();
	}
}