package stepdefinition;

import static org.testng.Assert.assertTrue;
import java.time.Duration;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class Loginsteps {

	WebDriver driver;
	ExtentReports extent;
	ExtentTest test;

	
	public Loginsteps() {
		ExtentSparkReporter spark = new ExtentSparkReporter("target/cucumber-reports/ExtentReport.html");
		extent = new ExtentReports();
		extent.attachReporter(spark);
		extent.setSystemInfo("Environment", "QA");
		extent.setSystemInfo("User", "Tester");
	}

	@Given("Browser is open")
	public void browser_is_open() {
		test = extent.createTest("Browser is open");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		test.log(Status.INFO, "Browser opened and maximized");
	}

	@And("user is on login page")
	public void user_is_on_login_page() {
		test.log(Status.INFO, "Navigating to login page");
		driver.get("https://www.saucedemo.com/");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		test.log(Status.PASS, "User is on the login page");
	}

	@When("user enter valid credentials {string} and {string}")
	public void user_enter_username_and_password(String username, String password) throws InterruptedException {
		test.log(Status.INFO, "Entering username: " + username + " and password");
		driver.findElement(By.id("user-name")).sendKeys(username);
		driver.findElement(By.id("password")).sendKeys(password);
		Thread.sleep(2000);
		test.log(Status.PASS, "Entered valid credentials");
	}

	@And("clicks on login button")
	public void clicks_on_login_button() throws InterruptedException {
		test.log(Status.INFO, "Clicking on login button");
		driver.findElement(By.id("login-button")).click();
		Thread.sleep(2000);
		test.log(Status.PASS, "Clicked login button");
	}

	@Then("user is redirected to the product page")
	public void user_is_redirected_to_the_product_page() {
		boolean product_page = driver.findElement(By.xpath("//span[text()='Products']")).isDisplayed();
		Assert.assertTrue(product_page);
		test.log(Status.PASS, "User successfully redirected to the product page");
		driver.close();
		test.log(Status.INFO, "Browser closed");
		extent.flush();
	}

	@When("user enter invalid credentials {string} and {string}")
	public void user_enter_invalid_username_and_password(String username, String password) throws InterruptedException {
		test = extent.createTest("Invalid Login Attempt");
		test.log(Status.INFO, "Entering invalid username: " + username + " and password");
		driver.findElement(By.id("user-name")).sendKeys(username);
		driver.findElement(By.id("password")).sendKeys(password);
		Thread.sleep(2000);
		test.log(Status.PASS, "Entered invalid credentials");
	}

	@Then("user should get the error message")
	public void user_should_get_error() {
		String error_msg = driver
				.findElement(By.xpath("//h3[contains(text(),'Epic sadface: Username and password do not match a')]"))
				.getText();
		Assert.assertEquals(error_msg, "Epic sadface: Username and password do not match any user in this service");
		test.log(Status.PASS, "Error message validated: " + error_msg);
		driver.close();
		test.log(Status.INFO, "Browser closed");
		extent.flush();
	}

	@When("user leaves both username and password fileds empty")
	public void user_leaves_both_username_and_password_fileds_empty() {
		test = extent.createTest("Empty Credentials Test");
		test.log(Status.INFO, "Leaving username and password fields empty");
	}

	@Then("An error message is displayed for empty credentials")
	public void an_error_message_is_displayed_for_empty_credentials() throws InterruptedException {
		
		driver.findElement(By.id("login-button")).click();
		Thread.sleep(2000);
		test.log(Status.PASS, "Clicked login button");
		String empty_error_msg = driver
				.findElement(By.xpath("//h3[normalize-space()='Epic sadface: Username is required']")).getText();
		Assert.assertEquals(empty_error_msg, "Epic sadface: Username is required");
		test.log(Status.PASS, "Error message validated: " + empty_error_msg);
		driver.close();
		test.log(Status.INFO, "Browser closed");
		extent.flush();

	}

}
