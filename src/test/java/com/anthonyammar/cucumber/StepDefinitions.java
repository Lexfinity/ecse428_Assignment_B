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
    //private final String PATH_TO_CHROME_DRIVER = "/Users/knam/Downloads/chromedriver";
    private final String IMAGE1_URL = System.getProperty("user.dir") + "/images/hashtag_5.jpg";
    private final String EMAIL_URL = "https://mail.google.com/mail/u/0/#inbox?compose=new"
    private final String INBOX_URL = "https://mail.google.com/mail/u/0/#inbox"
    private final String ATTACHMENT_BTN = "J-J5-Ji J-Z-I-J6-H"
    private final String COMPOSE_BTN = "T-I J-J5-Ji T-I-KE L3"
    private final String SEND_BTN = "gU Up"
    private final String RECEIPIENT_TEXT_FIELD = "vO"
    private final String SUBJECT_TEXT_FIELD = "aoT"
    private final String SUBJECT_TEXT_FIELD = "aT"

    // Given
    @Given("^I am a user$")
    public void givenIAmUser() throws Throwable {
        setupSeleniumWebDrivers();
        goTo(INBOX_URL);
    }

    @And("^ I have clicked \"compose a new email\"") throws Throwable {
        driver.findElement(By.className("COMPOSE_BTN")).click();
        goTo(EMAIL_URL);
    }

    @And("^ I have filled in the information for a recepient email and subject") throws Throwable {
        driver.findElement(By.className("RECEIPIENT_TEXT_FIELD")).sendKeys("anthony.laye@mail.mcgill.ca");
        driver.findElement(By.className("SUBJECT_TEXT_FIELD")).sendKeys("Test");
    }

    @When("^I import an image file to my email $")
    public void iImportAnImageFileToMyEmail() throws Throwable {

        //INSERT PATH FOR IMAGE
        driver.findElement(By.className("ATTACHMENT_BTN")).sendKeys(IMAGE1_URL);

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

        WebElement emailAlert = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.className("aT")));

        Assert.assertTrue(emailAlert.getText(), "Message sent"));
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

    // Helper functions
    private void setupSeleniumWebDrivers() throws MalformedURLException {
        if (driver == null) {
            System.out.println("Setting up ChromeDriver... ");
            //System.setProperty("webdriver.chrome.driver", PATH_TO_CHROME_DRIVER);
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