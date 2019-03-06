package com.kevinnam.cucumber;

import cucumber.annotation.en.And;
import cucumber.annotation.en.Given;
import cucumber.annotation.en.Then;
import cucumber.annotation.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;

public class StepDefinitions {

    // Variables
    private WebDriver driver;
    private final String PATH_TO_CHROME_DRIVER = "/Users/knam/Downloads/chromedriver";
    private final String PRODUCT_URL = "https://www.amazon.ca/Monoprice-115365-Select-Mini-Printer/dp/B01FL49VZE/ref=sr_1_1?ie=UTF8&qid=1488132110&sr=8-1&keywords=3d+printer";

    private final String EMAIL_URL = "https://mail.google.com/mail/u/0/#inbox?compose=new"
    private final String INBOX_URL = "https://mail.google.com/mail/u/0/#inbox"
    private final String ATTACHMENT_BTN = "J-J5-Ji J-Z-I-J6-H"
    private final String COMPOSE_BTN = "T-I J-J5-Ji T-I-KE L3"
    private final String SEND_BTN = "gU Up"
    private final String RECEIPIENT_TEXT_FIELD = "vO"
    private final String SUBJECT_TEXT_FIELD = "aoT"
    private final String SUBJECT_TEXT_FIELD = "aT"

    private final String PRODUCT_NAME = "Monoprice 115365 Monoprice Select Mini 3D Printer";
    private final String DELETE_BTN_NAME = "submit.delete.C3NLW69582M4B4";
    private final String CART_URL = "https://www.amazon.ca/gp/cart/view.html/ref=nav_cart";
    private final String ADD_TO_CART_BTN = "add-to-cart-button";
    private final String ACTIVE_CART = "sc-active-cart";
    private final String CHECKOUT_BTN = "sc-proceed-to-checkout";

    Scenario: Sending an email with an image file attachment
    Given I am a user
    When I import an image file to my email
    Then that file should appear as an attachment(s)
    And I can send the email with the attachment(s)

    // Given
    @Given("^I am a user$")
    public void givenIAmUser() throws Throwable {
        setupSeleniumWebDrivers();
        goTo(INBOX_URL);
    }

    @And("^ I have clicked \"compose a new email\"") throws Throwable {
        setupSeleniumWebDrivers();
        driver.findElement(By.className("COMPOSE_BTN")).click();
        goTo(EMAIL_URL);
    }

    @And("^ I have filled in the information for a recepient email and subject") throws Throwable {
        driver.findElement(By.className("RECEIPIENT_TEXT_FIELD")).sendKeys("anthony.laye@mail.mcgill.ca");
        driver.findElement(By.className("SUBJECT_TEXT_FIELD")).sendKeys("Test");
    }

    /*
    @Given("^I am on my current shopping cart$")
    public void iAmOnMyCurrentShoppingCart() throws Throwable {
        setupSeleniumWebDrivers();
        goTo(CART_URL);
    }
*/
    @When("^I import an image file to my email $")
    public void iImportAnImageFileToMyEmail() throws Throwable {
        // Go to a product page
        goTo(EMAIL_URL);

        //INSERT PATH FOR IMAGE
        driver.findElement(By.className("ATTACHMENT_BTN")).sendKeys("");

        // Add a product to shopping cart
        System.out.println("Uploading file to your email.. ");
        WebElement btn = (new WebDriverWait(driver, 10))

    }

    @Then("^that file should appear as an attachment$")
    public void myEmailHasAnImage() throws Throwable {


        WebElement attachment = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.className("vI")));

        if (!searchForText(attachment.getText(), FILENAME)) {
            System.out.println("Image was not found");
        }
    }


    @And("^I can send the email with the attachment ")
    public void iCanSendEmailWithImage() throws Throwable {
        ExpectedConditions.elementToBeClickable(By.name(SEND_BTN));

        WebElement email = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.className("aT")));


    }






    @When("^I upload an image file to my email$")
    public void uploadImageFileToEmail() throws Throwable {


    }

    @And("^I press send while the files are still uploading$")
    public void sendWhileUploading() throws Throwable {

    }

    @Then("^my email will not be sent$")
    public void emailNotSent() throws Throwable {

    }

    @And("^I will get a message saying to wait until my files are uploaded$")
    public void waitUntilFilesUploadedError() throws Throwable {

    }





    // When
    @When("^I press \"Delete\"$")
    public void iPressDelete() throws Throwable {
        // Attempt to find a delete button and click on it
        try {
            System.out.println("Attempting to find delete button... ");
            WebElement btn = (new WebDriverWait(driver, 10))
                    .until(ExpectedConditions.elementToBeClickable(By.name(DELETE_BTN_NAME)));
            System.out.print("Found!\n");
            btn.click();
            System.out.println("Clicking delete button.");
        } catch (Exception e) {
            System.out.println("No delete button present");
        }
    }

    @When("^I press \"Add to cart\"$")
    public void iPressAddToCart() throws Throwable {
        goTo(PRODUCT_URL);
        // Attempt to find add to cart button and click on it
        try {
            System.out.println("Attempting to find Add to cart button... ");
            WebElement btn = (new WebDriverWait(driver, 10))
                    .until(ExpectedConditions.elementToBeClickable(By.id(ADD_TO_CART_BTN)));
            System.out.print("Found!\n");
            btn.click();
            System.out.println("Clicking add to cart button");
        } catch (Exception e) {
            System.out.println("No add to cart button present");
        }
    }

    // Then
    @Then("^the product should exist in my shopping cart$")
    public void theProductShouldExistInMyShoppingCart() throws Throwable {
        goTo(CART_URL);

        // Attempt to find active cart
        WebElement cart = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.id(ACTIVE_CART)));

        // Product name should be in active cart
        Assert.assertTrue(searchForText(cart.getText(), PRODUCT_NAME));
    }

    @Then("^the product should no longer exist in my shopping cart$")
    public void theProductShouldNoLongerExistInMyShoppingCart() throws Throwable {
        goTo(CART_URL);

        // Attempt to find active cart
        WebElement cart = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.id(ACTIVE_CART)));

        // Product name should not be in active cart
        Assert.assertFalse(searchForText(cart.getText(), PRODUCT_NAME));
    }

    @Then("^there is nothing to delete from the shopping cart$")
    public void thereIsNothingToDeleteFromTheShoppingCart() throws Throwable {
        // Attempt to find a delete button
        try {
            System.out.println("Attempting to find a delete button... ");
            WebElement btn = (new WebDriverWait(driver, 10))
                    .until(ExpectedConditions.elementToBeClickable(By.name(DELETE_BTN_NAME)));
            btn.click();
            Assert.fail("Delete button should not be present");
        } catch (Exception e) {
            System.out.println("No delete button present");
        }
    }

    @And("^the quantity of the product should be equal to two$")
    public void theQuantityOfTheProductShouldBeEqualToTwo() throws Throwable {
        // Attempt to find quantity
        System.out.println("Attempting to find quantity... ");
        WebElement dropDown = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.className("a-dropdown-prompt")));
        System.out.print("Found!\n");

        // Quantity should be equal to two
        Assert.assertTrue(searchForText(dropDown.getText(), "2"));
    }

    @And("^the checkout button exists$")
    public void theCheckoutButtonExists() throws Throwable {
        // Attempt to find checkout button
        try {
            System.out.println("Attempting to find checkout button");
            WebElement checkoutBtn = (new WebDriverWait(driver, 10))
                    .until(ExpectedConditions.presenceOfElementLocated(By.className(CHECKOUT_BTN)));
            System.out.println("Found checkout button.");
            driver.quit();
        } catch (Exception e) {
            Assert.fail("No checkout button found. Should have been present.");
        }
    }

    @And("^the checkout button does not exist$")
    public void theCheckoutButtonDoesNotExist() throws Throwable {
        // Attempt to find checkout button
        try {
            System.out.println("Attempting to find checkout button... ");
            WebElement checkoutBtn = (new WebDriverWait(driver, 10))
                    .until(ExpectedConditions.presenceOfElementLocated(By.className(CHECKOUT_BTN)));
            Assert.fail("Checkout button was found. Should have not be present.");
        } catch (Exception e) {
            System.out.print("None found!\n");
            driver.quit();
        }
    }

    // Helper functions
    private void setupSeleniumWebDrivers() throws MalformedURLException {
        if (driver == null) {
            System.out.println("Setting up ChromeDriver... ");
            System.setProperty("webdriver.chrome.driver", PATH_TO_CHROME_DRIVER);
            driver = new ChromeDriver();
            System.out.print("Done!\n");
        }
    }

    private boolean searchForText(String text, String textToFind) {
        return text.contains(textToFind);
    }

    private void goTo(String url) {
        if (driver != null) {
            System.out.println("Going to " + url);
            driver.get(url);
        }
    }



}