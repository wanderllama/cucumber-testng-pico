package jw.demo.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import jw.demo.util.ConfigProperties;
import jw.demo.util.ScenarioCtx;
import jw.demo.util.TestContext;
import jw.demo.util.driver.Driver;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;

import java.lang.reflect.Method;

import static jw.demo.constantsAndEnums.Constants.*;
import static jw.demo.util.Util.assignLoggerByClass;

@SuppressWarnings("DataFlowIssue")
public class Hooks {

    private static final Logger LOG;
    private static final String[] tokenArray;

    static {
        LOG = assignLoggerByClass();
        tokenArray = new String[]{ACCESS_TOKEN, REFRESH_TOKEN};
    }

    // runs once at the start of the test suite
    @Before(order = 1)
    public void before() {
        if (TestContext.getGlobal() == null) {
            LOG.error("\n" +
                    "================ BEFORE SUITE =================\n" +
                    "========= HOPEFULLY WONT NEED TO READ =========");
            TestContext.setProperties(ConfigProperties.setupProperties()); // property files for data and configuration
//            AccessToken.init(); // access/refresh token are saved to map in TestContext
            TestContext.setGlobal(COMPLETE);
        }
    }

    @Before(order = 2)
    public void before(Scenario method) {
        // set up the WebDriver and navigate to Login Page
        WebDriver driver = Driver.setupBeforeScenario();
//        driver.(LOGIN);

        // gather/LOG scenario information, identify if scenario test Login page
        // if scenario doesn't test login page then marks scenario to use tokens
//        LOG.error(aboutScenario(context, method));

        // create scenarioCtx object and make it thread safe and accessible through the TestContext class
        ScenarioCtx scenarioCtx = new ScenarioCtx();
        scenarioCtx.setProperty("method name", method.getName());
        TestContext.setScenarioCtx(scenarioCtx);

        // adds tokens to the WebDriver if scenario is not testing the login page
//        if ((Boolean) context.getAttribute(REQUIRES_TOKENS)) {
//            LOG.error("attempting to save tokens to WebDriver local storage for non login page scenario");
//            setAllLocalStorageTokens(context, driver, tokenArray);
//            driver.navigate().refresh();
//        }
    }

    // figure out how to handle scenario context might be able to use ITestContext
    // will probably swap TestContext and ITestContext making TestContext for scenario data
    @After
    public void after() {
        Driver.quitDriver();
    }

    // log scenario information and identify if tokens are used for logging in
    private static String aboutScenario(ITestContext context, Method method) {
        Boolean requiresTokens = !method.getAnnotation(Given.class).value()
                .toLowerCase().contains(STRING_FOUND_IN_LOGIN_SCENARIOS);
        context.setAttribute(REQUIRES_TOKENS, requiresTokens);
        return "-------- Scenario Start --------\n" +
                "Scenario Name: " + method.getName() + "\nRequires Tokens" + requiresTokens;
    }

    // add tokens in accessTokens array using setLocalStorageToken
    private static void setAllLocalStorageTokens(ITestContext context, WebDriver driver, String... accessTokens) {
        for (String accessToken : accessTokens) {
            setLocalStorageToken(context, driver, accessToken);
        }
    }

    // add single token to local storage
    private static void setLocalStorageToken(ITestContext context, WebDriver driver, String tokenName) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(String.format("window.localStorage.setItem('%s','%s');",
                tokenName, context.getAttribute(tokenName)));
        LOG.error(String.format("%s saved to WebDriver local storage", tokenName));
    }
}
