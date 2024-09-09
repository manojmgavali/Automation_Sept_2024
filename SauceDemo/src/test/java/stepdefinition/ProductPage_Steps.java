package stepdefinition;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.cucumber.java.en.*;

public class ProductPage_Steps {

	WebDriver driver;
	ExtentReports extent;
	ExtentTest test;

	
	public ProductPage_Steps() {
		ExtentSparkReporter spark = new ExtentSparkReporter("target/cucumber-reports/ProductPageReport.html");
		extent = new ExtentReports();
		extent.attachReporter(spark);
		extent.setSystemInfo("Environment", "QA");
		extent.setSystemInfo("User", "Tester");
	}

	@Given("The Browser is open")
	public void the_browser_is_open() {
		test = extent.createTest("Browser is open");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		test.log(Status.INFO, "Browser opened and maximized");
	}

	@And("user is on the login page")
	public void user_is_on_the_login_page() {
		test.log(Status.INFO, "Navigating to login page");
		driver.get("https://www.saucedemo.com/");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		test.log(Status.PASS, "User is on the login page");
	}

	@When("user enter the valid credentials {string} and {string}")
	public void user_enter_the_username_and_password(String username, String password) throws InterruptedException {
		test.log(Status.INFO, "Entering username: " + username + " and password");
		driver.findElement(By.id("user-name")).sendKeys(username);
		driver.findElement(By.id("password")).sendKeys(password);
		Thread.sleep(2000);
		test.log(Status.PASS, "Entered valid credentials");
	}

	@And("clicks on the login button")
	public void clicks_on_the_login_button() throws InterruptedException {
		test.log(Status.INFO, "Clicking on login button");
		driver.findElement(By.id("login-button")).click();
		Thread.sleep(2000);
		test.log(Status.PASS, "Clicked login button");
	}

	@When("the user navigates to the product page")
	public void the_user_navigates_to_the_product_page() {
		Assert.assertTrue(driver.getCurrentUrl().contains("inventory.html"));
		test.log(Status.PASS, "User navigated to the product page");
	}

	@Then("the product list is displayed")
	public void the_product_list_is_displayed() {
		WebElement productList = driver.findElement(By.className("inventory_list"));
		Assert.assertTrue(productList.isDisplayed());
		test.log(Status.PASS, "Product list is displayed");
	}

	@Then("all expected products are visible")
	public void all_expected_products_are_visible() {
		String[] expectedProducts = { "Sauce Labs Backpack", "Sauce Labs Bike Light", "Sauce Labs Bolt T-Shirt",
				"Sauce Labs Fleece Jacket", "Sauce Labs Onesie", "Test.allTheThings() T-Shirt (Red)" };

		List<WebElement> productElements = driver.findElements(By.className("inventory_item_name"));

		for (String expectedProduct : expectedProducts) {
			boolean productFound = productElements.stream()
					.anyMatch(product -> product.getText().equals(expectedProduct));
			Assert.assertTrue(productFound, expectedProduct);
			test.log(Status.PASS, "Product found: " + expectedProduct);
		}
	}

	@When("the user clicks on product {string}")
	public void the_user_clicks_on(String productName) {
		List<WebElement> productElements = driver.findElements(By.className("inventory_item_name"));

		for (WebElement productElement : productElements) {
			if (productElement.getText().equals(productName)) {
				productElement.click();
				test.log(Status.INFO, "Clicked on product: " + productName);
				break;
			}
		}
	}

	@Then("the product details should be displayed with name {string}, description {string}, and price {string}")
	public void the_product_details_page_is_displayed(String Expected_Name, String Expected_Description,
			String Expected_Price) {
		WebElement productName = driver.findElement(By.className("inventory_details_name"));
		WebElement productDescription = driver.findElement(By.className("inventory_details_desc"));
		WebElement productPrice = driver.findElement(By.className("inventory_details_price"));

		Assert.assertEquals(Expected_Name, productName.getText());
		Assert.assertEquals(Expected_Description, productDescription.getText());
		Assert.assertEquals(Expected_Price, productPrice.getText());
		test.log(Status.PASS, "Product details validated: " + Expected_Name);
	}

	@When("user click the Add to Cart button for the product {string}")
	public void user_click_the_add_to_cart_button_for_the_product(String productName) {
		WebElement productButton = driver.findElement(
				By.xpath("//div[text()='" + productName + "']/ancestor::div[@class='inventory_item']//button"));
		productButton.click();
		test.log(Status.INFO, "Clicked Add to Cart for: " + productName);
	}

	@Then("the cart count should be {string}")
	public void the_cart_count_should_increase_by_one(String expectedCount) {
		WebElement cartBadge = driver.findElement(By.className("shopping_cart_badge"));
		Assert.assertEquals(expectedCount, cartBadge.getText());
		test.log(Status.PASS, "Cart count validated: " + expectedCount);
	}

	@And("the product {string} should be listed in the cart")
	public void the_product_should_be_listed_in_the_cart(String productName) {
		driver.findElement(By.className("shopping_cart_link")).click();
		WebElement cartItem = driver
				.findElement(By.xpath("//div[@class='inventory_item_name' and text()='" + productName + "']"));
		Assert.assertNotNull(cartItem);
		test.log(Status.PASS, "Product is listed in the cart: " + productName);
		driver.close();
		test.log(Status.INFO, "Browser closed");
		extent.flush();
	}
}
