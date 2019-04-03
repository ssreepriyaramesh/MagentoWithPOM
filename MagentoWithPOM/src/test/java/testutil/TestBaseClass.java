package testutil;


import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class TestBaseClass {
	
	protected static WebDriver driver;
	
	protected Logger logger;

	@Parameters({ "browserType" }) 
	@BeforeClass(alwaysRun = true)
	public void setup(String browser) throws Exception {
		
		logger = Logger.getLogger("TestBaseClass");
        PropertyConfigurator.configure("log4j.properties");
        logger.info("In the TestBaseClass");
        
		if (browser.equals("FF")) {
			System.setProperty("webdriver.gecko.driver",
			System.getProperty("user.dir") + "//src//test//resources//geckodriver.exe");
			System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, "true");
			System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, System.getProperty("user.dir") + "//FFLog.txt");
			
			FirefoxOptions options = new FirefoxOptions();
			options.addPreference("browser.download.folderList", 2);
			options.addPreference("browser.helperApps.alwaysAsk.force", false);
			options.addPreference("browser.download.dir", "C:\\JavaProjects\\MagentoWithPOM\\Downloads"); 
			options.addPreference("browser.download.defaultFolder","C:\\JavaProjects\\MagentoWithPOM\\Downloads"); 
			options.addPreference("browser.download.manager.showWhenStarting", false);
			options.addPreference("browser.helperApps.neverAsk.saveToDisk","multipart/x-zip,application/zip,application/x-zip-compressed,application/x-compressed,application/msword,application/csv,text/csv,image/png ,image/jpeg, application/pdf, text/html,text/plain,  application/excel, application/vnd.ms-excel, application/x-excel, application/x-msexcel, application/octet-stream");
			driver = new FirefoxDriver(options);
			
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			
		} else if (browser.equals("CH")) {
			String downloadPath = "C:\\JavaProjects\\MagentoWithPOM\\Downloads";
			HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
			chromePrefs.put("profile.default_content_settings.popups", 0);
			chromePrefs.put("download.default_directory", downloadPath);
			ChromeOptions options = new ChromeOptions();
			options.setExperimentalOption("prefs", chromePrefs);
			DesiredCapabilities cap = DesiredCapabilities.chrome();
			cap.setCapability(ChromeOptions.CAPABILITY, options);	
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + "//src//test//resources//chromedriver.exe");
			driver = new ChromeDriver(cap);
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			
		} else {
			throw new Exception("Browser is not correct");
		}
	}

	@AfterClass
	public void tearDown() {
		driver.quit();
		driver = null;
	}
	
	
}