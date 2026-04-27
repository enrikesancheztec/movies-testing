package com.kikesoft.moviestesting.e2e.cucumber;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.kikesoft.moviestesting.e2e.E2eConfig;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ProducersSteps {

    private static final String LIST_URL = E2eConfig.baseUrl() + "/en-US/producers";
    private static final String CREATE_URL = E2eConfig.baseUrl() + "/en-US/producers/create";

    private WebDriver driver;
    private WebDriverWait wait;
    private String producerName;

    @Before("@producers")
    public void setUpDriver() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @After("@producers")
    public void tearDownDriver() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Given("I open the producers list page")
    public void iOpenTheProducersListPage() {
        driver.get(LIST_URL);
    }

    @Then("I should see {string} text on the page")
    public void iShouldSeeTextOnThePage(String expectedText) {
        boolean isTextVisible = wait.until(
                ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), expectedText)
        );
        assertTrue(isTextVisible, "Expected text to be visible on the page: " + expectedText);
    }

    @Then("every listed producer should have a details link ending with its producer id")
    public void everyListedProducerShouldHaveAValidDetailsLink() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-testid='producers-list']")));
        wait.until(d -> !d.findElements(By.cssSelector("[data-testid^='producer-row-']")).isEmpty());

        List<WebElement> rows = driver.findElements(By.cssSelector("[data-testid^='producer-row-']"));
        assertTrue(!rows.isEmpty(), "Expected at least one producer row");

        for (WebElement row : rows) {
            String testId = row.getAttribute("data-testid");
            String producerId = testId.replace("producer-row-", "");
            WebElement detailsLink = row.findElement(By.cssSelector("a[data-testid='view-producer-details']"));
            String href = detailsLink.getAttribute("href");

            assertTrue(
                    href != null && href.endsWith("/en-US/producers/" + producerId),
                    "Expected details link to end with /en-US/producers/" + producerId
            );
        }
    }

    @Given("I open the create producer page from the producers list")
    public void iOpenTheCreateProducerPageFromTheProducersList() {
        driver.get(LIST_URL);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-testid='producers-list']")));

        WebElement createButton = waitForElementOrFail(
                By.cssSelector("[data-testid='create-producer-button']"),
                "create-producer-button"
        );
        createButton.click();

        wait.until(ExpectedConditions.urlToBe(CREATE_URL));
    }

    @When("I fill producer profile with 50 characters and leave producer name empty")
    public void iFillProducerProfileAndLeaveProducerNameEmpty() {
        WebElement nameInput = waitForElementOrFail(
                By.cssSelector("[data-testid='producer-name-input']"),
                "producer-name-input"
        );
        WebElement profileInput = waitForElementOrFail(
                By.cssSelector("[data-testid='producer-profile-input']"),
                "producer-profile-input"
        );

        nameInput.clear();
        profileInput.clear();
        profileInput.sendKeys(buildProfileWith50Chars());
    }

    @Then("the save producer button should be disabled")
    public void theSaveProducerButtonShouldBeDisabled() {
        WebElement saveButton = waitForElementOrFail(
                By.cssSelector("[data-testid='save-producer-button']"),
                "save-producer-button"
        );
        assertFalse(saveButton.isEnabled(), "Expected save button to stay disabled when name is empty");
    }

    @When("I fill the producer form with a unique valid name and profile")
    public void iFillTheProducerFormWithAUniqueValidNameAndProfile() {
        producerName = "Producer " + System.currentTimeMillis();
        String producerProfile = buildProfileWith50Chars();

        WebElement nameInput = waitForElementOrFail(
                By.cssSelector("[data-testid='producer-name-input']"),
                "producer-name-input"
        );
        WebElement profileInput = waitForElementOrFail(
                By.cssSelector("[data-testid='producer-profile-input']"),
                "producer-profile-input"
        );

        nameInput.clear();
        nameInput.sendKeys(producerName);
        profileInput.clear();
        profileInput.sendKeys(producerProfile);
    }

    @Then("the save producer button should be enabled")
    public void theSaveProducerButtonShouldBeEnabled() {
        WebElement saveButton = waitForElementOrFail(
                By.cssSelector("[data-testid='save-producer-button']"),
                "save-producer-button"
        );
        assertTrue(saveButton.isEnabled(), "Expected save button to be enabled for a valid form");
    }

    @When("I save the producer")
    public void iSaveTheProducer() {
        WebElement saveButton = waitForElementOrFail(
                By.cssSelector("[data-testid='save-producer-button']"),
                "save-producer-button"
        );
        saveButton.click();
    }

    @Then("I should be redirected to the producers list")
    public void iShouldBeRedirectedToTheProducersList() {
        wait.until(ExpectedConditions.urlToBe(LIST_URL));
    }

    @And("I should find the newly created producer in the list")
    public void iShouldFindTheNewlyCreatedProducerInTheList() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-testid='producers-list']")));
        wait.until(d -> !d.findElements(By.cssSelector("[data-testid^='producer-row-']")).isEmpty());

        List<WebElement> rows = driver.findElements(By.cssSelector("[data-testid^='producer-row-']"));
        boolean producerFound = rows.stream().anyMatch(row -> row.getText().contains(producerName));

        assertTrue(producerFound, "Expected new producer to be visible in the producers list");
    }

    private WebElement waitForElementOrFail(By locator, String selectorName) {
        try {
            return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (TimeoutException ex) {
            fail(
                    "Selector not found: [data-testid='" + selectorName + "']. "
                            + "Please add this selector in the web page to support stable E2E tests."
            );
            return null;
        }
    }

    private String buildProfileWith50Chars() {
        return "Lorem ipsum dolor sit amet, consectetur adipiscing elit.".substring(0, 50);
    }
}
