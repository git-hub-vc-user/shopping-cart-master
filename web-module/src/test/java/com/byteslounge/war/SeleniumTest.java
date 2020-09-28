package com.byteslounge.war;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class SeleniumTest {

    WebDriver driver = null;

    @SuppressWarnings("deprecation")

    @BeforeMethod

    public void setup() {

        //if using chrome
        WebDriverManager.chromedriver().version("85.0.4183.87").setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.addArguments("enable-automation");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-browser-side-navigation");
        options.addArguments("--disable-gpu");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:8090/shopping");

        //Internet Explorer
        //manage security level across all zones error in IE
		/*DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
		capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		WebDriverManager.iedriver().setup();
		driver = new InternetExplorerDriver(capabilities);
		driver.manage().window().maximize();
		//driver.get("https://www.google.com/");
		driver.get("http://localhost:8090/shopping");
		System.out.println("successfully launched");*/
    }

    @Test
    public void UrlVerification() {
        String prodname = driver.findElement(By.xpath("/html/body/table[1]/tbody/tr/th")).getText();
        System.out.println(prodname);
        if (prodname.equalsIgnoreCase("products")) {
            System.out.println("Verification of url is successful");
        } else
            System.out.println("There seems to be some issue with the url or webelements");
    }

    @AfterMethod
    public void closebrowser() {
        driver.quit();
    }
}
