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
import java.util.ArrayList;
import java.util.Random;

public class StepDefinitions {

    // Variables
    private WebDriver driver;
    private final String DRIVER_PATH = System.getProperty("user.dir") + "/chromedriver.exe";

    // Image Files
    private final String IMAGE1_URL = System.getProperty("user.dir") + "/images/hashtag_5.jpg";
    private final String IMAGE2_URL = System.getProperty("user.dir") + "/images/hashtag_27.jpg";
    private final String IMAGE3_URL = System.getProperty("user.dir") + "/images/hashtag_107.png";
    private final String[] image_list = {IMAGE1_URL, IMAGE2_URL, IMAGE3_URL};
    private final String LARGE_IMAGE_URL = System.getProperty("user.dir") + "/images/bigimage.jpg";

    // Emails
    private final String[] recipient_list = {"ecse428AA@gmail.com", "anthony.laye@mail.mcgill.ca", "p_hockey124@hotmail.com", "anthony_laye@hotmail.com"};

    // Authentication
    private final String SIGN_IN_EMAIL = "ecse428AA@gmail.com";
    private final String SIGN_IN_PASSWORD = "Ecse428@";

    private final String INBOX_URL = "https://mail.google.com/mail/u/0/#inbox";

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

    /**
     *  Method that brings signed in user to their gmail inbox
     */
    @Given("^I am a user$")
    public void givenIAmUser() throws Throwable {
        setupSeleniumWebDrivers();
        goTo(INBOX_URL);  // Go to main inbox page
        signIn();  // Sign in to Gmail
    }

    /**
     *  Method that clicks the compose email button, and opens up an email panel
     */
    @And("^I have clicked \"compose a new email\"$")
    public void iComposeEmail() {
        WebElement composeBTN = (new WebDriverWait(driver, 15))
                .until(ExpectedConditions.elementToBeClickable(By.className(COMPOSE_BTN)));

        Assert.assertTrue(checkInitialState()); // Make sure we are in the correct initial state before starting

        composeBTN.click();  // Click the compose email button
    }

    /**
     * Method that automatically fills in recepient information, and adds a subject title for email
     */
    @And("^I have filled in the information for a recepient email and subject$")
    public void iFillInformation() {
        WebElement recipient = (new WebDriverWait(driver, 20))
                .until(ExpectedConditions.presenceOfElementLocated(By.className(RECEIPIENT_TEXT_FIELD))); // Make sure attachment appears
        Random r = new Random(); // Get random recipient for email
        int randomNumber=r.nextInt(recipient_list.length);
        recipient.sendKeys(recipient_list[randomNumber]);

        WebElement subject = (new WebDriverWait(driver, 20))
                .until(ExpectedConditions.presenceOfElementLocated(By.className(SUBJECT_TEXT_FIELD))); // Make sure attachment appears
        subject.sendKeys("Test");
    }

    /**
     *     Method that automatically adds multiple recepients to the email list and adds subject header.
     *     List of recepients were provided, method generates 2 random numbers,
     *     and compares them to make sure they are unique. These numbers are used as index values
     *     to select 2 recepients from the list of recepients at random, both selected recepients will receive the email
     */
    @And("^I have filled in the information for two recepient emails and subject$")
    public void iFillInformationTwoRecipients() {
        WebElement recipient = (new WebDriverWait(driver, 20))
                .until(ExpectedConditions.presenceOfElementLocated(By.className(RECEIPIENT_TEXT_FIELD))); // Make sure attachment appears
        Random r = new Random(); // Get random recipient for email
        int randomNumber1 = r.nextInt(recipient_list.length);
        int randomNumber2 = r.nextInt(recipient_list.length);

        while(randomNumber1 == randomNumber2)
            randomNumber2 = r.nextInt(recipient_list.length); // Ensure different random numbers

        recipient.sendKeys(recipient_list[randomNumber1] + " ");

        recipient = (new WebDriverWait(driver, 20))
                .until(ExpectedConditions.presenceOfElementLocated(By.className(RECEIPIENT_TEXT_FIELD))); // Make sure recipient textbox appears

        recipient.sendKeys(recipient_list[randomNumber2]);

        WebElement subject = (new WebDriverWait(driver, 20))
                .until(ExpectedConditions.presenceOfElementLocated(By.className(SUBJECT_TEXT_FIELD))); // Make sure suubject textbox appears
        subject.sendKeys("Test");
    }


    /* ===================================================================================================
    ========================================== NORMAL FLOW 1 =============================================
    ======================================================================================================
     */

    /**
     *  Method to import an image from the image folder.
     */
    @When("^I import an image file to my email$")
    public void iImportAnImageFileToMyEmail() {
        Random r = new Random(); // Get random recipient for email
        int randomNumber = r.nextInt(image_list.length);
        driver.findElement(By.xpath(ATTACHMENT_BTN)).sendKeys(image_list[randomNumber]);  // Upload
        System.out.println("Uploading file to your email.. ");
    }

    /**
     *  Method to import multiple images from the image folder. 2 random numbers are generated,
     *  and used to import 2 random images from the list of images provided.
     */
    @When("^I import two varying image files to my email$")
    public void iImportMultipleImageFilesToMyEmail() {
        Random r = new Random(); // Get random recipient for email
        int randomNumber = r.nextInt(image_list.length);
        int randomNumber2 = r.nextInt(image_list.length);
        while (randomNumber == randomNumber2) {
            randomNumber2 = r.nextInt(image_list.length); // Ensure different random number
        }
        driver.findElement(By.xpath(ATTACHMENT_BTN)).sendKeys(image_list[randomNumber]);  // Upload
        System.out.println("Uploading file to your email.. ");

        driver.findElement(By.xpath(ATTACHMENT_BTN)).sendKeys(image_list[randomNumber2]);
        System.out.println("Uploading file to your email.. ");
    }

    /**
     *  Method to check the attachment class from the web code, to make sure image was properly uploaded
     *  if no image was uploaded then failure occurs.
     */
    @Then("^that file should appear as an attachment$")
    public void myEmailHasAnImage() {
        WebElement attachment = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.className(ATTACHMENT_TEXT_FIELD_CHECK))); // Make sure attachment appears

        if (attachment.getText().equals("")) {  // If no string it means attachment wasn't properly added
            Assert.fail("Image was not found");
        }
    }

    /**
     *  Method to check the attachment class from the web code, to make sure image was properly uploaded
     *  if no image was uploaded then failure occurs.
     */
    @Then("^those files should appear as an attachment$")
    public void myEmailHasImages() {
        WebElement attachment = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.className(ATTACHMENT_TEXT_FIELD_CHECK))); // Make sure attachment appears

        if (attachment.getText().equals("")) {  // If no string it means attachment wasn't properly added
            Assert.fail("Image was not found");
        }
    }


    /**
     * Once image(s) have been uploaded to the email, this method allows for the email
     * along with its attachments to be sent to the recepient.
     */
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
        Assert.assertTrue(checkInitialState()); // Confirm we are back to initial state
    }



    /* ===================================================================================================
    ========================================== ALTERNATIVE FLOW ===============================================
    ======================================================================================================
     */

    /**
     * Method that uploads an image larger than 25mb.
     */
    @When("^I upload an image larger than 25mb$")
    public void uploadLargeImage(){
        driver.findElement(By.xpath(ATTACHMENT_BTN)).sendKeys(LARGE_IMAGE_URL);  // Upload
        System.out.println("Uploading file to your email.. ");
    }

    /**
     *     Method that checks to see the presence of a google drive pop up,
     *     this pop up indicates that the image is too large to be sent directly from the email
     *     the image will then be uploaded to google drive
     */
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


    /**
     *Method that checks for the presence of the google drive link, which is generated and attached
     * to the email for the recepient to access the image. Email is then sent.
     */
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
        Assert.assertTrue(checkInitialState()); // Confirm we are back to initial state
    }


    /* ===================================================================================================
    ========================================== ERROR FLOW ===============================================
    ======================================================================================================
     */

    /**
     * Uploading an image file from image directory
     */
    @When("^I upload an image file to my email$")
    public void uploadImageFileToEmail() throws Throwable {
        driver.findElement(By.xpath(ATTACHMENT_BTN)).sendKeys(IMAGE2_URL);  // Upload
        System.out.println("Uploading file to your email.. ");
    }

    /**
     * Cancel button is pressed, removing the image attachment that is being loaded
     */
    @And("^I cancel the upload$")
    public void cancelWhileUploading() throws Throwable {

        WebElement cancelBTN = (new WebDriverWait(driver, 5))
                .until(ExpectedConditions.elementToBeClickable(By.className(CANCEL_BTN)));

        cancelBTN.click();
    }

    /**
     * Sends email without any attachment files contained within it
     */
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
        Assert.assertTrue(checkInitialState()); // Confirm we are back to initial state
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
    private boolean checkInitialState(){
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