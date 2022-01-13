package shopizer;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class AbstractTest {

	//protected EBrowser browser = EBrowser.chrome;// chrome par défaut
	//protected String mainPageAdress = "http://192.168.1.64:8080";
	//Properties for maven
	protected String mainPageAdress = System.getProperty("mainPage");
	protected String browser = System.getProperty("browser");
	
	protected WebDriver driver;
	WebDriverWait wait;
	JavascriptExecutor js;

	public void selectBrowser(String i) {
		if (i.equalsIgnoreCase("chrome")) {
			// chemin du driver
			System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
			// Initialisation du navigateur Chrome
			driver = new ChromeDriver();
			driver.manage().window().maximize();

		} else if (i.equalsIgnoreCase("firefox")) {
			// chemin du driver
			System.setProperty("webdriver.gecko.driver", "src/test/resources/drivers/geckodriver.exe");
			// Initialisation du navigateur Firefox
			driver = new FirefoxDriver();
			driver.manage().window().maximize();

		} else if (i.equalsIgnoreCase("edge")) {
			// chemin du driver
			System.setProperty("webdriver.edge.driver", "src/test/resources/drivers/msedgedriver.exe");
			// Initialisation du navigateur Edge
			driver = new EdgeDriver();
			driver.manage().window().maximize();
		}
		
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
		wait = new WebDriverWait(driver, 30);
		js = (JavascriptExecutor) driver;
		
		
		
	}
	
}
