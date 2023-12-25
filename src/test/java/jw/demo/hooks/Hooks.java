package jw.demo.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import jw.demo.enums.DocuportUrl;
import jw.demo.util.ConfigProperties;
import jw.demo.util.ScenarioCtx;
import jw.demo.util.TestContext;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.ITestContext;

import java.lang.reflect.Method;
import java.time.Duration;

import static jw.demo.enums.ContextConstants.*;
import static jw.demo.util.Util.loggerForClass;
import static jw.demo.util.driver.Driver.*;

// Extends BaseHooks, BaseHooks extends Util and Util extends Driver
public class Hooks {



    private static Logger LOG = loggerForClass();


    //    @BeforeAll
    public static void setupGlobal(ITestContext context) {
        LOG.info("================ BEFORE ALL ================\n" +
                "======= HOPEFULLY WON'T NEED TO READ =======");
//        System.out.println("================ BEFORE ALL ================");
        ConfigProperties.setupProperties(); // property files for data and configuration
        AccessToken.init(); // access/refresh token are saved to map
        AccessToken.saveTokensToContext(context); // access/refresh tokens saved to context
    }

    //    @Before
    public static void setup(ITestContext context, Method method) {
        LOG.info(scenarioInformation(context, method));
//        System.out.println("-------- Scenario Start --------\nScenario Name: " + scenarioInformation(method));
        setDriver();
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        getDriver().manage().window().maximize();
        navigateTo(DocuportUrl.LOGIN);
        if ((Boolean) context.getAttribute(REQUIRES_TOKENS)) {
            LOG.info("attempting to save tokens to WebDriver local storage for non login page scenario");
            JavascriptExecutor js = (JavascriptExecutor) getDriver();
            js.executeScript(String.format("window.localStorage.setItem('%s','%s');",
                    ACCESS_TOKEN, context.getAttribute(ACCESS_TOKEN)));
            js.executeScript(String.format("window.localStorage.setItem('%s','%s');",
                    REQUIRES_TOKENS, context.getAttribute(REFRESH_TOKEN)));
            LOG.info(String.format("%s and %s saved to WebDriver local storage", ACCESS_TOKEN, REFRESH_TOKEN));
            getDriver().navigate().refresh();
        }
        System.out.println(method.getAnnotation(Given.class).value().contains("Login Page"));
    }

    // figure out how to handle scenario context might be able to use ITestContext
    // will probably swap TestContext and ITestContext making TestContext for scenario data
    @After
    public static void teardown(ITestContext context, Method method) {

        if (getDriver() != null) {
            quitDriver();
        }
    }


    private static String scenarioInformation(ITestContext context, Method method) {
        Boolean requiresTokens = !method.getAnnotation(Given.class).value().toLowerCase().contains("navigate to login page");
        context.setAttribute(REQUIRES_TOKENS, requiresTokens);
        return "-------- Scenario Start --------\n" +
                "Scenario Name: " + method.getName() + "\nRequires Tokens" + requiresTokens;
    }


    @Before
    public static void suiteSetup(ITestContext context, Method method) {
        LOG.info("================ BEFORE ALL ================");
        ScenarioCtx scenarioCtx = new ScenarioCtx();
        TestContext.setScenarioCtx(scenarioCtx);
        try {
            System.out.println("what im looking for " + context.getAttribute("one"));
        } catch (Exception e) {
            System.out.println("hello");
        }
        TestContext.getScenarioCtx().setProperty("method name", method.getName());
    }
}
