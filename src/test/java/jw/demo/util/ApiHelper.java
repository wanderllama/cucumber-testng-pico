package jw.demo.util;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import jw.demo.constantsAndEnums.DocuportUrl;

/**
 * Every Api Step definition class should extend this class
 */

public class ApiHelper {
    private static Gson gson;

    static {
        RestAssured.baseURI = DocuportUrl.getBasePathURI().toString();
    }

    protected static RequestSpecification givenConfig() {
        RestAssured.useRelaxedHTTPSValidation();
        return new RequestSpecBuilder()
                .addHeader("Accept-Language", "en")
                .addHeader("Content-Type", "application/json")
                .build();
    }

    // Specify all one time default Gson config
    public static Gson gson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gson(gsonBuilder);
        return gson;
    }

    // Custom Gson config to override Default Gson configuration
    public static Gson gson(GsonBuilder gsonBuilder) {
        gson = gsonBuilder.create();
        return gson;
    }
}