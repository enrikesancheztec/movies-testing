package com.kikesoft.moviestesting.e2e.cucumber;

import org.openqa.selenium.WebDriver;

import com.kikesoft.moviestesting.e2e.bots.ProducersBot;

public final class CucumberScenarioContext {

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();
    private static final ThreadLocal<ProducersBot> PRODUCERS_BOT = new ThreadLocal<>();

    private CucumberScenarioContext() {
    }

    public static void setDriver(WebDriver driver) {
        DRIVER.set(driver);
        PRODUCERS_BOT.remove();
    }

    public static WebDriver getDriver() {
        WebDriver driver = DRIVER.get();
        if (driver == null) {
            throw new IllegalStateException("WebDriver is not initialized for this scenario.");
        }
        return driver;
    }

    public static ProducersBot getProducersBot() {
        ProducersBot producersBot = PRODUCERS_BOT.get();
        if (producersBot == null) {
            producersBot = new ProducersBot(getDriver());
            PRODUCERS_BOT.set(producersBot);
        }
        return producersBot;
    }

    public static void clear() {
        PRODUCERS_BOT.remove();
        DRIVER.remove();
    }
}