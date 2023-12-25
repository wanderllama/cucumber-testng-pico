package jw.demo.util.driver;


import jw.demo.enums.Browser;
import jw.demo.enums.DocuportUrl;
import jw.demo.util.ConfigProperties;
import org.openqa.selenium.WebDriver;

public class Driver extends BaseDriver {
    protected static final ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();
    private static Browser browser;

    public static void driverInit() {
        setBrowser();
    }

    public static void setDriver() {
        BaseDriver baseDriver = new BaseDriver();
        baseDriver.setDriver(ConfigProperties.getData("browser"));
        threadLocalDriver.set(baseDriver.getD());
    }

    public static WebDriver getDriver() {
        if (threadLocalDriver.get() == null) {
            setDriver();
        }
        return threadLocalDriver.get();
    }

    public static void quitDriver() {
        if (threadLocalDriver.get() != null) {
            threadLocalDriver.get().quit();
            threadLocalDriver.remove();
            System.out.println("driver closed");
        } else {
            System.out.println("not driver to shutdown");
        }
    }

    public static void navigateTo(DocuportUrl url) {
        getDriver().get(url.url());
    }

    private static void setBrowser() {
        String b = ConfigProperties.getProperties().getProperty("browser").trim().toLowerCase();
        switch (b) {
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

}