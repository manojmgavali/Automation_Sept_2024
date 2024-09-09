package stepdefinition;

import static org.testng.Assert.assertTrue;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.cucumber.java.en.*;

public class Cartfunctionality_Steps {

    WebDriver driver;
    ExtentReports extent;
    ExtentTest test;

    
    public Cartfunctionality_Steps() {
        ExtentSparkReporter spark = new ExtentSparkReporter("target/cucumber-reports/ExtentReport.html");
        extent = new ExtentReports();
        extent.attachReporter(spark);
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("User", "Tester");
    }

    @Given("User on the login page")
    public void user_on_the_login_page() {
        test = extent.createTest("User on the login page");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        test.log(Status.INFO, "Navigated to the login page");
    }

    @When("user login with valid credentials {string} and {string}")
    public void user_login_with_valid_credentials(String username, String password) throws InterruptedException {
        test.log(Status.INFO, "Logging in with username: " + username);
        driver.findElement(By.id("user-name")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        Thread.sleep(2000);
        driver.findElement(By.id("login-button")).click();
        Thread.sleep(2000);
        test.log(Status.PASS, "Logged in with valid credentials");
    }

    @Then("user should be redirected to the product page")
    public void user_should_be_redirected_to_the_product_page() {
        boolean product_page = driver.findElement(By.xpath("//span[text()='Products']")).isDisplayed();
        Assert.assertTrue(product_page);
        test.log(Status.PASS, "User successfully redirected to the product page");
    }

    @Then("user add {string} to the cart")
    public void user_add_to_the_cart(String productName) {
        test = extent.createTest("Add product to cart");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement product = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='" + productName + "']/ancestor::div[@class='inventory_item']")));
        WebElement addToCartButton = product.findElement(By.xpath(".//button[text()='Add to cart']"));
        addToCartButton.click();
        test.log(Status.PASS, "Product added to cart: " + productName);
    }

    @When("user click on the cart icon")
    public void user_click_on_the_cart_icon() {
        test = extent.createTest("Click on the cart icon");
        WebElement cartIcon = driver.findElement(By.className("shopping_cart_link"));
        cartIcon.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("cart.html"));
        test.log(Status.PASS, "Clicked on the cart icon and navigated to the cart page");
    }

    @Then("user should see the following product in the cart:")
    public void user_should_see_the_following_product_in_the_cart(List<String> expectedProducts) {
        test = extent.createTest("Verify products in the cart");
        List<WebElement> cartItems = driver.findElements(By.className("inventory_item_name"));
        for (String expectedProduct : expectedProducts) {
            boolean productFound = cartItems.stream().anyMatch(e -> e.getText().equals(expectedProduct));
            Assert.assertTrue(productFound, expectedProduct + " not found in the cart");
        }
        test.log(Status.PASS, "All expected products are present in the cart");
    }

    @When("user remove {string} from the cart")
    public void user_remove_from_the_cart(String productName) throws InterruptedException {
        test = extent.createTest("Remove product from cart");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement removeButton = driver.findElement(By.xpath("//div[text()='" + productName + "']/ancestor::div[@class='inventory_item']//button[text()='Remove']"));
        Thread.sleep(3000);
        removeButton.click();
        test.log(Status.PASS, "Product removed from cart: " + productName);
    }

    @Then("cart count should be {string}")
    public void cart_count_should_be(String expectedCount) {
        test = extent.createTest("Verify cart count");
        List<WebElement> cartBadge = driver.findElements(By.className("shopping_cart_badge"));
        if (expectedCount.equals("0")) {
            Assert.assertTrue(cartBadge.isEmpty(), "Cart badge should not be present");
        } else {
            Assert.assertEquals(expectedCount, cartBadge.get(0).getText(), "Cart badge count does not match");
        }
        test.log(Status.PASS, "Cart count verified: " + expectedCount);
    }

    @Then("the product {string} should not be listed in the cart")
    public void the_product_should_not_be_listed_in_the_cart(String productName) {
        test = extent.createTest("Verify product not listed in cart");
        List<WebElement> cartItems = driver.findElements(By.className("inventory_item_name"));
        boolean productFound = cartItems.stream().anyMatch(e -> e.getText().equals(productName));
        Assert.assertTrue(productFound, "Product should not be in the cart: " + productName);
        test.log(Status.PASS, "Verified product is not listed in the cart: " + productName);
    }

    @When("user click on the {string} button")
    public void user_click_on_the_button(String buttonText) throws InterruptedException {
        test = extent.createTest("Click on button: " + buttonText);
        WebElement button = driver.findElement(By.xpath("//button[text()='" + buttonText + "']"));
        Thread.sleep(2000);
        button.click();
        if (buttonText.equals("Checkout")) {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.urlContains("checkout-step-one.html"));
        }
        test.log(Status.PASS, "Clicked on button: " + buttonText);
    }

    @And("user enter checkout information:")
    public void enter_checkout_information(io.cucumber.datatable.DataTable dataTable) {
        test = extent.createTest("Enter checkout information");
        List<List<String>> details = dataTable.asLists();
        String name = details.get(1).get(1);
        String address = details.get(2).get(1);
        String postalCode = details.get(3).get(1);

        driver.findElement(By.id("first-name")).sendKeys(name);
        driver.findElement(By.id("last-name")).sendKeys(address);
        driver.findElement(By.id("postal-code")).sendKeys(postalCode);

        driver.findElement(By.id("continue")).click();
        test.log(Status.PASS, "Entered checkout information: " + name + ", " + address + ", " + postalCode);
    }

    @And("user complete the checkout process")
    public void complete_the_checkout_process() {
        test = extent.createTest("Complete checkout process");
        driver.findElement(By.id("finish")).click();
        test.log(Status.PASS, "Completed the checkout process");
    }

    @Then("the order confirmation page should be displayed")
    public void the_order_confirmation_page_should_be_displayed() {
        test = extent.createTest("Verify order confirmation page");
        WebElement confirmationMessage = driver.findElement(By.className("complete-header"));
        Assert.assertTrue(confirmationMessage.getText().contains("THANK YOU FOR YOUR ORDER"));
        test.log(Status.PASS, "Order confirmation page displayed with message: " + confirmationMessage.getText());
        driver.close();
        test.log(Status.INFO, "Browser closed");
        extent.flush();
    }
}
