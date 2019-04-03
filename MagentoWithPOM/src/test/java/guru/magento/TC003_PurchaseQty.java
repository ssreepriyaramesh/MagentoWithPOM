package guru.magento;

import java.util.HashMap;

import org.testng.annotations.Test;

import testutil.CustomSoftAssert;
import testutil.TestBaseClass;
import testutil.TestURL;
import testutil.TestVal;

/*Test Case:
3. Verify that you cannot add more product in cart than the product available in store. 

Test Steps:
1. Go to http://live.guru99.com/
2. Click on 'MOBILE' menu.
3. In the list of all mobile, click on 'ADD TO CART' for Sony Xperia mobile.
4. Change 'QTY' value to 1000 and click 'UPDATE' button.
5. Verify the error message.
6. Then click on 'EMPTY CART' link in the footer of list of all mobiles.
7. Verify cart is empty. 

Expected Results:
1. On clicking update button an error is shown 'The requested quantity for "Sony Xperia"
is not available.'
2. On clicking empty cart button - a message 'SHOPPING CART IS EMPTY' is shown.

*/

public class TC003_PurchaseQty extends TestBaseClass {

	CustomSoftAssert csa = new CustomSoftAssert();

	@Test(priority = 1, description = "check the purchase qty of a product", groups = { "functional testing" })
	public void checkProdPurchaseQty() {
		
		logger.info("In the TC003_PurchaseQty Class");
		
		driver.get(TestURL.ECOM_SITE);

		SiteHomePage siteHmPg = new SiteHomePage(driver);
		MobilePage mobiPg = siteHmPg.clickMobiLink();

		ShoppingCartPage shopCartPg = mobiPg.addProdToCart("Sony Xperia");
		String strProdQty = "501";
		HashMap<String, String> map_DisplayMsg = shopCartPg.updateProdQty(strProdQty);

		if (Integer.parseInt(strProdQty) > 500) {
			
			String strItemErrMsg = map_DisplayMsg.get("ItemErrMsg").trim();
			logger.info("Item Err Msg: " + strItemErrMsg);
			logger.info("Asserting Item Error Message Text!");
			csa.assertEquals(strItemErrMsg, TestVal.ITEM_ERR_MSG_TEXT);
			csa.assertAll();

			String strErrMsg = map_DisplayMsg.get("ErrMsg").trim();
			logger.info("Err Msg: " + strErrMsg);
			logger.info("Asserting Error Message Text!");
			csa.assertEquals(strErrMsg, TestVal.ERR_MSG_TEXT);
			csa.assertAll();
		}
		
		 EmptyCartPage empCartPg = shopCartPg.emptyProdCart();
		 
		 String strCartMsg = empCartPg.getCartMsg();
		 logger.info("Asserting Empty Cart Message Text!");
		 csa.assertTrue(strCartMsg.trim().equals(TestVal.EMPTY_CART_MSG_TEXT));
		 csa.assertAll();
	}

}
