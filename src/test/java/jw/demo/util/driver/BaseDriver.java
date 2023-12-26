package jw.demo.util.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import jw.demo.constantsAndEnums.BrowserType;
import jw.demo.util.TestContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static jw.demo.constantsAndEnums.BrowserType.*;
import static jw.demo.constantsAndEnums.Constants.BROWSER;

public class BaseDriver {

    private WebDriver driver;

    private BrowserType browserIs;

    void setBrowser() {
        String b = TestContext.getProperties().getProperty(BROWSER).trim().toLowerCase();
        switch (b) {
            case "chrome-linux":
            case "chrome":
                browserIs = CHROME;
                break;
            case "firefox":
                browserIs = FIREFOX;
                break;
            case "edge":
                browserIs = EDGE;
                break;
            default:
                System.out.printf("%s is not a valid browser property used in configuration properties file", b);
        }
    }

    void createDriver() {
        if (driver == null) {
            System.out.printf("Browser: %s%n", browserIs);
            switch (browserIs) {
                case CHROME:
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver();
                    break;
                case FIREFOX:
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                    break;
                case EDGE:
                    WebDriverManager.edgedriver().setup();
                    driver = new EdgeDriver();
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
