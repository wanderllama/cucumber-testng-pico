package jw.demo.hooks;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jw.demo.models.auth.Authenticate;
import jw.demo.util.ConfigProperties;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

import static jw.demo.enums.ContextConstants.ACCESS_TOKEN;
import static jw.demo.enums.ContextConstants.REFRESH_TOKEN;
import static jw.demo.util.Util.assignLoggerByClass;
import static jw.demo.util.Util.exceptionErrorMsg;

public final class AccessToken {

    private static final Logger LOG;

    static {
        LOG = assignLoggerByClass();
    }

    private static Map<String, String> tokenMap;
    private static HttpResponse<String> tokenResponse;
    private static String AUTH_URL;

    // prevent objects being made of this class
    private AccessToken() {
        throw new IllegalStateException("Utility class");
    }

    public static void init() {
        // setup ConfigProperties to access data from config.properties
        ConfigProperties.setupProperties();
        // set url required for getting tokes
        AUTH_URL = ConfigProperties.getData("authUrl");
        // sent POST request for tokens
        tokenResponse = getTokens();
        if (tokenResponse.body().equals("{}")) {
            // try to create polymorphic method to replace this mess
            try {
                throw new RuntimeException();
            } catch (RuntimeException e) {
                exceptionErrorMsg(e);
            }
        }
        tokenMap = setTokens();

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
            exceptionErrorMsg("error getting payload from file required for POST request for tokens", e);
        } catch (InterruptedException e) {
            exceptionErrorMsg("error sending POST request for tokens", e);
        }
        return tokenResponse;
    }

    // use POST response from getTokens() to create tokens object containing all tokens
    public static HashMap<String, String> setTokens() {
        ObjectMapper mapper = new ObjectMapper();
        Authenticate response;
        try {
            response = mapper.readValue(tokenResponse.body(), Authenticate.class);
        } catch (JsonProcessingException e) {
            exceptionErrorMsg(e);
            throw new RuntimeException("Error processing json token response to authenticate object");
        }
        HashMap<String, String> tokens = new HashMap<>();
        tokens.put(ACCESS_TOKEN, response.getUser().getJwtToken().getAccessToken());
        tokens.put(REFRESH_TOKEN, response.getUser().getJwtToken().getRefreshToken());
        return tokens;
    }

    private static String getPayload() {
        return "{\"usernameOrEmailAddress\":\"" +
                ConfigProperties.getData("username") +
                "\",\"password\":\"" +
                ConfigProperties.getData("password") +
                "\"}";
    }

    public static void saveTokensToContext(ITestContext context) {
        context.setAttribute(ACCESS_TOKEN, tokenMap.get(ACCESS_TOKEN));
        context.setAttribute(REFRESH_TOKEN, tokenMap.get(REFRESH_TOKEN));
    }
}
