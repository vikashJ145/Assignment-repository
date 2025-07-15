package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

@CucumberOptions(
    features = "src/test/resources/features/api", 
    glue = { "stepdefs.api" }, 
    plugin = {
        "pretty",
        "html:target/cucumber-reports.html",
        "json:target/cucumber-reports/cucumber.json",
        "junit:target/cucumber-reports/cucumber.xml",
        "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
    }, 
    monochrome = true,
    publish = true
)
public class TestNGApiRunner extends AbstractTestNGCucumberTests {
    
    @BeforeMethod
    public void setUp(ITestResult result) {
        Allure.getLifecycle().updateTestCase(testResult -> {
            testResult.setName(result.getMethod().getMethodName());
            testResult.setFullName(result.getTestClass().getName() + "." + result.getMethod().getMethodName());
        });
    }
    
    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            attachScreenshot("Test Failed");
            attachLogs("Test execution logs");
        }
    }
    
    @Attachment(value = "Screenshot", type = "image/png")
    public byte[] attachScreenshot(String name) {
        // For API tests, we can attach request/response details instead of screenshots
        return "API Test Screenshot".getBytes();
    }
    
    @Attachment(value = "Logs", type = "text/plain")
    public String attachLogs(String name) {
        return "Test execution completed with detailed logging";
    }
}
