import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.cucumber.java.After;
import io.cucumber.java.Before;

public class ExtentReportHooks {

    private static ExtentReports extent;
    private static ExtentTest feature;
    private static ExtentTest scenario;
    private static ExtentSparkReporter spark;

    // This runs before all scenarios
    @Before
    public void setUp() {
        if (extent == null) {
            spark = new ExtentSparkReporter("target/cucumber-reports/ExtentReport.html");
            extent = new ExtentReports();
            extent.attachReporter(spark);

            // Setup system information for the report
            extent.setSystemInfo("Environment", "QA");
            extent.setSystemInfo("User", "Tester");
        }
    }

    // Call this method to start logging a feature in your step definitions
    public static ExtentTest createFeature(String featureName) {
        feature = extent.createTest(featureName);
        return feature;
    }

    // Call this method to create and log a scenario in your step definitions
    public static ExtentTest createScenario(String scenarioName) {
        scenario = feature.createNode(scenarioName);
        return scenario;
    }

    public static ExtentTest getScenario() {
        return scenario;
    }

    // This runs after all scenarios
    @After
    public void tearDown() {
    	if (driver != null) {
            driver.quit();
        }
        extent.flush();
    }

    public static ExtentReports getExtent() {
        return extent;
    }
}
