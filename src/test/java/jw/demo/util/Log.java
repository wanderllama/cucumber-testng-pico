package jw.demo.util;

import jw.demo.constantsAndEnums.WaitTime;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class Log {

    private static final Logger LOG;

    static {
        LOG = Util.assignLoggerByClass();
    }

    public static String exceptionErrorMsg(Exception e) {
        ArrayList<String> lines = new ArrayList<>();
        return prettyError(String.format("%s was thrown during method: %s",
                e.getClass().getSimpleName(), getMethodName()), lines);
    }

    public static String exceptionErrorMsg(String customMessage, Exception e) {
        ArrayList<String> lines = new ArrayList<>();
        return prettyError("Error: " + customMessage + String.format("\n%s was thrown during method: %s",
                e.getClass().getSimpleName(), getMethodName()), lines);
    }

    public static String exceptionErrorMsg(Exception e, By element) {
        ArrayList<String> lines = new ArrayList<>();
        return prettyError(String.format("%s was thrown during method: %s\nLocator Used: %s",
                e.getClass().getSimpleName(), getMethodName(), element), lines);
    }

    public static String exceptionErrorMsg(String customMsg, Exception e, By element) {
        ArrayList<String> lines = new ArrayList<>();
        return prettyError("Error: " + customMsg + String.format("\n%s was thrown during method: %s\nLocator Used: %s",
                e.getClass().getSimpleName(), getMethodName(), element), lines);
    }

    public static String exceptionErrorMsg(Exception e, WaitTime given, By element) {
        ArrayList<String> lines = new ArrayList<>();
        return prettyError(String.format("%s was thrown after %s seconds during method: %s\nLocator Used: %s",
                e.getClass().getSimpleName(), given.amountOfSeconds(),
                getMethodName(), element), lines);
    }

    public static String exceptionErrorMsg(String customMsg, Exception e, WaitTime given, By element) {
        ArrayList<String> lines = new ArrayList<>();
        return prettyError("Error: " + customMsg + String.format("\n%s was thrown after %s seconds during method: %s\nLocator Used: %s",
                e.getClass().getSimpleName(), given.amountOfSeconds(),
                getMethodName(), element), lines);
    }

    public static String getMethodName() {
        return Thread.currentThread().getStackTrace()[3].getMethodName();
    }

    // not the best but working
    private static String prettyError(String str, ArrayList<String> lines) {
        ArrayList<String> list = new ArrayList<>(List.of(str.split("\n")));
        list.sort(Comparator.comparingInt(String::length));
        int starLength = list.get(list.size() - 1).length() + 4;
        if (starLength % 2 != 0) {
            String tmp = list.get(list.size() - 1).replaceFirst(":", "");
            list.remove(list.get(list.size() - 1));
            list.add(tmp);
        }
        String star = "*".repeat(starLength);
        StringBuilder msg = new StringBuilder("\n" + star + "*\n*");
        for (String parts : list) {
            int spaces = (starLength - parts.length()) / 2;
            msg.append(" ".repeat(spaces)).append(parts).append(" ".repeat(spaces)).append("*\n*");
        }
        return msg.append(star).append("\n").toString();
    }
}
