package com.saucelabs.Tests.OldTests;

import com.saucelabs.AnyPage;
import com.saucelabs.MapPage;
import com.saucelabs.ProblemPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * Created by nklimotc on 21.10.2014.
 */

public class zOldFiltersTest {

    WebDriver driver;
    AnyPage anyPage;
    MapPage mapPage;
    ProblemPage problemPage;

    public String afterDate = "14 лист. 2014";
    public String beforeDate = "15 лист. 2015";
    public static int typeNumber = 1;
    public static double latitude = 50.29;
    public static double longitude = 30.29;

    @BeforeSuite
    public void setUp() {

        this.driver = new FirefoxDriver();
        this.anyPage = new AnyPage(driver);
        this.mapPage = new MapPage(driver);
        this.problemPage = new ProblemPage(driver);
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.get("http://127.0.0.1:8090/#/map");
        driver.manage().window().maximize();
    }


   //@Test
   public void checkFiltersNegative() throws Exception {
        mapPage.clickZoomOut();
        mapPage.openFiltersBoard();
        mapPage.setAfterDate(afterDate);
        mapPage.setBeforeDate(beforeDate);
        mapPage.selectOnlyOneFilter(typeNumber);
        mapPage.clickAtProblemByCoordinateVisible(latitude, longitude);
        Assert.assertTrue(problemPage.getProblemType().equals(mapPage.getFilterTitle(typeNumber)));
    }

    @Test
    public void checkFiltersPositive() throws Exception {
        String imageURLsString = "";
        String imageCommentsString = "";
        anyPage.logIn("admin@.com","admin");
        anyPage.addProblemToVisibleCenter(latitude, longitude, "ProblemFor Проблеми лісів", "Проблеми лісів", "Decsription", "problemProposeTest", Arrays.asList(imageURLsString.split("\n")), Arrays.asList(imageCommentsString.split("\n")));
        anyPage.logOut();
        mapPage.clickZoomOut();
        mapPage.openFiltersBoard();
        mapPage.setAfterDate(afterDate);
        mapPage.setBeforeDate(beforeDate);
        mapPage.selectOnlyOneFilter(typeNumber);
        mapPage.clickAtProblemByCoordinateVisible(latitude, longitude);

        Assert.assertEquals(mapPage.getFilterTitle(typeNumber), problemPage.getProblemType());
        driver.close();
    }
    }

