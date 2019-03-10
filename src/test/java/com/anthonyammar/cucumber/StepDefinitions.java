package test.java.com.anthonyammar.cucumber;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
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

    // Image Files
    private final String IMAGE1_URL = System.getProperty("user.dir") + "/images/hashtag_5.jpg";
    private final String LARGE_IMAGE_URL = System.getProperty("user.dir") + "/images/bigimage.jpg";
    private final String MEDIUM_IMAGE_URL = System.getProperty("user.dir") + "/images/bangkok.jpg";
    private final String DRIVER_PATH = System.getProperty("user.dir") + "/chromedriver.exe";

    // Authentication
    private final String SIGN_IN_EMAIL = "ecse428AA@gmail.com";
    private final String SIGN_IN_PASSWORD = "Ecse428@";

    private final String INBOX_URL = "https://mail.google.com/mail/u/0/#inbox";
    private final String DRAFT_URL = "https://mail.google.com/mail/u/0/#drafts";

    // HTML Class and ID names for selenium web elements
    private final String ATTACHMENT_BTN = "//input[@type='file']";
    private final String COMPOSE_BTN = "z0";
    private final String SEND_BTN = "gU";
    private final String CANCEL_BTN = "vq";
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
        goTo(INBOX_URL);  // Go to main inbox page
        signIn();  // Sign in to Gmail
    }

    @And("^I have clicked \"compose a new email\"$")
    public void iComposeEmail() {
        WebElement composeBTN = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(By.className(COMPOSE_BTN)));

        Assert.assertTrue(checkInitialFinalState()); // Make sure we are in the correct initial state before starting

        composeBTN.click();  // Click the compose email button
    }

    @And("^I have filled in the information for a recepient email and subject$")
    public void iFillInformation() {
        WebElement recipient = (new WebDriverWait(driver, 5))
                .until(ExpectedConditions.presenceOfElementLocated(By.className(RECEIPIENT_TEXT_FIELD))); // Make sure attachment appears
        recipient.sendKeys(SIGN_IN_EMAIL);

        WebElement subject = (new WebDriverWait(driver, 5))
                .until(ExpectedConditions.presenceOfElementLocated(By.className(SUBJECT_TEXT_FIELD))); // Make sure attachment appears
        subject.sendKeys("Test");
    }


    /* ===================================================================================================
    ========================================== NORMAL FLOW ===============================================
    ======================================================================================================
     */

    @When("^I import an image file to my email$")
    public void iImportAnImageFileToMyEmail() {
        driver.findElement(By.xpath(ATTACHMENT_BTN)).sendKeys(IMAGE1_URL);  // Upload
        System.out.println("Uploading file to your email.. ");
    }

    @Then("^that file should appear as an attachment$")
    public void myEmailHasAnImage() {
        WebElement attachment = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.className(ATTACHMENT_TEXT_FIELD_CHECK))); // Make sure attachment appears

        if (attachment.getText().equals("")) {  // If no string it means attachment wasn't properly added
            Assert.fail("Image was not found");
        }
    }


    @And("^I can send the email with the attachment$")
    public void iCanSendEmailWithImage(){
        WebElement sendBTN = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(By.className(SEND_BTN)));

        sendBTN.click(); // Click send button

        WebElement emailAlert = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.className(CONFIRMATION_TEXT_FIELD))); // Look for sent confirmation

        try {
            while(emailAlert.getText().contains("Sending")); // Wait for sending dialog to go away
        }
        catch (Exception e){
            // Will enter here when no longer sending
        }

        emailAlert = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.className(CONFIRMATION_TEXT_FIELD))); // Look for sent confirmation

        System.out.println(emailAlert.getText());
        Assert.assertTrue(confirmSent(emailAlert)); // Confirm email was sent
        Assert.assertTrue(checkInitialFinalState());
    }


    /* ===================================================================================================
    ========================================== ALTERNATIVE FLOW ===============================================
    ======================================================================================================
     */

    @When("^I upload an image larger than 25mb$")
    public void uploadLargeImage(){
        driver.findElement(By.xpath(ATTACHMENT_BTN)).sendKeys(LARGE_IMAGE_URL);  // Upload
        System.out.println("Uploading file to your email.. ");
    }

    @Then("^it will automatically be uploaded to my google drive and attached to email$")
    public void uploadToDrive(){
        WebElement drivePopUp = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.className(DRIVE_POPUP_TEXT_FIELD)));  // Check to see for google drive popup

        if (!drivePopUp.getText().contains("Attaching File")) {  // If no string it means attachment wasn't properly added
            Assert.fail("File not uploading");
        }

        try {
            while(drivePopUp.getText().contains("Attaching File")); // Wait for drive pop up to go away
        }
        catch (Exception e){
            // Will enter here when no longer attaching file
        }
    }

    @And("^I can send an email with the google drive link to the attachment$")
    public void sendDriveLink() {
        WebElement sendBTN = (new WebDriverWait(driver, 50))
                .until(ExpectedConditions.elementToBeClickable(By.className(SEND_BTN)));
        sendBTN.click(); // Send email

        WebElement emailAlert = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.className(CONFIRMATION_TEXT_FIELD)));

        try {
            while(emailAlert.getText().contains("Sending")); // Wait for sending dialog to go away
        }
        catch (Exception e){
            // Will enter here when no longer sending
        }

        emailAlert = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.className(CONFIRMATION_TEXT_FIELD))); // Look for sent confirmation
        Assert.assertTrue(confirmSent(emailAlert)); // Confirm email was sent
        Assert.assertTrue(checkInitialFinalState());
    }


    /* ===================================================================================================
    ========================================== ERROR FLOW ===============================================
    ======================================================================================================
     */

    //Uploading an image file from image directory
    @When("^I upload an image file to my email$")
    public void uploadImageFileToEmail() throws Throwable {
        driver.findElement(By.xpath(ATTACHMENT_BTN)).sendKeys(IMAGE1_URL);  // Upload
        System.out.println("Uploading file to your email.. ");
    }

    //Cancel button is pressed, removing the image attachment that is being loaded
    @And("^I cancel the upload$")
    public void cancelWhileUploading() throws Throwable {

        WebElement cancelBTN = (new WebDriverWait(driver, 5))
                .until(ExpectedConditions.elementToBeClickable(By.className(CANCEL_BTN)));

        cancelBTN.click();
    }

    // Sends email
    @Then("^my email will be sent without an image attachment$")
    public void emailSentWithoutImage() throws Throwable {

        try {
            WebElement attachment = (new WebDriverWait(driver, 4))
                    .until(ExpectedConditions.presenceOfElementLocated(By.className(ATTACHMENT_TEXT_FIELD_CHECK))); // Make sure attachment appears
        }
        catch (Exception e){
            System.out.println("Attachment succesfully deleted");
            // If we enter here, it means attachment cannot be found and was succesfully deleted
        }


        WebElement sendBTN = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(By.className(SEND_BTN)));

        sendBTN.click();

        WebElement emailAlert = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.className(CONFIRMATION_TEXT_FIELD)));

        try {
            while(emailAlert.getText().contains("Sending")); // Wait for sending dialog to go away
        }
        catch (Exception e){
            // Will enter here when no longer sending
        }

        emailAlert = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.className(CONFIRMATION_TEXT_FIELD))); // Look for sent confirmation
        Assert.assertTrue(confirmSent(emailAlert)); // Confirm email was sent
        Assert.assertTrue(checkInitialFinalState()); // Confirm we are in final state
    }


    /* ===================================================================================================
    ========================================== HELPER METHODS ============================================
    ======================================================================================================
     */

    /**
     * Confirms that the email was actually sent
     * @return isSent
     */
    private boolean confirmSent(WebElement popup){
        return popup.getText().contains("Message sent.");
    }

    /**
     * Method ensure that we are in the correct initial and final state (since they are the same)!
     * @return isInState
     */
    private boolean checkInitialFinalState(){
        return driver.getCurrentUrl().equals(INBOX_URL);
    }

    private void signIn() {
        driver.findElement(By.className("whsOnd")).sendKeys(SIGN_IN_EMAIL); // Enter email
        WebElement nextBTN = (new WebDriverWait(driver, 5))
                .until(ExpectedConditions.elementToBeClickable(By.className("qhFLie")));

        nextBTN.click(); // Next page

        WebElement welcome = (new WebDriverWait(driver, 5))
                .until(ExpectedConditions.presenceOfElementLocated(By.className("jXeDnc"))); // Find password field

        try{
            while(!welcome.getText().contains("Welcome")); // Wait until it becomes a password field
        }
        catch (Exception e){

        } // After this we are at password field


        WebElement password = (new WebDriverWait(driver, 5))
                .until(ExpectedConditions.presenceOfElementLocated(By.className("whsOnd"))); // Find password field
        password.sendKeys(SIGN_IN_PASSWORD);  // Enter password

        nextBTN = (new WebDriverWait(driver, 5))
                .until(ExpectedConditions.elementToBeClickable(By.className("qhFLie")));

        nextBTN.click(); // Sign in
    }

    // Helper functions
    private void setupSeleniumWebDrivers() throws MalformedURLException {
        if (driver == null) {
            System.out.println("ChromeDriver is being set up.");
            System.setProperty("webdriver.chrome.driver", DRIVER_PATH);

            driver = new ChromeDriver();
            System.out.println("Set up.");
        }
    }

    private void goTo(String url) {
        if (driver != null) {
            System.out.println("Going to " + url);
            driver.get(url);
        }
    }
}