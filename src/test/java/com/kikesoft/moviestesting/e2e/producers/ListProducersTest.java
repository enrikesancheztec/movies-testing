package com.kikesoft.moviestesting.e2e.producers;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.kikesoft.moviestesting.e2e.E2eConfig;

class ListProducersTest {

    private static final String LIST_URL = E2eConfig.baseUrl() + "/en-US/producers";

    @Test
    void shouldDisplayProducersListText() {
        WebDriver driver = new ChromeDriver();

        try {
            driver.get(LIST_URL);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            boolean isTextVisible = wait.until(
                    ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), "Producers List")
            );

            assertTrue(isTextVisible, "Expected 'Producers List' text to be visible on the page");
        } finally {
            driver.quit();
        }
    }

    @Test
    void shouldHaveProducerDetailLinkWhenProducerIsListed() {
        WebDriver driver = new ChromeDriver();

        try {
            driver.get(LIST_URL);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-testid='producers-list']")));
            wait.until(d -> !d.findElements(By.cssSelector("[data-testid^='producer-row-']")).isEmpty());

            List<WebElement> rows = driver.findElements(By.cssSelector("[data-testid^='producer-row-']"));
            assertTrue(rows.size() > 0, "Expected at least one producer row");

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
        } finally {
            driver.quit();
        }
    }
}
