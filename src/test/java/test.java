import jw.demo.constantsAndEnums.BrowserType;

import static jw.demo.constantsAndEnums.BrowserType.*;
import static jw.demo.constantsAndEnums.Constants.ACCESS_TOKEN;
import static jw.demo.constantsAndEnums.Constants.REFRESH_TOKEN;

public class test {

    private static final String[] tokenArray;

    static {
//        LOG = assignLoggerByClass();
        tokenArray = new String[]{ACCESS_TOKEN, REFRESH_TOKEN};
    }

    public static void main(String[] args) {
        System.out.println("edge");
    }

    private static void met(String b) {
        BrowserType browserIs = CHROME;
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
        System.out.println(browserIs);
    }
}
