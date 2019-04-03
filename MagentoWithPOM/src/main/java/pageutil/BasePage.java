package pageutil;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BasePage {
	
	protected WebDriver driver;
	protected Logger logger;

	protected BasePage(WebDriver driver){
		this.driver = driver;
		logger = Logger.getLogger("BasePage");
        PropertyConfigurator.configure("log4j.properties");
        logger.info("In the BasePage Class");
	}
        
}
