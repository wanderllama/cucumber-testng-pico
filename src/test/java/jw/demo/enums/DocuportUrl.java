package jw.demo.enums;

import jw.demo.util.driver.Driver;
import org.apache.logging.log4j.Logger;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import static jw.demo.util.Util.assignLoggerByClass;
import static jw.demo.util.Util.exceptionErrorMsg;

public enum DocuportUrl {

    BASE("https://beta.docuport.app/"),
    API("api/v1/"),
    LOGIN("login"),
    AUTHENTICATE("authentication/account/authenticate"),
    TAX_FORM_1099("1099-form"),
    MY_UPLOADS("my-uploads");

    private static final Logger LOG;

    static {
        LOG = assignLoggerByClass();
    }

    private final String endpoint;

    DocuportUrl(String endpoint) {
        this.endpoint = endpoint;
    }

    public static void startAtHomePage() {
        Driver.navigateTo(DocuportUrl.BASE);
        // will need to handle login and clicking continue button
    }

    public static URL getApiUrl(String endpoint) {
        return createApiUrl(endpoint);
    }

    private static URL createApiUrl(String endpoint) {
        try {
            return new URL(getBasePathURI() + endpoint);
        } catch (MalformedURLException e) {
            exceptionErrorMsg(e);
            throw new RuntimeException(e);
        }
    }

    public static URI getBasePathURI() {
        return URI.create(BASE.url() + API.url());
    }

    public String url() {
        LOG.info(String.format("navigating to: %s%n", BASE + endpoint));
        return endpoint;
    }


    public static URL createUrl(String path) {
        try {
            return new URL(path);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

}
