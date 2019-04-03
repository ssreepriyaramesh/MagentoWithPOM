package guru.magento;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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
2. Pop up window is closed. 

Difference: Comparison of 2 or more products at a time.
*
*/
public class TC004_1_CompareProducts extends TestBaseClass{

	CustomSoftAssert csa = new CustomSoftAssert();
	
	@Test(priority = 1, description = "compare the selected products", groups = { "functional testing" })
	public void checkCompareProducts() throws InterruptedException {
		
		logger.info("In the TC004_1_CompareProducts Class");
		
		driver.get(TestURL.ECOM_SITE);

		SiteHomePage siteHmPg = new SiteHomePage(driver);
		MobilePage mobiPg = siteHmPg.clickMobiLink();
		
		ArrayList explst_ProdToComp = new ArrayList<String>(Arrays.asList("Sony Xperia", "Samsung Galaxy", "IPhone"));
		ArrayList<String> actlst_ProdToComp = mobiPg.compareProducts(explst_ProdToComp);
		
		ArrayList<String> explst_PRODTOCOMP = new ArrayList<String>();
		for(int i = 0; i < explst_ProdToComp.size(); i++)
		{
			explst_PRODTOCOMP.add(((String)explst_ProdToComp.get(i)).toUpperCase());
		}
		Collections.sort(actlst_ProdToComp);
		Collections.sort(explst_PRODTOCOMP);
		
		logger.info("The actual comparison list: " + actlst_ProdToComp);
		logger.info("The expected comparison list: " + explst_PRODTOCOMP);
		
		csa.assertEquals(actlst_ProdToComp, explst_PRODTOCOMP);
		csa.assertAll();
		
	}
	
}
