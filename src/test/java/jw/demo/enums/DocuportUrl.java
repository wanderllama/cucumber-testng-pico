package jw.demo.enums;

import jw.demo.util.driver.Driver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public enum DocuportUrl {

    BASE("https://beta.docuport.app/"),
    API("api/v1/"),
    LOGIN("login"),
    AUTHENTICATE("authentication/account/authenticate"),
    TAX_FORM_1099("1099-form"),
    MY_UPLOADS("my-uploads");

    private static final Logger LOG = LoggerFactory.getLogger(DocuportUrl.class);
    private final String endpoint;

    DocuportUrl(String endpoint) {
        this.endpoint = endpoint;
    }

    public static void startAtHomePage() {
        Driver.navigateTo(DocuportUrl.BASE);
        // will need to handle login and clicking continue button
    }


    public static URL getApiUrlForEndPoint(String endpoint) {
        return createApiUrl(endpoint);
    }

    public static URI getBasePathURI() {
        return URI.create(BASE.url() + API.url());
    }


    public static String url(String applicationUrl) {
        return BASE.url();
    }


    public static URL createApiUrl(String endpoint) {
        try {
            return new URL(endpoint);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }


    public static URL createUrl(String path) {
        try {
            return new URL(path);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public String url() {
        System.out.printf("navigating to: %s%n", BASE + endpoint);
        return endpoint;
    }

}
