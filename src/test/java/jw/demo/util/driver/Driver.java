package jw.demo.util.driver;

import jw.demo.constantsAndEnums.DocuportUrl;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import static jw.demo.constantsAndEnums.WaitTime.LONG;
import static jw.demo.util.Util.assignLoggerByClass;

public class Driver {

    protected static final ThreadLocal<WebDriver> threadLocalDriver;
    private static final Logger LOG;

    static {
        LOG = assignLoggerByClass();
        threadLocalDriver = new ThreadLocal<>();
    }

    public static WebDriver setupBeforeScenario() {
        // set up the WebDriver
        setDriver();
        getDriver().manage().timeouts().implicitlyWait(LONG.waitTime());
        getDriver().manage().window().maximize();
        return threadLocalDriver.get();
    }

    public static void setDriver() {
        BaseDriver baseDriver = new BaseDriver();
        baseDriver.setBrowser();
        baseDriver.createDriver();
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

    public static void navigateTo(String page) {
        getDriver().get(DocuportUrl.getUrlFor(page));
    }

    public static WebElement findElement(By element) {
        return getDriver().findElement(element);
    }

    static String getDriverBrowser() {
        LOG.error("Getting browser info");
        Capabilities cap = ((RemoteWebDriver) getDriver()).getCapabilities();
        return cap.getBrowserName();
    }

}