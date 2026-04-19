package com.kikesoft.moviestesting.e2e.producers;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

class ListProducersTest {

    @Test
    void shouldDisplayProducersListText() {
        WebDriver driver = new ChromeDriver();

        try {
            driver.get("http://localhost:3000/en-US/producers");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            boolean isTextVisible = wait.until(
                    ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), "Producers List")
            );

            assertTrue(isTextVisible, "Expected 'Producers List' text to be visible on the page");
        } finally {
            driver.quit();
        }
    }
}
