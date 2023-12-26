package jw.demo.hooks;

import jw.demo.util.Util;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Arrays;

/*
    This class contains @Before @After methods to control their order of execution
    The annotations above will also be honored (inherited) when placed on a superclass
    of a TestNG class. This is useful for example to centralize test setup for multiple test
    classes in a common superclass.

    In that case, TestNG guarantees that the "@Before" methods are executed in inheritance order
    (highest superclass first, then going down the inheritance chain), and the "@After" methods in
    reverse order (going up the inheritance chain).
 */

// Extends Util and Util extends Driver
public class BaseHooks extends Util {

    static Logger LOG;

    static {
        LOG = assignLoggerByClass();
    }

    private static String scenarioInformation(Method method) {
        StringBuilder info = new StringBuilder(method.getName() + "\nScenario Groups: ");
        Arrays.asList(method.getAnnotation(Test.class).groups()).forEach(group -> info.append(group).append(" "));
        info.append("\nDepends on Groups: ");
        Arrays.asList(method.getAnnotation(Test.class).dependsOnGroups()).forEach(group -> info.append(group).append(" "));
        return info.toString();
    }



    // runs before each method first
//    @BeforeMethod(alwaysRun = true)
    public void setup(Method method) {
        LOG.info("-------- Scenario Start --------\nScenario Name: " + scenarioInformation(method));
//        System.out.println("-------- Scenario Start --------\nScenario Name: " + scenarioInformation(method));
        setDriver();
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        getDriver().manage().window().maximize();
    }
}
