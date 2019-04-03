package guru.magento;

import java.util.HashMap;
import java.util.Set;

import org.testng.annotations.Test;

import testutil.CustomSoftAssert;
import testutil.TestBaseClass;
import testutil.TestURL;

/*Test Case
9. Verify Discount Coupon works correctly.

Test Steps:
1. Go to http://live.guru99.com/
2. Go to Mobile and add IPHONE to cart.
3. Enter Coupon code. Coupon code: GURU50. 
4. Verify the discount generated.

Expected Results:
1. Price is discounted by 5%. */

public class TC009_DiscountCoupon extends TestBaseClass{
	CustomSoftAssert csa = new CustomSoftAssert();

	@Test(priority = 1, description = "verify discount coupon price", groups = {
			"functional testing" })
	public void verifyDiscountCoupon() {
		
		logger.info("TC009_DiscountCoupon Class - verifyDiscountCoupon");
		driver.get(TestURL.ECOM_SITE);

		SiteHomePage siteHmPg = new SiteHomePage(driver);
		MobilePage mobiPg = siteHmPg.clickMobiLink();
		
		ShoppingCartPage shopCartPg = mobiPg.addProdToCart("IPhone");
		String strDisctCode = "GURU50";
		shopCartPg.applyDiscountCode(strDisctCode);
		HashMap<String,String> hmapDisctDet = shopCartPg.getDiscountDetails();
		Set<String> setDisctKeys = hmapDisctDet.keySet();
		
		for(String strKey : setDisctKeys)
		{
			logger.info("The Discount Details: " + strKey + " " + hmapDisctDet.get(strKey));
		}
		
		checkDiscountDetails(strDisctCode, hmapDisctDet);
		
	}
	
	void checkDiscountDetails(String strDisctCode, HashMap<String,String> hmapDisctDet)
	{
		
		double actdblSubtotal = Double.parseDouble(hmapDisctDet.get("Subtotal").substring(1));
		double actdblDiscount = Double.parseDouble(hmapDisctDet.get("Discount").substring(2));
		double actdblGrandTotal = Double.parseDouble(hmapDisctDet.get("Grand Total").substring(1));				
		double expdblGrandTotal = 0;
		double expdblDiscount = 0;
		double dblDisctPC = 0;
		if (strDisctCode == "GURU50")
		{
			dblDisctPC = 5;
		}
		
		expdblDiscount = actdblSubtotal * (dblDisctPC/100);
		logger.info("The expected discount: " + expdblDiscount);
		expdblGrandTotal = actdblSubtotal - expdblDiscount;
		logger.info("The expected Grand Total: " + expdblGrandTotal);
		
		csa.assertEquals(actdblDiscount, expdblDiscount);
		csa.assertEquals(actdblGrandTotal, expdblGrandTotal);
		csa.assertAll();
		
	}
}
