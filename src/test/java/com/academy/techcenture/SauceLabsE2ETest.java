package com.academy.techcenture;


import com.github.javafaker.Faker;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.time.Duration;
import java.util.List;

public class SauceLabsE2ETest {

    private WebDriver driver;
    private Faker faker;
    WebDriverWait wait;
    private final String url = "https://www.saucedemo.com/";

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        this.driver = new ChromeDriver();
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        this.driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));
        wait = new WebDriverWait(this.driver, Duration.ofSeconds(10));
        this.faker = new Faker();
    }

    @Test
    public void sauceLabsE2ETest() throws InterruptedException {

        driver.get(url);
        String title = driver.getTitle();

        Assert.assertTrue(title.equals("Swag Labs"), "Titles did not match");

        WebElement loginLogo = driver.findElement(By.className("login_logo"));
        WebElement loginBotPicture = driver.findElement(By.className("bot_column"));

        Assert.assertTrue(loginLogo.isDisplayed() && loginBotPicture.isDisplayed(), "Login Logo is not displayed");

        WebElement userName = driver.findElement(By.id("user-name"));
        userName.sendKeys("standard_user");
        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("secret_sauce");
        WebElement loginBtn = driver.findElement(By.id("login-button"));
        loginBtn.click();

        Assert.assertTrue(title.equals("Swag Labs"), "Titles did not match");

        WebElement titleProducts = driver.findElement(By.className("title"));

        Assert.assertEquals(titleProducts.getText().trim(), "PRODUCTS");

        driver.findElement(By.id("react-burger-menu-btn")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("bm-item-list")));
        List<WebElement> menuItems = driver.findElements(By.xpath("//a[@class='bm-item menu-item']"));
        String[] menuOptions = {"all items", "about", "logout", "reset app state"};

        for (int i = 0; i < menuItems.size(); i++) {
            boolean menuItemCorrect = menuItems.get(i).getText().trim().toLowerCase().equals(menuOptions[i]);
            Assert.assertTrue(menuItemCorrect, "Menu Item did not match: " + menuOptions[i]);
        }
        driver.findElement(By.id("about_sidebar_link")).click();

        Assert.assertEquals(driver.getTitle().trim(), "Cross Browser Testing, Selenium Testing, Mobile Testing | Sauce Labs");

        driver.navigate().back();

        WebElement closeMenu = driver.findElement(By.id("react-burger-cross-btn"));

        if (closeMenu.isDisplayed()){
            closeMenu.click();
        }else{
            System.out.println("it was already closed");
        }

        List<WebElement> inventoryElements = driver.findElements(By.className("inventory_item_name"));

        Assert.assertEquals(inventoryElements.size(), 6, "Size of inventory elements was different");

        WebElement productSortContainer = driver.findElement(By.className("product_sort_container"));
        new Select(productSortContainer).selectByValue("lohi");

        List<WebElement> inventoryPrices = driver.findElements(By.className("inventory_item_price"));
        boolean firstPrice = inventoryPrices.get(0).getText().trim().equals("$7.99");
        boolean lastPrice = inventoryPrices.get(inventoryPrices.size() - 1).getText().trim().equals("$49.99");

        Assert.assertTrue(firstPrice && lastPrice, "First or last price are not correct");

        driver.findElement(By.linkText("Sauce Labs Onesie")).click();

        WebElement onesieTitle = driver.findElement(By.xpath("//div[@class='inventory_details_name large_size']"));

        WebElement onesieDescription = driver.findElement(By.xpath("//div[@class='inventory_details_desc large_size']"));

        WebElement onesiePrice = driver.findElement(By.xpath("//div[@class='inventory_details_price']"));

        boolean addToCartBtn = driver.findElement(By.id("add-to-cart-sauce-labs-onesie")).isDisplayed();

        Assert.assertTrue(addToCartBtn, "Add to cart btn was not displayed");
        Assert.assertTrue(onesieTitle.getText().equals("Sauce Labs Onesie"), "Title of product is not correct");
        Assert.assertTrue(onesieDescription.getText().equals("Rib snap infant onesie for the junior automation engineer in development." +
                " Reinforced 3-snap bottom closure, two-needle hemmed sleeved and bottom won't unravel."), "Description of product did not match");

        String productPrice = driver.findElement(By.xpath("//div[@class='inventory_details_price']")).getText();
        Assert.assertEquals(productPrice, "$7.99");

        WebElement clickBtn = driver.findElement(By.xpath("//div/button[contains(text(),'Add to cart')]"));
        Assert.assertTrue(clickBtn.isDisplayed(), "Add to cart was not displayed");
        clickBtn.click();

        String removeBtnText = driver.findElement(By.id("remove-sauce-labs-onesie")).getText();
        Assert.assertEquals(removeBtnText, "REMOVE", "did not match");

        driver.findElement(By.className("shopping_cart_link")).click();

        Assert.assertTrue(driver.findElement(By.className("title")).isDisplayed(), "Filter componenet was not displayed");

        Assert.assertTrue(driver.findElement(By.xpath("//div[@class='cart_quantity']")).getText().equals("1"), "Quantity was not 1");

        Assert.assertTrue(driver.findElement(By.id("checkout")).isEnabled(), "Checkout button was not enabled");

        driver.findElement(By.id("checkout")).click();

        String lastName = faker.name().lastName();
        String firstName = faker.name().firstName();
        String zipCode = faker.address().zipCode().substring(0, 5);

        driver.findElement(By.id("first-name")).sendKeys(firstName);
        driver.findElement(By.id("last-name")).sendKeys(lastName);
        driver.findElement(By.id("postal-code")).sendKeys(zipCode);

        WebElement continueBtn = driver.findElement(By.id("continue"));

        Assert.assertTrue(continueBtn.isEnabled(), "Continue btn was not enabled");
        continueBtn.click();

        Assert.assertTrue(driver.findElement(By.className("title")).isDisplayed(), "i dont know why it failed here");

        WebElement paymentInfo = driver.findElement(By.xpath("(//div[contains(@class,'summary_value_label')])[1]"));

        Assert.assertTrue(paymentInfo.isDisplayed(), "Payment info was not displayed");

        WebElement deliveryInfo = driver.findElement(By.xpath("//div[contains(text(),'FREE PONY EXPRESS DELIVERY!')]"));
        Assert.assertEquals(deliveryInfo.getText(), "FREE PONY EXPRESS DELIVERY!", "Delivery info was not displayed");

        Double subtotal = Double.parseDouble(driver.findElement(By.className("summary_subtotal_label")).getText().replaceAll("[^0-9,.]", ""));

        Double tax = Double.parseDouble(driver.findElement(By.className("summary_tax_label")).getText().replaceAll("[^0-9,.]", ""));
        Double total = Double.parseDouble(driver.findElement(By.className("summary_total_label")).getText().replaceAll("[^0-9,.]", ""));

        Assert.assertTrue(subtotal + tax == total, "Total price does not match");
        Assert.assertTrue(driver.findElement(By.id("finish")).isEnabled(), "Finish btn is not enabled");
        driver.findElement(By.id("finish")).click();
        Boolean thanksMsg = driver.findElement(By.className("complete-header")).getText().equalsIgnoreCase("THANK YOU FOR YOUR ORDER");
        Boolean orderMsg = driver.findElement(By.className("complete-text")).getText().equalsIgnoreCase("Your order has been dispatched, and will arrive just as fast as the pony can get there!");
        Boolean img = driver.findElement(By.className("pony_express")).isDisplayed();
        Assert.assertTrue(thanksMsg, "Thanks message was not displayed");
        Assert.assertTrue(orderMsg, "Order message was not displayed");
        Assert.assertTrue(img, "Pony express image was not displayed");
        Assert.assertTrue(driver.findElement(By.id("back-to-products")).isEnabled(), "Back to product was not enabled");
        driver.findElement(By.id("back-to-products")).click();

        driver.findElement(By.id("react-burger-menu-btn")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("bm-item-list")));
        driver.findElement(By.id("logout_sidebar_link")).click();
        Thread.sleep(3000);
    }

    @AfterMethod
    public void cleanUp() {
        if (this.driver != null) {
            this.driver.quit();
        }
    }


}
