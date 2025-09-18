package com.ruhuna.uniplus.ui;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.assertj.core.api.Assertions.assertThat;
import java.time.Duration;

public class AdminLoginUITest extends UiTestBase {

    @Test
    void adminCanLogin() {
        driver.get(baseUrl + "/login");
        driver.findElement(By.id("email")).sendKeys("admin@uni.lk");
        driver.findElement(By.id("password")).sendKeys("secret123");
        driver.findElement(By.id("loginBtn")).click();

        // Navigate to admin dashboard automatically via your code, then assert
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.urlContains("/admin"));

        String h1 = driver.findElement(By.tagName("h1")).getText();
        assertThat(h1.toLowerCase()).contains("admin dashboard");
    }
}
