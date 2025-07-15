package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/resources/features/api", glue = "stepdefs/api", plugin = {
        "pretty",
        "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
}, monochrome = true)
public class OldTestNGTestRunner extends AbstractTestNGCucumberTests {
}
