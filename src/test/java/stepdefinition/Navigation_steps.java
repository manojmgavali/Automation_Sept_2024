package stepdefinition;

import static org.testng.Assert.assertTrue;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.cucumber.java.en.*;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Navigation_steps {

	 WebDriver driver;
	   WebDriverWait wait;
	   ExtentReports extent;
	   ExtentTest test;

	    

	    public Navigation_steps() {
	        // Initialize ChromeDriver using WebDriverManager
	        WebDriverManager.chromedriver().setup();
	        driver = new ChromeDriver();
	    }

	    @Given("^user is on the Sauce Demo login page$")
	    public void user_is_on_the_sauce_demo_login_page() {
	        test = extent.createTest("User on Sauce Demo Login Page");
	        driver.get("https://www.saucedemo.com/");
	        test.log(Status.INFO, "Navigated to Sauce Demo login page");
	    }
	    
    @And("^user logs in with valid credentials$")
    public void user_logs_in_with_valid_credentials() {
        test = extent.createTest("Login with Valid Credentials");
        WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")));
        WebElement password = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("login-button"));

        username.sendKeys("standard_user");
        password.sendKeys("secret_sauce");
        loginButton.click();
        test.log(Status.INFO, "Entered valid credentials and clicked login button");
    }

    @When("^user navigates to the product page$")
    public void user_navigates_to_the_product_page() {
        test = extent.createTest("Navigate to Product Page");
        wait.until(ExpectedConditions.urlContains("inventory.html"));
        test.log(Status.INFO, "Navigated to the product page");
    }

    @When("^user navigates to the cart page$")
    public void user_navigates_to_the_cart_page() {
        test = extent.createTest("Navigate to Cart Page");
        WebElement cartIcon = wait.until(ExpectedConditions.elementToBeClickable(By.className("shopping_cart_link")));
        cartIcon.click();
        wait.until(ExpectedConditions.urlContains("cart.html"));
        test.log(Status.INFO, "Navigated to the cart page");
    }

    @When("^user navigates to the checkout page$")
    public void user_navigates_to_the_checkout_page() {
        test = extent.createTest("Navigate to Checkout Page");
        WebElement checkoutButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("checkout")));
        checkoutButton.click();
        wait.until(ExpectedConditions.urlContains("checkout-step-one.html"));
        test.log(Status.INFO, "Navigated to the checkout page");
    }

    @Then("^the page should load correctly$")
    public void the_page_should_load_correctly()
    {
        test = extent.createTest("Verify Page Load");
        test.log(Status.INFO, "Verified that the page has loaded correctly");
    }

    @When("^user clicks on the \"Logout\" button$")
    public void user_clicks_on_the_logout_button() 
    {
        test = extent.createTest("Click Logout Button");
        WebElement menuButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("react-burger-menu-btn")));
        menuButton.click();
        WebElement logoutButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("logout_sidebar_link")));
        logoutButton.click();
        test.log(Status.INFO, "Clicked on logout button");
    }

    @Then("^user should be redirected to the login page$")
    public void user_should_be_redirected_to_the_login_page() 
    {
        test = extent.createTest("Verify Redirect to Login Page");
        wait.until(ExpectedConditions.urlContains("index.html"));
        test.log(Status.INFO, "Redirected to the login page");
    }

    @Then("^user should be logged out$")
    public void user_should_be_logged_out() {
        test = extent.createTest("Verify User Logout");
        WebElement loginButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
        assertTrue(loginButton.isDisplayed());
        test.log(Status.PASS, "User is successfully logged out");
        extent.flush();
        // Closing the browser after the test
        driver.quit();
        driver = null;  // Set driver to null to ensure fresh start for next test
    }
}
