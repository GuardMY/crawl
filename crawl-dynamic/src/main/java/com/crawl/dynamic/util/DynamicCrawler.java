package com.crawl.dynamic.util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class DynamicCrawler {
    private WebDriver driver;

    public DynamicCrawler() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--window-size=1920,1080");
        
        driver = new ChromeDriver(options);
    }

    public DynamicCrawler(ChromeOptions options) {
        driver = new ChromeDriver(options);
    }

    public String crawlPage(String url) {
        driver.get(url);
        return driver.getPageSource();
    }

    public String crawlPage(String url, Duration waitTime) {
        driver.get(url);
        WebDriverWait wait = new WebDriverWait(driver, waitTime);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        return driver.getPageSource();
    }

    public String crawlPage(String url, String waitForElementCss) {
        driver.get(url);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(waitForElementCss)));
        return driver.getPageSource();
    }

    public List<String> extractLinks(String url) {
        driver.get(url);
        List<WebElement> linkElements = driver.findElements(By.tagName("a"));
        List<String> links = new ArrayList<>();
        for (WebElement element : linkElements) {
            String href = element.getDomAttribute("href");
            if (href != null && !href.isEmpty()) {
                links.add(href);
            }
        }
        return links;
    }

    public String getTitle(String url) {
        driver.get(url);
        return driver.getTitle();
    }

    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }

    public WebDriver getDriver() {
        return driver;
    }
}
