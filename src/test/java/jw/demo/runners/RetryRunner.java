package jw.demo.runners;


import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(plugin =
        {
                "html:target/cucumber-report/runwebat/cucumber.html"
                , "json:target/cucumber-report/runwebat/cucumber.json"
                , "rerun:target/cucumber-report/runwebat/rerun.txt"
                , "pretty"
        }
        , monochrome = true
        , features = "src/test/resources/features"
        , glue = "jw/demo/stepDefinitions"
        , tags = "@DI"
)
public class RetryRunner extends AbstractTestNGCucumberTests {
}
