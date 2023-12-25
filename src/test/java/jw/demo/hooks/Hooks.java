package jw.demo.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.Given;
import jw.demo.enums.DocuportUrl;
import jw.demo.util.ConfigProperties;
import jw.demo.util.TestContext;
import jw.demo.util.Util;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.ITestContext;

import java.lang.reflect.Method;
import java.time.Duration;

// Extends BaseHooks, BaseHooks extends Util and Util extends Driver
public class Hooks extends Util {

    static TestContext context;
    private static Logger LOG = loggerForClass();

    private Hooks(TestContext context) {
        Hooks.context = context;
    }

    @BeforeAll
    public static void setupGlobal() {
        LOG.info("================ BEFORE ALL ================\n" +
                "======= HOPEFULLY WON'T NEED TO READ =======");
//        System.out.println("================ BEFORE ALL ================");
        ConfigProperties.setupProperties(); // property files for data and configuration
        AccessToken.init();
        context.setAccessToken(AccessToken.getTokenMap());
    }

    @Before
    public static void setup(ITestContext testContext, Method method) {
        LOG.info(scenarioInformation(testContext, method));
//        System.out.println("-------- Scenario Start --------\nScenario Name: " + scenarioInformation(method));
        setDriver();
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        getDriver().manage().window().maximize();
        navigateTo(DocuportUrl.LOGIN);
        if ((Boolean) testContext.getAttribute("requiresTokens")) {
            LOG.info("attempting to save tokens to WebDriver local storage for non login page scenario");
            JavascriptExecutor js = (JavascriptExecutor) getDriver();
            context.getAccessToken().forEach((key, value) -> {
                js.executeScript(String.format(
                        "window.localStorage.setItem('%s','%s');", key, value));
                LOG.info(String.format("%s saved to WebDriver local storage", key));
            });
            getDriver().navigate().refresh();
        }
        System.out.println(method.getAnnotation(Given.class).value().contains("Login Page"));
    }

    @After
    public static void teardown(ITestContext context) {
        if (context != null) {
            context = null;
        }
        if (getDriver() != null) {
            quitDriver();
        }
    }


    private static String scenarioInformation(ITestContext context, Method method) {
        Boolean requiresTokens = !method.getAnnotation(Given.class).value().toLowerCase().contains("navigate to login page");
        context.setAttribute("requiresTokens", requiresTokens);
        StringBuilder info = new StringBuilder("-------- Scenario Start --------\n" +
                "Scenario Name: ");
        info.append(method.getName()).append("\nRequires Tokens").append(requiresTokens);
        return info.toString();
    }
}
