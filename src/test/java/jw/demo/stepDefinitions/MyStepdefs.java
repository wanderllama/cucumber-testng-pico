package jw.demo.stepDefinitions;

import io.cucumber.java.en.Given;
import jw.demo.util.TestContext;

public class MyStepdefs {

    @Given("this is step {string} for test")
    public void thisIsStepForTest(String number) {
        if (Integer.parseInt(number) == 1) {
            // set variable in shared TestContext only in first scenario
            TestContext.setGlobal(number);
        }
        // set property in ScnearioCtx
        TestContext.getScenarioCtx().setProperty(number, TestContext.getScenarioCtx().getProperty("method name"));
        // only print results in second scenario
        if (Integer.parseInt(number) == 2) {
            System.out.println("ScenarioCtx is not shared since property set in scenario 1 is:  " + TestContext.getScenarioCtx().getProperty("1"));
            System.out.println("TestContext is shared and variable set in scenario 1 is:  " + TestContext.getGlobal());
        }

    }
}
