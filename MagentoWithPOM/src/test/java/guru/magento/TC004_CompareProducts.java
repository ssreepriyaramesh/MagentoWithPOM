package guru.magento;

import java.util.ArrayList;

import org.testng.annotations.Test;

import testutil.CustomSoftAssert;
import testutil.TestBaseClass;
import testutil.TestURL;

/*Test Case:
4. Verify that you are able to compare two product. 

Test Steps:
1. Go to http://live.guru99.com/
2. Click on 'MOBILE' menu.
3. In mobile products list, click on 'Add To Compare' for 2 mobiles.
(Phone 1 - Sony Xperia; Phone 2 - IPhone).  
4. Click on 'COMPARE' button. 
5. Verify the pop-up window and check that the products are reflected in it. 
6. Close the Pop up windows. 

Expected Results:
1. A Pop-up window opens with heading a 'COMPARE PRODUCTS' and 
the selected products are present in it.
2. Pop up window is closed. */

public class TC004_CompareProducts extends TestBaseClass{

	CustomSoftAssert csa = new CustomSoftAssert();
	String strCompProd1 = "Sony Xperia";
	String strCompProd2 = "Samsung Galaxy";

	@Test(priority = 1, description = "compare the two selected products", groups = { "functional testing" })
	public void checkCompareProducts() throws InterruptedException {
		
		logger.info("In the TC004_CompareProducts Class");

		driver.get(TestURL.ECOM_SITE);

		SiteHomePage siteHmPg = new SiteHomePage(driver);
		MobilePage mobiPg = siteHmPg.clickMobiLink();
		
		ArrayList<String> actlst_ProdToComp = mobiPg.compareProducts(strCompProd1, strCompProd2);
		
		boolean bAllProdPresent = checkCompProdPresent(actlst_ProdToComp);
		csa.assertTrue(bAllProdPresent);
		csa.assertAll();
	}
	
	boolean checkCompProdPresent(ArrayList<String> actlst_ProdToComp)
	{
		String actstrCompProd = null;
		boolean bCompProd1 = false;
		boolean bCompProd2 = false;
		boolean bAllProdPresent = false;
		for(int i=0; i<actlst_ProdToComp.size(); i++)
		{
			actstrCompProd = (String) actlst_ProdToComp.get(i);
			logger.info(actstrCompProd);
			if(actstrCompProd.equals(strCompProd1.toUpperCase()))
			{
				logger.info("Asserting for: " + strCompProd1);
				csa.assertEquals(actstrCompProd, strCompProd1.toUpperCase());
				csa.assertAll();
				logger.info("The Product Comparison Page has: " + actstrCompProd);
				bCompProd1 = true;
			}
			
			if(actstrCompProd.equals(strCompProd2.toUpperCase()))
			{
				logger.info("Asserting for: " + strCompProd2);
				csa.assertEquals(actstrCompProd, strCompProd2.toUpperCase());
				csa.assertAll();	
				logger.info("The Product Comparison Page has: " + actstrCompProd);
				bCompProd2 = true;
			}
		}
		
		if (bCompProd1 && bCompProd2)
		{
			bAllProdPresent = true;
			logger.info("All selected products to compare are present in the pop up window!");
		}
		
		return bAllProdPresent;
	}

}
