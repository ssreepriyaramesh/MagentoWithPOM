package guru.magento;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.testng.annotations.Test;

import com.opencsv.CSVReader;

import testutil.CustomSoftAssert;
import testutil.TestBaseClass;
import testutil.TestURL;

/*Test Case:
10. Export all orders in csv file and display these information in console 
and you are able to send this file to another email id as attachment. 

Test Steps:
1. Go to http://live.guru99.com/index.php/backendlogin/ (id = "user01"
password = "guru99com"). 
2. Go to Sales > Orders menu. 
3. Select 'CSV' in Export to drop down and click Export button. 
5. Read down loaded file and display all order information in console window. 
6. Attach this exported file and email to another email id. 

Expected Results:
1. Console displays all order information.
2. Email is sent successfully.


 */

public class TC010_ExportOrdersFileAndEmail extends TestBaseClass {

	CustomSoftAssert csa = new CustomSoftAssert();
	String strDownloadFileFmt = null;

	@Test(priority = 1, description = "export Orders file and display it in the console", groups = {
			"functional testing" })
	public void exportOrdersFileAndDisplay() throws InterruptedException, IOException {

		logger.info("TC010_ExportOrdersFileAndEmail - exportOrdersFileAndDisplay");
		driver.get(TestURL.ADMIN_SITE);

		AdminLoginPage adminlogPg = new AdminLoginPage(driver);
		AdminHomePage adminHmPg = adminlogPg.logIntoAccount("user01", "guru99com");
		adminHmPg.closeAnyMessages();
		AdminOrdersPage adminOrdPg = adminHmPg.goToOrdersPage();
		strDownloadFileFmt = "CSV";
		adminOrdPg.downloadOrdersFile(strDownloadFileFmt);

		Thread.sleep(10000);

		if (strDownloadFileFmt.equals("CSV")) {
			String FILE_PATH = "C:\\JavaProjects\\MagentoWithPOM\\Downloads\\orders.csv";

			CSVReader reader = new CSVReader(new FileReader(FILE_PATH));
			List<String[]> val = reader.readAll();
			logger.info("Total number of rows in CSV file: " + val.size());

			Iterator<String[]> itList = val.iterator();

			while (itList.hasNext()) {
				String[] arrValues = itList.next();
				for (int i = 0; i < arrValues.length; i++) {
					System.out.print("     " + arrValues[i]);
				}
				System.out.println("");
			}
		}
	}

	@Test(priority = 2, description = "email Orders file", groups = { "functional testing" })
	public void emailOrdersFile() throws EmailException {

		logger.info("TC010_ExportOrdersFileAndEmail - emailOrdersFile");

		// Create the email message
		MultiPartEmail email = new MultiPartEmail();

		email.setHostName("smtp.googlemail.com");
		email.setSmtpPort(465);
		email.setAuthenticator(new DefaultAuthenticator("priya.readersprouts@gmail.com", ""));
		email.setSSL(true);
		email.addTo("ssreepriyaramesh@gmail.com", "SreePriyaRamesh");
		email.setFrom("priya.readersprouts@gmail.com", "Priya Ramesh");
		email.setSubject("Orders List");
		email.setMsg("Please find Orders List attached.");

		// Create the attachment
		EmailAttachment attachment = null;
		String EMAIL_ATTCHMT_PATH = null;
		
		if (strDownloadFileFmt.equals("CSV")) {
			EMAIL_ATTCHMT_PATH = "C:\\JavaProjects\\MagentoWithPOM\\Downloads\\orders.csv";
			attachment = new EmailAttachment();
			attachment.setPath(EMAIL_ATTCHMT_PATH);
			attachment.setDisposition(EmailAttachment.ATTACHMENT);
			attachment.setDescription("Orders List");
			attachment.setName("Orders.csv");
		}
		// Add the attachment
		email.attach(attachment);
		logger.info("File attachment done!");

		// Send the email
		email.send();
		logger.info("Email sent!");

	}

}
