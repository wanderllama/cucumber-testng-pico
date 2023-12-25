package jw.demo.util.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import jw.demo.enums.Browser;
import jw.demo.util.ConfigProperties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BaseDriver {

    private WebDriver driver;

    private Browser browser;

    void setBrowser() {
        String b = ConfigProperties.getProperties().getProperty("browser").trim().toLowerCase();
        switch (b) {
            case "chrome-linux":
            case "chrome":
                browser = Browser.CHROME;
                break;
            case "firefox":
                browser = Browser.FIREFOX;
                break;
            case "edge":
                browser = Browser.EDGE;
                break;
            default:
                System.out.printf("%s is not a valid browser property used in configuration properties file", b);
        }
    }

    void createDriver() {
        if (driver == null) {
            System.out.printf("Browser: %s%n", browser);
            switch (browser) {
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
