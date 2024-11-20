
package com.genaro1024.xmlapi.xmlapi.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
public class XMLSchemaControllerTest {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
        driver = new ChromeDriver();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testGetAllXML() {
        driver.get("http://localhost:8080/xmlschema/all");
        WebElement body = driver.findElement(By.tagName("body"));
        // Add assertions based on expected XML content
        assertEquals("expected XML content", body.getText());
    }

    @Test
    public void testGetXMLBySearch() {
        driver.get("http://localhost:8080/xmlschema/search/sample");
        WebElement body = driver.findElement(By.tagName("body"));
        // Add assertions based on expected XML content
        assertEquals("expected XML content for search", body.getText());
    }

    @Test
    public void testEcho() {
        driver.get("http://localhost:8080/xmlschema/echo/test");
        WebElement body = driver.findElement(By.tagName("body"));
        assertEquals("test", body.getText());
    }

    @Test
    public void testUppercase() {
        driver.get("http://localhost:8080/xmlschema/uppercase/test");
        WebElement body = driver.findElement(By.tagName("body"));
        assertEquals("TEST", body.getText());
    }

    @Test
    public void testLowercase() {
        driver.get("http://localhost:8080/xmlschema/lowercase/TEST");
        WebElement body = driver.findElement(By.tagName("body"));
        assertEquals("test", body.getText());
    }
}