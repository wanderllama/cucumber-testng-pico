package jw.demo.util;


import jw.demo.pages.POM;
import jw.demo.util.driver.Driver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.NoSuchElementException;

import static jw.demo.enums.ContextConstants.PASSWORD;
import static jw.demo.enums.ContextConstants.USERNAME;
import static jw.demo.enums.WaitTime.REGULAR;

public class Util extends Driver {

    private static final Logger LOG;

    POM pom = new POM();

    static {
        LOG = assignLoggerByClass();
    }

    // create WebDriverWait object -> Not sure if I will keep these wait helpers
    public static WebDriverWait getWait() {
        return getWait(REGULAR.waitTime());
    }

    public static void waitTillInvisible(WebElement element, Duration d) {
        try {
            Util.getWait(d).until(ExpectedConditions.invisibilityOf(element));
        } catch (TimeoutException e) {
            LOG.error(String.format("%s failed waitTillInvisible wait after waiting %s%n", element.toString(), d));
        }
    }

    public static WebDriverWait getWait(Duration d) {
        return new WebDriverWait(Driver.getDriver(), d);
    }

    public static void waitTillInvisible(WebElement element) {
        try {
            Util.getWait(REGULAR.waitTime()).until(ExpectedConditions.invisibilityOf(element));
        } catch (TimeoutException e) {
            LOG.error(String.format("%s failed waitTillInvisible wait after waiting %s%n", element.toString(), REGULAR.getSeconds()));
        }
    }

    public static WebElement waitTillVisible(By element) {
        WebElement webElement = null;
        try {
            webElement = Util.getWait(REGULAR.waitTime()).until(ExpectedConditions.visibilityOf(Driver.getDriver().findElement(element)));
        } catch (TimeoutException e) {
            LOG.error(String.format("%s failed waitTillVisible wait after waiting %s%n", element.toString(), REGULAR.getSeconds()));
        }
        return webElement;
    }

    public static WebElement waitTillVisible(By element, Duration d) {
        WebElement webElement = null;
        try {
            webElement = Driver.getDriver().findElement(element);
            Util.getWait(d).until(ExpectedConditions.visibilityOf(webElement));
        } catch (TimeoutException e) {
            LOG.error(String.format("%s failed waitTillVisible wait after waiting %s%n", element.toString(), d.getSeconds()));
        } catch (NoSuchElementException e) {
            LOG.error(String.format("%s could not be located NoSuchElementException", element.toString()));
        }
        return webElement;
    }

    public static void jsClick(WebElement element) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
            js.executeScript("arguments[0].click();", element);
        } catch (Exception e) {
            LOG.error(String.format("JavascriptExecutor error while clicking locator %s%n", element.toString()));
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
