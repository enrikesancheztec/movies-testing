package com.kikesoft.moviestesting.e2e.bots;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.fail;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.kikesoft.moviestesting.e2e.E2eConfig;

public class ProducersBot extends BaseBot {

    private static final String LIST_PATH = "/en-US/producers";
    private static final String CREATE_PATH = "/en-US/producers/create";
    private static final String LIST_TITLE = "Producers List";
    private static final String ROW_PREFIX = "producer-row-";

    private static final By BODY = By.tagName("body");
    private static final By PRODUCERS_LIST = By.cssSelector("[data-testid='producers-list']");
    private static final By PRODUCER_ROWS = By.cssSelector("[data-testid^='producer-row-']");
    private static final By CREATE_PRODUCER_BUTTON = By.cssSelector("[data-testid='create-producer-button']");
    private static final By PRODUCER_NAME_INPUT = By.cssSelector("[data-testid='producer-name-input']");
    private static final By PRODUCER_PROFILE_INPUT = By.cssSelector("[data-testid='producer-profile-input']");
    private static final By SAVE_PRODUCER_BUTTON = By.cssSelector("[data-testid='save-producer-button']");
    private static final By VIEW_PRODUCER_DETAILS = By.cssSelector("a[data-testid='view-producer-details']");

    public ProducersBot(WebDriver driver) {
        super(driver);
    }

    public void openList() {
        openPath(LIST_PATH);
    }

    public boolean isListTitleVisible() {
        return waitForText(BODY, LIST_TITLE);
    }

    public void waitUntilListReady() {
        waitForPresence(PRODUCERS_LIST);
        wait.until(webDriver -> !webDriver.findElements(PRODUCER_ROWS).isEmpty());
    }

    public List<String> getListedProducerIds() {
        return getProducerRows().stream()
                .map(row -> row.getAttribute("data-testid"))
                .map(testId -> testId.replace(ROW_PREFIX, ""))
                .collect(Collectors.toList());
    }

    public boolean hasProducerNamed(String producerName) {
        return getProducerRows().stream().anyMatch(row -> row.getText().contains(producerName));
    }

    public String getProducerDetailsHref(String producerId) {
        WebElement row = findProducerRow(producerId);
        return row.findElement(VIEW_PRODUCER_DETAILS).getAttribute("href");
    }

    public void openProducerDetails(String producerId) {
        WebElement row = findProducerRow(producerId);
        row.findElement(VIEW_PRODUCER_DETAILS).click();
        wait.until(webDriver -> webDriver.getCurrentUrl().endsWith(LIST_PATH + "/" + producerId));
    }

    public void openCreateForm() {
        openList();
        waitForPresence(PRODUCERS_LIST);
        waitForElementOrFail(CREATE_PRODUCER_BUTTON, "create-producer-button").click();
        waitForUrl(E2eConfig.baseUrl() + CREATE_PATH);
    }

    public void setProducerName(String producerName) {
        WebElement nameInput = waitForElementOrFail(PRODUCER_NAME_INPUT, "producer-name-input");
        nameInput.clear();
        nameInput.sendKeys(producerName);
    }

    public void setProducerProfile(String producerProfile) {
        WebElement profileInput = waitForElementOrFail(PRODUCER_PROFILE_INPUT, "producer-profile-input");
        profileInput.clear();
        profileInput.sendKeys(producerProfile);
    }

    public boolean isSaveEnabled() {
        return waitForElementOrFail(SAVE_PRODUCER_BUTTON, "save-producer-button").isEnabled();
    }

    public void saveProducer() {
        waitForElementOrFail(SAVE_PRODUCER_BUTTON, "save-producer-button").click();
        waitForUrl(E2eConfig.baseUrl() + LIST_PATH);
        waitUntilListReady();
    }

    private List<WebElement> getProducerRows() {
        waitUntilListReady();
        return findElements(PRODUCER_ROWS);
    }

    private WebElement findProducerRow(String producerId) {
        waitUntilListReady();
        return waitForElementOrFail(By.cssSelector("[data-testid='" + ROW_PREFIX + producerId + "']"), ROW_PREFIX + producerId);
    }

    private WebElement waitForElementOrFail(By locator, String selectorName) {
        try {
            return waitForPresence(locator);
        } catch (TimeoutException ex) {
            fail(
                    "Selector not found: [data-testid='" + selectorName + "']. "
                            + "Please add this selector in the web page to support stable E2E tests."
            );
            return null;
        }
    }
}