package com.saucelabs.Tests.LocalTests;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.saucelabs.AdminPage;
import com.saucelabs.AnyPage;
import com.saucelabs.ProblemPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utility.Constant;
import utility.ExcelUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import com.saucelabs.Tests.DAO.DeleteUserDAO;
/*
 * Created by Yermek on 27.11.2014.
*/

public class SmokeTest {

    @DataProvider(name = "sampleTestData", parallel = false)
    public static Object[][] testDataExample() {
        return new Object[][]{
                new Object[]{
                        "50.9",
                        "30.9",
                        "Serious problem",
                        "Загрози біорізноманіттю",
                        "Description of a problem",
                        "Solution proposal",
                        "" + "\n" + "",
                        //"",
                        "imageComment1" + "\n" + "imageComment2",
                        //"",
                        "admin@.com",
                        "admin",
                        "testFirstName",
                        "testLastName",
                        "test646@test.com",
                        "test01",
                        "Comment1"
                }
        };
    }

    @Test(dataProvider = "sampleTestData")
    public void sampleAll(String latitudeString, String longitudeString, String problemTitle,
                          String problemType, String problemDescription, String problemSolution,
                          String imageURLsString, String imageCommentsString,
                          String adminEmail, String adminPassword,
                          String newUserFirstName, String newUserLastName,
                          String newUserEmail, String newUserPassword,
                          String userCommentsString) throws IOException {

            double          latitude        = Double.parseDouble(latitudeString);
            double          longitude       = Double.parseDouble(longitudeString);
            List<String>    imageURLs       = Arrays.asList(imageURLsString.split("\n"));
            List<String>    imageComments   = Arrays.asList(imageCommentsString.split("\n"));
            int             id;
        WebDriver driver = new FirefoxDriver();
        driver.get("http://localhost:8090");
        //driver.get("http://176.36.11.25");
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

        AnyPage anyPage         = new AnyPage(driver);
        ProblemPage problemPage = new ProblemPage(driver);

        anyPage.register(newUserFirstName, newUserLastName, newUserEmail, newUserPassword);
        anyPage.addProblemToVisibleCenter(latitude, longitude, problemTitle, problemType, problemDescription, problemSolution,
                imageURLs, imageComments);
        anyPage.logOut();

        anyPage.logIn(adminEmail, adminPassword);

        try {
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        id = problemPage.getProblemId(latitude, longitude);

        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        problemPage.openProblemById(id);

        Assert.assertEquals(problemPage.getProblemTitle(), problemTitle);

        //problemPage.deleteOpenedProblem();

        anyPage.logOut();

        driver.quit();
    }
}
