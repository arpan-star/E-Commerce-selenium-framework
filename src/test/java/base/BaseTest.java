package base;

import java.lang.reflect.Method;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import io.github.bonigarcia.wdm.WebDriverManager;
import utilities.ConfigReader;
import utils.ExtentManager;
import utils.ScreenshotUtil;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

public class BaseTest {

    protected WebDriver driver;
    ConfigReader config;
    protected static ExtentReports extent = ExtentManager.getInstance();
    protected ExtentTest test;
    
    @BeforeSuite
    public void setupReporter() {
    	
    	extent.setSystemInfo("Project", "E-Commerce E2E flow");
        extent.setSystemInfo("Tester", "Arpan Mahapatra");
        extent.setSystemInfo("Browser", config != null ? config.getProperty("browser") : "Configured");
        extent.setSystemInfo("Environment", "QA");
    }

    @BeforeMethod
    public void setUp(Method method) {
    	test = extent.createTest(method.getName());
    	
    	config = new ConfigReader();
    	String browser = config.getProperty("browser");
    	String headless = config.getProperty("headless");
    	
    	if (browser.equalsIgnoreCase("chrome")) {
    		ChromeOptions options = new ChromeOptions();
    	    if(headless.equalsIgnoreCase("true")) {
    	        options.addArguments("--headless=new");
    	    }
    	    driver = new ChromeDriver(options);
        } 
        else if (browser.equalsIgnoreCase("edge")) {
        	EdgeOptions options = new EdgeOptions();
            if(headless.equalsIgnoreCase("true")) {
                options.addArguments("--headless=new");
            }
            driver = new EdgeDriver(options);
        }

        driver.manage().window().maximize();
        driver.get(config.getProperty("url"));
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
    	if(result.getStatus() == ITestResult.FAILURE) {

            String screenshotPath = ScreenshotUtil.captureScreenshot(driver, result.getName());

            test.fail(result.getThrowable());
            test.addScreenCaptureFromPath(screenshotPath);
        }

        else if(result.getStatus() == ITestResult.SUCCESS) {
            test.pass("Test Passed");
        }

        else {
            test.skip("Test Skipped");
        }
    	
        driver.quit();
    }
    
    @AfterSuite
    public void flushReport() {
    	extent.flush();
    }
}