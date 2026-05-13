package com.kikesoft.moviestesting.e2e.producers;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import com.kikesoft.moviestesting.e2e.bots.ProducersBot;
import com.kikesoft.moviestesting.e2e.support.DriverFactory;

class CreateProducersTest {

    @Test
    void shouldKeepSaveDisabledWhenNameIsEmpty() {
        WebDriver driver = DriverFactory.createChromeDriver();

        try {
            ProducersBot producersBot = new ProducersBot(driver);

            producersBot.openCreateForm();
            producersBot.setProducerName("");
            producersBot.setProducerProfile("");
            producersBot.setProducerProfile(buildProfileWith50Chars());

            assertTrue(!producersBot.isSaveEnabled(), "Expected save button to stay disabled when name is empty");
        } finally {
            driver.quit();
        }
    }

    @Test
    void shouldCreateProducerAndShowItInList() {
        WebDriver driver = DriverFactory.createChromeDriver();

        try {
            String producerName = "Producer " + System.currentTimeMillis();
            String producerProfile = buildProfileWith50Chars();
            ProducersBot producersBot = new ProducersBot(driver);

            producersBot.openCreateForm();
            producersBot.setProducerName(producerName);
            producersBot.setProducerProfile(producerProfile);

            assertTrue(producersBot.isSaveEnabled(), "Expected save button to be enabled for a valid form");
            producersBot.saveProducer();

            assertTrue(producersBot.hasProducerNamed(producerName), "Expected new producer to be visible in the producers list");
        } finally {
            driver.quit();
        }
    }

    private static String buildProfileWith50Chars() {
        return "Lorem ipsum dolor sit amet, consectetur adipiscing elit.".substring(0, 50);
    }
}
