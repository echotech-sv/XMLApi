
package com.genaro1024.xmlapi.xmlapi.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class XMLSchemaControllerTest {

    
    private WebDriver driver;

    @BeforeEach
    public void setUp() throws MalformedURLException {
        ChromeOptions options = new ChromeOptions();
        driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void testGetAllXML() {
        driver.get("http://localhost:8081/xmlschema/all");
        WebElement body = driver.findElement(By.tagName("body"));
        // Add assertions based on expected XML content
        assertEquals("expected XML content", body.getText());
    }

    @Test
    void testGetXMLBySearch() {
        driver.get("http://localhost:8081/xmlschema/search/sample");
        WebElement body = driver.findElement(By.tagName("body"));
        // Add assertions based on expected XML content
        assertEquals("expected XML content for search", body.getText());
    }

    @Test
    void testEcho() {
        driver.get("http://localhost:8081/xmlschema/echo/test");
        WebElement body = driver.findElement(By.tagName("body"));
        assertEquals("test", body.getText());
    }

    @Test
    void testUppercase() {
        driver.get("http://localhost:8081/xmlschema/uppercase/test");
        WebElement body = driver.findElement(By.tagName("body"));
        assertEquals("TEST", body.getText());
    }

    @Test
    void testLowercase() {
        driver.get("http://localhost:8081/xmlschema/lowercase/TEST");
        WebElement body = driver.findElement(By.tagName("body"));
        assertEquals("test", body.getText());
    }
}