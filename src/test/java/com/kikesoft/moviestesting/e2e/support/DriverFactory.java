package com.kikesoft.moviestesting.e2e.support;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public final class DriverFactory {

    private DriverFactory() {
    }

    public static WebDriver createChromeDriver() {
        return new ChromeDriver();
    }
}