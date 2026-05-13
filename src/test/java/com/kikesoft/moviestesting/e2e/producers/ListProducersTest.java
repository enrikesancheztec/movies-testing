package com.kikesoft.moviestesting.e2e.producers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import com.kikesoft.moviestesting.e2e.bots.ProducersBot;
import com.kikesoft.moviestesting.e2e.support.DriverFactory;

class ListProducersTest {

    @Test
    void shouldDisplayProducersListText() {
        WebDriver driver = DriverFactory.createChromeDriver();

        try {
            ProducersBot producersBot = new ProducersBot(driver);

            producersBot.openList();

            assertTrue(producersBot.isListTitleVisible(), "Expected 'Producers List' text to be visible on the page");
        } finally {
            driver.quit();
        }
    }

    @Test
    void shouldHaveProducerDetailLinkWhenProducerIsListed() {
        WebDriver driver = DriverFactory.createChromeDriver();

        try {
            ProducersBot producersBot = new ProducersBot(driver);

            producersBot.openList();
            producersBot.waitUntilListReady();

            List<String> producerIds = producersBot.getListedProducerIds();
            assertTrue(producerIds.size() > 0, "Expected at least one producer row");

            for (String producerId : producerIds) {
                String href = producersBot.getProducerDetailsHref(producerId);

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
