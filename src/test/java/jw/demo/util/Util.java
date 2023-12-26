package jw.demo.util;


import jw.demo.constantsAndEnums.WaitTime;
import jw.demo.pages.POM;
import jw.demo.util.driver.Driver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static jw.demo.constantsAndEnums.Constants.PASSWORD;
import static jw.demo.constantsAndEnums.Constants.USERNAME;


public class Util {

    private static final Logger LOG;

    static {
        LOG = assignLoggerByClass();
    }

    public static WebDriverWait waitFor(Duration d) {
        return new WebDriverWait(Driver.getDriver(), d);
    }

    public static void waitTillInvisible(By element) {
        try {
            waitFor(WaitTime.REGULAR.waitTime()).until(ExpectedConditions.invisibilityOf(
                    Driver.findElement(element)));
        } catch (TimeoutException e) {
            Log.exceptionErrorMsg(e, WaitTime.REGULAR, element);
            throw new TimeoutException(e.getMessage());
        }
    }

    public static WebElement waitTillVisible(By element) {
        POM pom = new POM();
        try {
            waitFor(WaitTime.REGULAR.waitTime()).until(ExpectedConditions.visibilityOf(
                    pom.getBasePage().getDriver().findElement(element)));
        } catch (TimeoutException e) {
            Log.exceptionErrorMsg(e, WaitTime.REGULAR, element);
            throw new TimeoutException(e.getMessage());
        }
        return pom.getBasePage().getDriver().findElement(element);
    }

    public static void jsClick(By element) {
        WebDriver driver = new POM().getBasePage().getDriver();
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", driver.findElement(element));
        } catch (JavascriptException e) {
            throw new JavascriptException(Log.exceptionErrorMsg(
                    String.format("JavascriptExecutor error while clicking locator %s%n", element.toString()), e, element));
        }
    }

    public static Logger assignLoggerByClass() {
        String callerName = Thread.currentThread().getStackTrace()[2].getClassName();
        Class<?> caller = null;
        try {
            caller = Class.forName(callerName);
        } catch (ClassNotFoundException e) {
            LOG.error("loggerForClass couldn't identify caller name");
            e.printStackTrace();
        }
        return caller != null ? LogManager.getLogger(caller.getSimpleName()) : null;
    }

    public static String getUsername(ScenarioCtx context) {
        return context.getProperty(USERNAME).toString();
    }

    public static String getPassword(ScenarioCtx context) {
        return context.getProperty(PASSWORD).toString();
    }

}
