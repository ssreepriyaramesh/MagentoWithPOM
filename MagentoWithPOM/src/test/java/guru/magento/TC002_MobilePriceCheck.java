package guru.magento;

import java.util.ArrayList;

import org.testng.annotations.Test;

import guru.magento.MobilePage;
import guru.magento.SiteHomePage;
import testutil.CustomSoftAssert;
import testutil.TestBaseClass;
import testutil.TestURL;
import testutil.TestVal;

/*
 * Test Case:
	2. Verify that cost of product in list page and details pages are equal. 
	
Test Steps:
	1. Go to http://live.guru99.com/
	2. Click on 'MOBILE' menu
	3. In the list of all mobile, read the cost of Sony Xperia mobile. Note this value.
	4. Click on Sony Xperia mobile.
	5. Read the Sony Xperia mobile from detail page.
	6. Compare value in Step 3 and Step 5.

Expected Results:
	1) Product Value in list and details page should be equal($100). 

*/

public class TC002_MobilePriceCheck extends TestBaseClass {
	
	CustomSoftAssert csa = new CustomSoftAssert();

	@Test(priority = 1, description = "compare the mobile list and detailed page prices", groups = {
			"functional testing" })
	public void checkMobilePrices() {
		
		logger.info("In the TC002_MobilePriceCheck Class");
		driver.get(TestURL.ECOM_SITE);

		SiteHomePage siteHmPg = new SiteHomePage(driver);
		MobilePage mobiPg = siteHmPg.clickMobiLink();
		
		String strMobileName = "Sony Xperia";
		String strPhoneLstPrice = mobiPg.getMobileLstPrice(strMobileName);
		MobileDetPage mobiDetPg = mobiPg.clickMobileName(strMobileName);
		String strPhoneDetPrice = mobiDetPg.getMobileDetPrice();
		boolean bCheck = compareMobilePrices(strPhoneLstPrice, strPhoneDetPrice);
		//Check if the Phone's List Price and Detailed Price are same
		csa.assertTrue(bCheck, "The Phone List and Detailed Page Price Check Failed!");
		csa.assertAll();

	}

	boolean compareMobilePrices(String strPhoneLstPrice, String strPhoneDetPrice) {
		// Compare the Phone prices on the List page with the Details page
		boolean bCheck = false;

		char c1 = strPhoneLstPrice.charAt(0);
		if (!Character.isDigit(c1)) {
			strPhoneLstPrice = strPhoneLstPrice.substring(1);
		}
		
		double dblPhone_LP = Double.parseDouble(strPhoneLstPrice);

		char c2 = strPhoneDetPrice.charAt(0);
		if (!Character.isDigit(c2)) {
			strPhoneDetPrice = strPhoneDetPrice.substring(1);
		}
		
		double dblPhone_DP = Double.parseDouble(strPhoneDetPrice);

		if (dblPhone_LP == dblPhone_DP) {
			System.out.println(
					"The Phone price on the List page is the same as that on the Details Page - Verification Passed!");
			bCheck = true;
		} else {
			System.out.println(
					"The Phone price on the List page is not the same as that on the Details Page - Verification Failed!");
			bCheck = false;
		}
		return bCheck;

	}
}
