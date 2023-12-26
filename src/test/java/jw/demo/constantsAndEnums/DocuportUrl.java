package jw.demo.constantsAndEnums;

import jw.demo.util.driver.Driver;
import org.apache.logging.log4j.Logger;

import static jw.demo.util.Util.assignLoggerByClass;

public class DocuportUrl {

    private static final Logger LOG;
    public static String BASE = "https://beta.docuport.app/";
    public static String API = "api/v1/";
    public static String LOGIN = "login";
    public static String AUTHENTICATE = "authentication/account/authenticate";
    public static String TAX_FORM_1099 = "1099-form";
    public static String MY_UPLOADS = "my-uploads";

    static {
        LOG = assignLoggerByClass();
    }

    public static void startAtHomePage() {
        Driver.navigateTo(DocuportUrl.BASE);
        // will need to handle login and clicking continue button
    }

    public static String getUrlFor(String page) {
        return createUrl(page);
    }

    public static String createUrl(String endpoint) {
        return getBasePathURI() + endpoint;
    }

    public static String getBasePathURI() {
        return BASE;
    }

    public static String getApiUrl(String endpoint) {
        return createApiUrl(endpoint);
    }

    private static String createApiUrl(String endpoint) {
        return getApiBasePathURI() + endpoint;
    }

    public static String getApiBasePathURI() {
        return BASE + API;
    }
}
