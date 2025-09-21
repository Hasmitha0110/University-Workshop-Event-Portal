package com.ruhuna.uniplus.ui;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("ui")
public class CreateEventUITest extends UiTestBase {

    void login() {
        driver.get(baseUrl + "/login");
        driver.findElement(By.id("email")).sendKeys("admin@uni.lk");
        driver.findElement(By.id("password")).sendKeys("secret123");
        driver.findElement(By.id("loginBtn")).click();
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.urlContains("/admin"));
    }

    @Test
    void adminCreatesEvent() {
        login();
        driver.findElement(By.id("createEventBtn")).click();

        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("eventTitle")));

        driver.findElement(By.id("eventTitle")).sendKeys("Selenium Workshop");
        driver.findElement(By.id("eventVenue")).sendKeys("Main Hall");
        String date = LocalDate.now().plusDays(2).format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        WebElement dateField = driver.findElement(By.id("eventDate"));
        dateField.clear();
        dateField.sendKeys(date);
        driver.findElement(By.id("eventSubmitBtn")).click();

        // Verify it appears in the list
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(),'Selenium Workshop')]")));

        String pageText = driver.getPageSource();
        assertThat(pageText).contains("Selenium Workshop");
    }
}
