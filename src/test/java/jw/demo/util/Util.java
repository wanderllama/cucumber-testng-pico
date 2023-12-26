package jw.demo.util;


import jw.demo.enums.WaitTime;
import jw.demo.util.driver.Driver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;
import java.util.NoSuchElementException;

import static jw.demo.enums.ContextConstants.PASSWORD;
import static jw.demo.enums.ContextConstants.USERNAME;
import static jw.demo.enums.WaitTime.REGULAR;

public class Util extends Driver {

    private static final Logger LOG;

    static {
        LOG = assignLoggerByClass();
    }

    // create WebDriverWait object -> Not sure if I will keep these wait helpers
    public static WebDriverWait waitFor() {
        return waitFor(REGULAR.waitTime());
    }

    public static void waitTillInvisible(By element, WaitTime given) {
        try {
            waitFor(given.waitTime()).until(ExpectedConditions.invisibilityOf(getDriver().findElement(element)));
        } catch (TimeoutException e) {
            exceptionErrorMsg(e, given, element);
        }
    }

    public static WebDriverWait waitFor(Duration d) {
        return new WebDriverWait(getDriver(), d);
    }

    public static void waitTillInvisible(By element) {
        try {
            waitFor(REGULAR.waitTime()).until(ExpectedConditions.invisibilityOf(
                    getDriver().findElement(element)));
        } catch (TimeoutException e) {
            exceptionErrorMsg(e, REGULAR, element);
            throw new TimeoutException(e.getMessage());
        }
    }

    public static WebElement waitTillVisible(By element) {
        WebElement webElement = null;
        try {
            webElement = waitFor(REGULAR.waitTime()).until(ExpectedConditions.visibilityOf(getDriver().findElement(element)));
        } catch (TimeoutException e) {
            exceptionErrorMsg(e, REGULAR, element);
            throw new TimeoutException(e.getMessage());
        }
        return webElement;
    }

    public static WebElement waitTillVisible(By element, WaitTime given) {
        WebElement webElement = null;
        try {
            webElement = getDriver().findElement(element);
            waitFor(given.waitTime()).until(ExpectedConditions.visibilityOf(webElement));
        } catch (NoSuchElementException e) {
            exceptionErrorMsg(e, given, element);
            throw new NoSuchElementException(Arrays.toString(e.getStackTrace()));
        } catch (TimeoutException e) {
            exceptionErrorMsg(e, given, element);
            throw new TimeoutException(Arrays.toString(e.getStackTrace()));
        }
        return webElement;
    }

    public static void jsClick(By element) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) getDriver();
            js.executeScript("arguments[0].click();", getDriver().findElement(element));
        } catch (JavascriptException e) {
            exceptionErrorMsg(e, element);
            LOG.error(String.format("JavascriptExecutor error while clicking locator %s%n", element.toString()));
            throw new JavascriptException(Arrays.toString(e.getStackTrace()));
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

    public static void exceptionErrorMsg(Exception e) {
        LOG.error(String.format("%s was thrown during %s method",
                e.getClass().getSimpleName(), getMethodName()));
    }

    public static void exceptionErrorMsg(String customMessage, Exception e) {
        LOG.error(customMessage + String.format("%s was thrown during %s method",
                e.getClass().getSimpleName(), getMethodName()));
    }

    public static void exceptionErrorMsg(Exception e, By element) {
        LOG.info(String.format("%s was thrown during %s method\nLocator Used: %s",
                e.getClass().getSimpleName(), getMethodName(), element));
    }

    public static void exceptionErrorMsg(Exception e, WaitTime given, By element) {
        LOG.info(String.format("%s was thrown after %s%n seconds during %s method\nLocator Used: %s",
                e.getClass().getSimpleName(), given.amountOfSeconds(),
                getMethodName(), element));
    }

    public static String getMethodName() {
        return Thread.currentThread().getStackTrace()[2].getMethodName();
    }

    public static String getUsername(ScenarioCtx context) {
        return context.getProperty(USERNAME).toString();
    }

    public static String getPassword(ScenarioCtx context) {
        return context.getProperty(PASSWORD).toString();
    }

}
