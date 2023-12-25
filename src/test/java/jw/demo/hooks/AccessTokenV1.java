package jw.demo.hooks;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jw.demo.enums.DocuportUrl;
import jw.demo.models.auth.Authenticate;
import jw.demo.models.auth.Token;
import jw.demo.pages.POM;
import jw.demo.util.ConfigProperties;
import jw.demo.util.ScenarioCtx;
import jw.demo.util.Util;
import lombok.Getter;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static jw.demo.util.driver.Driver.getDriver;
import static jw.demo.util.driver.Driver.navigateTo;

public final class AccessTokenV1 {

    private static final Logger LOG = Util.loggerForClass();
    @Getter
    private static final ThreadLocal<ScenarioCtx> scenarioCtx = new ThreadLocal<>();
    private static HttpResponse<String> tokenResponse;
    private static String accessToken;
    private static String AUTH_URL;
    @Getter
    private static HashMap<String, String> tokensMap;
    POM pom = new POM();

    // prevent objects being made of this class
    private AccessTokenV1() {
        throw new IllegalStateException("Utility class");
    }

    private static void init() {
        // setup ConfigProperties to access data from config.properties
        ConfigProperties.setupProperties();
        // set url required for getting tokes
        AUTH_URL = ConfigProperties.getData("authUrl");
        // sent POST request for tokens
        tokenResponse = getTokens();
        if (tokenResponse.body().equals("{}")) {
            throw new RuntimeException("empty response for tokens");
        }
        tokensMap = setTokens();

    }

    // send POST request for tokens returns response
    public static synchronized HttpResponse<String> getTokens() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .header("Content-Type", "application/json")
                    .header("Accept", "*/*")
                    .uri(URI.create(AUTH_URL))
                    .POST(HttpRequest.BodyPublishers.ofString(getPayload()))
                    .build();
            tokenResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            System.out.println("error getting payload from file required for POST request for tokens");
        } catch (InterruptedException e) {
            System.out.println("error sending POST request for tokens");
        }
        return tokenResponse;
    }

    // use POST response from getTokens() to create tokens object containing all tokens
    public static HashMap<String, String> setTokens() {

        ObjectMapper mapper = new ObjectMapper();
        Gson gson = new Gson();
        Authenticate response;
        try {
            response = mapper.readValue(tokenResponse.body(), Authenticate.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error processing json token response to authenticate object");
        }

        String tmp = gson.toJson(response.getUser());
        JsonObject jsonObj = new JsonObject();
        jsonObj.getAsJsonObject(tmp);

        String permissionsString = response.getUser().getPermissions().toString();
        Token tokens = new Token(response.getUser().getJwtToken().getRefreshToken(), tmp, response.getUser().getJwtToken().getAccessToken(), permissionsString);
        return mapper.convertValue(tokens, new TypeReference<>() {
        });
    }

    private static String getPayload() {
        return "{\"usernameOrEmailAddress\":\"" +
                ConfigProperties.getData("username") +
                "\",\"password\":\"" +
                ConfigProperties.getData("password") +
                "\"}";
    }

    static void saveSessionData(ITestContext context, Method method) {
        saveCookie(context, method);
        setSessionData(context, method);
        System.out.println("session data saved");
    }

    public static void setSessionDataV2(ITestContext context, Method method) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        if (setSessionDataAllowed(method)) {
            Map<String, String> map = getTokensMap();
            map.forEach((key, value) -> js.executeScript(String.format(
                    "window.localStorage.setItem('%s','%s');",
                    key, value)));
            System.out.println("key: " + map.keySet() + " value: " + map.values());
        }
//        Set<String> keyNames = context.getAttributeNames();
//        for (String key : keyNames) {
//            Cookie tmp = (Cookie) context.getAttribute(key);
//            if (tmp.getDomain().equals(".docuport.app")) {
//                continue;
//            }
//            getDriver().manage().addCookie(tmp);
//        }
    }

    // only need access token to login
    static void setSessionData(ITestContext context, Method method) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        if (setSessionDataAllowed(method)) {
            Set<String> keyNames = context.getAttributeNames();
            for (String key : keyNames) {
                if (context.getAttribute(key) instanceof Cookie tmp) {
                    tmp = (Cookie) context.getAttribute(key);
                    if (tmp.getDomain().equals(".docuport.app")) {
                        Cookie tmp2 = new Cookie(tmp.getName(), tmp.getValue(), tmp.getDomain().substring(1), tmp.getPath(), tmp.getExpiry(), tmp.isSecure(), tmp.isHttpOnly(), tmp.getSameSite());
                        tmp = null;
                        tmp = tmp2;

                        System.out.println("\n " + tmp2 + "\n ");
                    }
                    System.out.println("\n" + tmp + "\n ");
                    getDriver().manage().addCookie(tmp);
                } else {
                    js.executeScript(String.format(
                            "window.localStorage.setItem('%s','%s');",
                            key, context.getAttribute(key).toString()));
                }
            }
        } else {
            throw new RuntimeException("method not allowed. Only to be run for docuport scenarios.\nAdd scenario to docuport group to automatically run this at the start of scenario");
        }

        navigateTo(DocuportUrl.BASE);
////        waitTillPresent(getLoginPage().getContinueBtnAfterLoginLocator(), A.waitTimeOf(1000));
//
//        try {
//            pom.getLoginPage().getContinueBtnAfterLogin().click();
//        } catch (ElementClickInterceptedException e) {
//            jsClick(pom.getLoginPage().getContinueBtnAfterLogin());
//        }
//        Util.waitTillInvisible(getBasePage().getProgressBar(), A.waitTimeOf(1000));
    }

    // only need access token to login
    static void saveLocalStorage(ITestContext context, Method method) {
        if (saveSessionDataAllowed(method)) {
            JavascriptExecutor js = (JavascriptExecutor) getDriver();
            for (int i = 0, m = -1; m != 0; i++) {
                String key = ((String) js.executeScript(
                        String.format("return window.localStorage.key(" + i + ");")));
                if (key == null) {
                    i--;
                    if (m != -1) break;
                    continue;
                }
                String value = ((String) js.executeScript(
                        String.format("return window.localStorage.getItem('%s');", key)));
                context.setAttribute(key, value);
                m--;
            }
        } else {
            System.out.println("method not allowed");
        }
    }

    // cookies not required for login
    static void saveCookie(ITestContext context, Method method) {
        if (saveSessionDataAllowed(method)) {
            Set<Cookie> cookiesSet = getDriver().manage().getCookies();
            for (Cookie cookie : cookiesSet) {
                Cookie tmp = new Cookie(cookie.getName(), cookie.getValue(), cookie.getDomain(), cookie.getPath(),
                        cookie.getExpiry(), cookie.isSecure(), cookie.isHttpOnly(), cookie.getSameSite());
                context.setAttribute(cookie.getName(), tmp);
                System.out.println(tmp);
            }
        } else {
            System.out.println("method not allowed");
        }
    }

    private static boolean setSessionDataAllowed(Method method) {
        return (method.getAnnotation(Test.class).dependsOnGroups())[0].equals("cookie");
    }

    private static boolean saveSessionDataAllowed(Method method) {
        return method.getAnnotation(Test.class).groups()[0].equals("cookie");
    }

}
