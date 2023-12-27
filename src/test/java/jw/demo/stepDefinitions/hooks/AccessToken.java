package jw.demo.stepDefinitions.hooks;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jw.demo.constantsAndEnums.DocuportUrl;
import jw.demo.models.auth.Authenticate;
import jw.demo.util.ConfigProperties;
import jw.demo.util.Log;
import jw.demo.util.TestContext;
import lombok.SneakyThrows;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

import static jw.demo.constantsAndEnums.Constants.*;
import static jw.demo.constantsAndEnums.DocuportUrl.AUTHENTICATE;
import static jw.demo.util.Util.assignLoggerByClass;

public final class AccessToken {

    private static final Logger LOG;

    static {
        LOG = assignLoggerByClass();
    }

    private static Map<String, String> tokenMap;
    private static HttpResponse<String> tokenResponse;


    // prevent objects being made of this class
    private AccessToken() {
        throw new IllegalStateException("Utility class");
    }

    public static void init() {
        // setup ConfigProperties to access data from config.properties
        ConfigProperties.setupProperties();
        // set url required for getting tokes

        // sent POST request for tokens
        tokenResponse = getTokens();
        if (tokenResponse.body().equals(EMPTY_JSON)) {
//            throw new RuntimeException(Log.exceptionErrorMsg(e));
        }
        TestContext.setTokensMap(setTokens());
    }

    // send POST request for tokens returns response
    @SneakyThrows
    public static HttpResponse<String> getTokens() {
        String authUriStr = DocuportUrl.getApiUrl(AUTHENTICATE);
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .header("Content-Type", "application/json")
                    .header("Accept", "*/*")
                    .uri(new URI(authUriStr))
                    .POST(HttpRequest.BodyPublishers.ofString(getPayload()))
                    .build();
            tokenResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new IOException(Log.exceptionErrorMsg("error getting payload from file required for POST request for tokens", e));
        } catch (InterruptedException e) {
            throw new InterruptedException(Log.exceptionErrorMsg("error sending POST request for tokens", e));
        } catch (URISyntaxException e) {
            throw new URISyntaxException(Log.exceptionErrorMsg(e), authUriStr);
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
            throw new RuntimeException(Log.exceptionErrorMsg("Error processing json token response to authenticate object", e));
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
}
