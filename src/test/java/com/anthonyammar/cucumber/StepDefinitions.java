package test.java.com.anthonyammar.cucumber;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.net.MalformedURLException;

public class StepDefinitions {

    // Variables
    private WebDriver driver;
    private final String IMAGE1_URL = System.getProperty("user.dir") + "/images/hashtag_5.jpg";
    private final String LARGE_IMAGE_URL = System.getProperty("user.dir") + "/images/bigimage.jpg";
    private final String DRIVER_PATH = System.getProperty("user.dir") + "/chromedriver.exe";

    private final String SIGN_IN_EMAIL = "ecse428AA@gmail.com";
    private final String SIGN_IN_PASSWORD = "Ecse428@";

    private final String INBOX_URL = "https://mail.google.com/mail/u/0/#inbox";
    private final String ATTACHMENT_BTN = "//input[@type='file']";
    private final String COMPOSE_BTN = "z0";
    private final String SEND_BTN = "gU";
    private final String RECEIPIENT_TEXT_FIELD = "vO";
    private final String SUBJECT_TEXT_FIELD = "aoT";
    private final String CONFIRMATION_TEXT_FIELD = "aT";
    private final String ATTACHMENT_TEXT_FIELD_CHECK = "vI";
    private final String DRIVE_POPUP_TEXT_FIELD = "Kj-JD-K7-K0";

    /* ===================================================================================================
    ========================================== SETUP =====================================================
    ======================================================================================================
     */

    @Given("^I am a user$")
    public void givenIAmUser() throws Throwable {
        setupSeleniumWebDrivers();
        goTo(INBOX_URL);
        signIn();
    }

    @And("^I have clicked \"compose a new email\"$")
    public void iComposeEmail() throws Throwable {
        WebElement composeBTN = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(By.className(COMPOSE_BTN)));

        composeBTN.click();
    }

    @And("^I have filled in the information for a recepient email and subject$")
    public void iFillInformation() throws Throwable {
        Thread.sleep(3000);

        driver.findElement(By.className(RECEIPIENT_TEXT_FIELD)).sendKeys(SIGN_IN_EMAIL);
        driver.findElement(By.className(SUBJECT_TEXT_FIELD)).sendKeys("Test");
    }


    /* ===================================================================================================
    ========================================== NORMAL FLOW ===============================================
    ======================================================================================================
     */

    @When("^I import an image file to my email$")
    public void iImportAnImageFileToMyEmail() throws Throwable {
        driver.findElement(By.xpath(ATTACHMENT_BTN)).sendKeys(IMAGE1_URL);  // Upload
        System.out.println("Uploading file to your email.. ");
        Thread.sleep(8000);  // Wait for upload
    }

    @Then("^that file should appear as an attachment$")
    public void myEmailHasAnImage() throws Throwable {
        WebElement attachment = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.className(ATTACHMENT_TEXT_FIELD_CHECK)));

        if (attachment.getText().equals("")) {  // If no string it means attachment wasn't properly added
            System.out.println("Image was not found");
        }
    }


    @And("^I can send the email with the attachment$")
    public void iCanSendEmailWithImage() throws Throwable {
        WebElement sendBTN = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(By.className(SEND_BTN)));

        sendBTN.click();

        Thread.sleep(2000);

        WebElement emailAlert = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.className(CONFIRMATION_TEXT_FIELD)));

        Assert.assertTrue(emailAlert.getText().contains("Message sent."));
    }


    /* ===================================================================================================
    ========================================== ALTERNATIVE FLOW ===============================================
    ======================================================================================================
     */

    @When("^I upload an image larger than 25mb$")
    public void uploadLargeImage() throws Throwable {
        driver.findElement(By.xpath(ATTACHMENT_BTN)).sendKeys(LARGE_IMAGE_URL);  // Upload
        System.out.println("Uploading file to your email.. ");
    }

    @Then("^it will automatically be uploaded to my google drive and attached to email$")
    public void uploadToDrive() throws Throwable {
        WebElement drivePopUp = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.className(DRIVE_POPUP_TEXT_FIELD)));  // Check to see for google drive

        if (!drivePopUp.getText().contains("Attaching File")) {  // If no string it means attachment wasn't properly added
            System.out.println("File not uploading");
        }

        Thread.sleep(10000);  // Wait for upload
    }

    @And("^I can send an email with the google drive link to the attachment$")
    public void sendDriveLink() throws Throwable {

        WebElement sendBTN = (new WebDriverWait(driver, 5))
                .until(ExpectedConditions.elementToBeClickable(By.className(SEND_BTN)));
        sendBTN.click();

        Thread.sleep(3000);

        WebElement emailAlert = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.className(CONFIRMATION_TEXT_FIELD)));

        Assert.assertTrue(emailAlert.getText().contains("Message sent."));
    }



    /* ===================================================================================================
    ========================================== ERROR FLOW ===============================================
    ======================================================================================================
     */


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


    private void signIn() {
        driver.findElement(By.className("whsOnd")).sendKeys(SIGN_IN_EMAIL);
        WebElement nextBTN = (new WebDriverWait(driver, 5))
                .until(ExpectedConditions.elementToBeClickable(By.className("qhFLie")));

        nextBTN.click();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        driver.findElement(By.className("whsOnd")).sendKeys(SIGN_IN_PASSWORD);
        nextBTN = (new WebDriverWait(driver, 5))
                .until(ExpectedConditions.elementToBeClickable(By.className("qhFLie")));

        nextBTN.click();
    }

    // Helper functions
    private void setupSeleniumWebDrivers() throws MalformedURLException {
        if (driver == null) {
            System.out.println("Setting up ChromeDriver... ");
            System.setProperty("webdriver.chrome.driver", DRIVER_PATH);
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