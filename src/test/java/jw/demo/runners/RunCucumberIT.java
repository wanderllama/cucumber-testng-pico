package jw.demo.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(plugin =
        {
                "html:target/cucumber-report/runwebat/cucumber.html"
                , "json:target/cucumber-report/runwebat/cucumber.json"
                , "rerun:target/cucumber-report/runwebat/rerun.txt"
                , "pretty"
        }
        , monochrome = true
        , features = "src/test/resources/features"
        , glue = "{jw/demo/stepDefinitions, jw/demo/hooks/Hooks.java}"
        , tags = "@context"
)
public class RunCucumberIT extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
