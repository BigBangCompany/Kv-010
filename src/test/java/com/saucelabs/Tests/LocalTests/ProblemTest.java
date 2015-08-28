package com.saucelabs.Tests.LocalTests;

import com.saucelabs.AdminPage;
import com.saucelabs.ProblemPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.util.concurrent.TimeUnit;

/**
 * Created by vadym on 28.08.15.
 */
public class ProblemTest {

    private WebDriver driver;
    ProblemPage problemPage;
    AdminPage adminPage;

    @BeforeSuite
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        driver.get("http://localhost:8090/#/map");
        driver.manage().window().maximize();
        problemPage = new ProblemPage(driver);
        adminPage = new AdminPage(driver);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @AfterSuite
    public  void turnDown() {
        this.driver.quit();
    }


}
