package jw.demo.util.driver;

import jw.demo.enums.DocuportUrl;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import static jw.demo.enums.WaitTime.LONG;
import static jw.demo.util.Util.loggerForClass;

public class Driver {

    private static final Logger LOG = loggerForClass();
    protected static final ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();

    public static void setupBeforeScenario() {
        // set up the WebDriver
        setDriver();
        getDriver().manage().timeouts().implicitlyWait(LONG.waitTime());
        getDriver().manage().window().maximize();
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

    public static void navigateTo(DocuportUrl goToThis) {
        getDriver().get(goToThis.url());
    }

    static String getDriverBrowser() {
        LOG.info("Getting browser info");
        Capabilities cap = ((RemoteWebDriver) getDriver()).getCapabilities();
        return cap.getBrowserName();
    }


}