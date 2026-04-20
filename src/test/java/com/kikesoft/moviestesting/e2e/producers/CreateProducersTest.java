package com.kikesoft.moviestesting.e2e.producers;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.kikesoft.moviestesting.e2e.E2eConfig;

class CreateProducersTest {

    private static final String LIST_URL = E2eConfig.baseUrl() + "/en-US/producers";
    private static final String CREATE_URL = E2eConfig.baseUrl() + "/en-US/producers/create";

    @Test
    void shouldKeepSaveDisabledWhenNameIsEmpty() {
        WebDriver driver = new ChromeDriver();

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            openCreateProducerFormFromList(driver, wait);

            WebElement nameInput = waitForElementOrFail(
                    wait,
                    By.cssSelector("[data-testid='producer-name-input']"),
                    "producer-name-input"
            );
            WebElement profileInput = waitForElementOrFail(
                    wait,
                    By.cssSelector("[data-testid='producer-profile-input']"),
                    "producer-profile-input"
            );
            WebElement saveButton = waitForElementOrFail(
                    wait,
                    By.cssSelector("[data-testid='save-producer-button']"),
                    "save-producer-button"
            );

            nameInput.clear();
            profileInput.clear();
            profileInput.sendKeys(buildProfileWith50Chars());

            assertTrue(!saveButton.isEnabled(), "Expected save button to stay disabled when name is empty");
        } finally {
            driver.quit();
        }
    }

    @Test
    void shouldCreateProducerAndShowItInList() {
        WebDriver driver = new ChromeDriver();

        try {
            String producerName = "Producer " + System.currentTimeMillis();
            String producerProfile = buildProfileWith50Chars();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            openCreateProducerFormFromList(driver, wait);

            WebElement nameInput = waitForElementOrFail(
                    wait,
                    By.cssSelector("[data-testid='producer-name-input']"),
                    "producer-name-input"
            );
            WebElement profileInput = waitForElementOrFail(
                    wait,
                    By.cssSelector("[data-testid='producer-profile-input']"),
                    "producer-profile-input"
            );
            WebElement saveButton = waitForElementOrFail(
                    wait,
                    By.cssSelector("[data-testid='save-producer-button']"),
                    "save-producer-button"
            );

            nameInput.clear();
            nameInput.sendKeys(producerName);
            profileInput.clear();
            profileInput.sendKeys(producerProfile);

            assertTrue(saveButton.isEnabled(), "Expected save button to be enabled for a valid form");
            saveButton.click();

            wait.until(ExpectedConditions.urlToBe(LIST_URL));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-testid='producers-list']")));
            wait.until(d -> !d.findElements(By.cssSelector("[data-testid^='producer-row-']")).isEmpty());

            List<WebElement> rows = driver.findElements(By.cssSelector("[data-testid^='producer-row-']"));
            boolean producerFound = rows.stream().anyMatch(row -> row.getText().contains(producerName));

            assertTrue(producerFound, "Expected new producer to be visible in the producers list");
        } finally {
            driver.quit();
        }
    }

    private static String buildProfileWith50Chars() {
        return "Lorem ipsum dolor sit amet, consectetur adipiscing elit.".substring(0, 50);
    }

    private static void openCreateProducerFormFromList(WebDriver driver, WebDriverWait wait) {
        driver.get(LIST_URL);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-testid='producers-list']")));

        WebElement createButton = waitForElementOrFail(
                wait,
                By.cssSelector("[data-testid='create-producer-button']"),
                "create-producer-button"
        );
        createButton.click();

        wait.until(ExpectedConditions.urlToBe(CREATE_URL));
    }

    private static WebElement waitForElementOrFail(WebDriverWait wait, By locator, String selectorName) {
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
}
