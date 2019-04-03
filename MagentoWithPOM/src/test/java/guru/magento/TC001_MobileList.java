package guru.magento;


import java.util.ArrayList;

import org.testng.annotations.Test;

import testutil.CustomSoftAssert;
import testutil.TestBaseClass;
import testutil.TestURL;
import testutil.TestVal;

/*Test Case:
 
1. Verify item in Mobile List page can be sorted by 'Name'. 

Test Steps:
1. Go to http://live.guru99.com/
2. Verify Title of the page.
3. Click on 'MOBILE' menu. 
4. Verify Title of the page. 
5. In the list of all mobile, select 'SORT BY' dropdown as 'name'. 
6. Verify all products are sorted by name. 

Expected Results:
1. Text 'THIS IS DEMO SITE' shown in home page. 
2. Title 'MOBILE' is shown on mobile list page. 
3. All 3 products sorted by name. 

*/

public class TC001_MobileList extends TestBaseClass {

	CustomSoftAssert csa = new CustomSoftAssert();
	
	@Test(priority = 1, description = "verify mobile list sorted by name", groups = {"functional testing"})
	public void verifyPageTitle()
	{
		logger.info("In the TC001_MobileList Class");
		driver.get(TestURL.ECOM_SITE);
		
		SiteHomePage siteHP = new SiteHomePage(driver);
		
		String strSiteHPTitle = siteHP.getTitle();
		csa.assertEquals("Home page", driver.getTitle());
		
		MobilePage mobiPg = siteHP.clickMobiLink();
		String strMobiPgTitle = mobiPg.getTitle();
		csa.assertEquals("Mobile", driver.getTitle());
		
		String strMobiPgHeaderTitle = mobiPg.getHeaderText();
		//System.out.println(strMobiPgHeaderTitle);
		//System.out.println(TestVal.MOBILE_PAGE_HEADER_TEXT);
		csa.assertEquals(strMobiPgHeaderTitle, TestVal.MOBILE_PAGE_HEADER_TEXT);
	
		//Verify if the products are sorted by Name
		mobiPg.sortProdBy("Name");
		ArrayList<String> prodNames = mobiPg.getProdListNames();
		boolean bInOrder = checkProdListOrder(prodNames);	
		csa.assertTrue(bInOrder);
		
		
		
		csa.assertAll();
		
	}
	
	
	boolean checkProdListOrder(ArrayList prodNames){
		
		boolean bInOrder = false;
		String prodName2 = null;
		int j = 0;
		
		for (int i = 0; i < (prodNames.size()-1); i++ )
		{
			String prodName1 = (String) prodNames.get(i);
			
			j = i + 1;
			//System.out.println("i = " + i);
			//System.out.println("j = " + j);
			prodName2 = (String) prodNames.get(j);
						
			if(prodName2.compareTo(prodName1) >= 0)
			{
				//System.out.println(prodName1 + " is in order before "  + prodName2);
				if  (j == (prodNames.size()-1))
				{
					System.out.println("All products are sorted by Name!");
					bInOrder = true;
				}
			}
			else
			{
				System.out.println("Error: The product names are not sorted in order!");
				bInOrder = false;
				break;
			}	
		}
		return bInOrder;
	}
	
}
