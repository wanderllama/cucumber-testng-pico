package jw.demo.util.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

public class BaseDriver {

    private WebDriver driver;

    void setDriver(String b) {
        if (driver == null) {
            String driverDir = "user-data-dir=/Library/tools/tools/chromedriver/current/mac64arm/";
            b = !b.isBlank() ? b : "chrome";
            System.out.printf("Browser: %s%n", b);
            if (b.equals("chrome-linux")) b = "chrome";
            switch (b) {
                case "chrome":
                    ChromeOptions co = new ChromeOptions();
                    co.addArguments("--disable-extensions");
                    co.addArguments("--disable-web-security");
                    co.addArguments("--test-type");
                    co.addArguments(driverDir + "chromedriver-mac-arm64/chromedriver");
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver();
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                    break;
                case "edge":
                    WebDriverManager.edgedriver().setup();
                    driver = new EdgeDriver();
                    break;
                case "safari":
                    WebDriverManager.getInstance(SafariDriver.class).setup();
                    driver = new SafariDriver();
                    break;
//                default:
//                    throw new WebDriverException("%s is an invalid browser".formatted(b));
            }
        }
    }

    WebDriver getD() {
        return driver;
    }

}
