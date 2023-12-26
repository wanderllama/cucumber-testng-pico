package jw.demo.util;

import jw.demo.constantsAndEnums.WaitTime;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;


public class Log {

    private static final Logger LOG;

    static {
        LOG = Util.assignLoggerByClass();
    }

    public static void exceptionErrorMsg(Exception e) {
        LOG.error(String.format("%s was thrown during %s method",
                e.getClass().getSimpleName(), getMethodName()));
    }

    public static void exceptionErrorMsg(String customMessage, Exception e) {
        LOG.error(customMessage + String.format("\n%s was thrown during %s method",
                e.getClass().getSimpleName(), getMethodName()));
    }

    public static void exceptionErrorMsg(Exception e, By element) {
        LOG.info(String.format("%s was thrown during %s method\nLocator Used: %s",
                e.getClass().getSimpleName(), getMethodName(), element));
    }

    public static void exceptionErrorMsg(String customMsg, Exception e, By element) {
        LOG.info(customMsg + String.format("\n%s was thrown during %s method\nLocator Used: %s",
                e.getClass().getSimpleName(), getMethodName(), element));
    }

    public static void exceptionErrorMsg(Exception e, WaitTime given, By element) {
        LOG.info(String.format("%s was thrown after %s%n seconds during %s method\nLocator Used: %s",
                e.getClass().getSimpleName(), given.amountOfSeconds(),
                getMethodName(), element));
    }

    public static void exceptionErrorMsg(String customMsg, Exception e, WaitTime given, By element) {
        LOG.info(customMsg + String.format("\n%s was thrown after %s%n seconds during %s method\nLocator Used: %s",
                e.getClass().getSimpleName(), given.amountOfSeconds(),
                getMethodName(), element));
    }

    public static String getMethodName() {
        return Thread.currentThread().getStackTrace()[2].getMethodName();
    }
}
