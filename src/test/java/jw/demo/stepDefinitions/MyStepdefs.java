package jw.demo.stepDefinitions;

import io.cucumber.java.en.Given;
import jw.demo.util.TestContext;

public class MyStepdefs {

    @Given("this is step {string} for test")
    public void this_is_step_for_test(String number) {
        System.out.printf("scenario %s printed this at start\n", number);
        if (Integer.parseInt(number) == 1) {
            // set variable in shared TestContext only in first scenario
            TestContext.setGlobal(number);
        }
        // set property in ScenarioCtx
        TestContext.getScenarioCtx().setProperty(number, TestContext.getScenarioCtx().getProperty("method name"));
        // only print results in second scenario
        if (Integer.parseInt(number) == 2) {
            System.out.printf("\nscenario %s printed this inside if statement\nScenarioCtx is not shared since property set in scenario 1 is: " + TestContext.getScenarioCtx().getProperty("1"), number);
            System.out.printf("\n\nscenario %s printed this\nTestContext is shared and variable set in scenario 1 is: " + TestContext.getGlobal(), number);
        }
    }

}
