package com.saucelabs;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.api.DesktopScreenRegion;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.Target;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.robot.desktop.DesktopKeyboard;
import org.sikuli.api.robot.desktop.DesktopMouse;
import org.sikuli.api.visual.Canvas;
import org.sikuli.api.visual.DesktopCanvas;
import utility.ClipboardUploadThread;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Refactoring by Vadym on 08/25/15.
 */

public class AnyPage extends MapPage implements IAnyPage, IMapPage {

    public static final By ADD_PROBLEM_BUTTON = By.xpath("//*[@class='navbar-brand b-menu__button']");
    public static final By ADD_PROBLEM_NEXT_TAB2_BUTTON = By.xpath("//button[@class='btn btn-default btn-sm ng-scope']");
    public static final By PROBLEM_NAME_TEXT_BOX = By.id("problemName");
    public static final By PROBLEM_TYPE_DROP_DOWN_LIST = By.cssSelector("#select-field option");
    public static final By PROBLEM_DESCRIPTION_FIELD = By.id("description-field");
    public static final By PROBLEM_PROPOSE_FIELD = By.id("proposal-field");
    public static final By DROP_ZONE = By.xpath("//div[contains(@class,'dz-clickable')]/span");
    public static final By IMAGE_COMMENT_TEXT_BOX = By.cssSelector("textarea.comment_field");
    public static final By ADD_PROBLEM_SUBMIT_BUTTON = By.id("btn-submit");
    public static final By ALERT_WINDOW = By.className("alert");
    public static final By CLOSE_CROSS_IN_ALERT_WINDOW = By.className("close");
    public static final By LOGIN_LINK = By.linkText("\u0412\u0425\u0406\u0414"); // 'Вхід'
    public static final By BODY = By.xpath("//body");
    public static final By ADD_PROBLEM_TAB3_IMAGE = By.className("fa-file-photo-o");
    public static final By OPEN_FIRST_RESOURCE_MENU = By.className("fa-caret-down");
    public static final By FIRST_RESOURCE_IN_MENU = By.xpath("//ul[@id='b-header__resources']/li/a");
    public static final By TITLE_EXPRESSION = By.tagName("h1");
    public static final By EMAIL_FIELD = By.name("email");
    public static final By PASSWORD_FIELD = By.name("password");
    public static final By LOGIN_BUTTON = By.id("login-button");
    public static final By USER_PICTOGRAM = By.className("fa-user");
    public static final By LOGOUT_LINK = By.linkText("\u0412\u0418\u0419\u0422\u0418"); // 'Вийти'
    public static final By REGISTRATION_LINK = By.xpath("//button[@id='register-button']");
    public static final By FIRST_NAME_FIELD = By.name("first_name");
    public static final By LAST_NAME_FIELD = By.name("last_name");
    public static final By PASSWORD_REPEAT_FIELD = By.name("password_second");
    public static final By REGISTRATION_SUBMIT_BUTTON = By.className("b-form__button");
    public static final By LOGGED_IN__USER_NAME = By.xpath("//i[contains(@class, 'fa-user')]/..");
    public static final By USER_DROPDOWN = By.cssSelector(".dropdown-toggle.b-menu__button.ng-binding");
    public static final By NEWS_ICON = By.cssSelector(".navbar-nav>li");
    public static final By CHANGE_ITEM = By.linkText("ЗМІНИТИ ПАРОЛЬ");
    public static final By OLD_PASSWORD = By.cssSelector("#old_password");
    public static final By NEW_PASSWORD = By.cssSelector("#new_password");
    public static final By NEW_PASSWORD_SECOND = By.cssSelector("#new_password_second");
    public static final By CHANGE_BUTTON = By.cssSelector(".b-form__button");
    public static final By CLOSE = By.cssSelector(".close");


    private WebDriver driver;

    public AnyPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    /**
     * @return first text in resource menu.
     */
    @Override
    public String getFirstResourceTitleFromMenu() {
        driver.findElement(OPEN_FIRST_RESOURCE_MENU).click();  // Why? We can just return text and dont use this 'click'
        return driver.findElement(FIRST_RESOURCE_IN_MENU).getText();
    }

    /**
     * @return title of the first text from resource menu.
     */
    @Override
    public String getFirstResourceTitleFromOpenedResource() {
        driver.findElement(FIRST_RESOURCE_IN_MENU).click();
        return driver.findElement(TITLE_EXPRESSION).getText();
    }

    /**
     * Sign in on the website.
     *
     * @param email    for login
     * @param password for login.
     */
    @Override
    public void logIn(String email, String password) {
        driver.findElement(LOGIN_LINK).click();     //Может уже зашел в систему? нет этой проверки
        driver.findElement(EMAIL_FIELD).clear();
        driver.findElement(EMAIL_FIELD).sendKeys(email);
        driver.findElement(PASSWORD_FIELD).clear();
        driver.findElement(PASSWORD_FIELD).sendKeys(password);
        driver.findElement(LOGIN_BUTTON).click();
    }

    /**
     * Log out from the website.
     */
    @Override
    public void logOut() {
        explicitWaitForElement(3, USER_PICTOGRAM);   //Может и не залогинен?
        driver.findElement(USER_PICTOGRAM).click();
        driver.findElement(LOGOUT_LINK).click();
    }

    /**
     * This method registers you on website by your parameters (name, last name, email, pass)
     *
     * @param first_name The name user; may not be null.
     * @param last_name  The last name user; may not be null.
     * @param email      The email user; may not be null; your email should uses '@' and be in lower case
     * @param password   The password user; may not be null; pls sent minimum 6 unique characters
     */
    @Override
    public void register(String first_name, String last_name, String email, String password) {
        driver.findElement(LOGIN_LINK).click();              //Проверки на уникальности имейла и т д
        driver.findElement(REGISTRATION_LINK).click();
        driver.findElement(FIRST_NAME_FIELD).clear();
        driver.findElement(FIRST_NAME_FIELD).sendKeys(first_name);
        driver.findElement(LAST_NAME_FIELD).clear();
        driver.findElement(LAST_NAME_FIELD).sendKeys(last_name);
        driver.findElements(EMAIL_FIELD).get(1).sendKeys(email);
        driver.findElements(PASSWORD_FIELD).get(1).sendKeys(password);
        driver.findElement(PASSWORD_REPEAT_FIELD).clear();
        driver.findElement(PASSWORD_REPEAT_FIELD).sendKeys(password);
        driver.findElement(REGISTRATION_SUBMIT_BUTTON).click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.findElement(CLOSE_CROSS_IN_ALERT_WINDOW).click();
    }

    /**
     * @return name from logged user on the website.
     */
    @Override
    public String getLoggedInUserName() {
        return driver.findElement(LOGGED_IN__USER_NAME).getText();
    }

    /**
     * This method adds problem to the website using forms and field for describe problem.
     *
     * @param latitude           is coordinate X where problem on the map.
     * @param longitude          is coordinate Y where problem on the map.
     * @param problemName        is title our problem.
     * @param problemType        is type of the problem like landfill, illegal construction, poaching etc
     * @param problemDescription is description your problem. Try to describe your problem in detail
     * @param problemPropose     How would you have solved this problem?
     * @param imageUrls          are urls to you pictures/photos this problem.
     * @param imageComments      are comments to your photos. Size list should be same with imageUrls
     */
    @Override
    public void addProblemToVisibleCenter(double latitude, double longitude,
                                          String problemName, String problemType,
                                          String problemDescription, String problemPropose,
                                          List<String> imageUrls, List<String> imageComments) {

        driver.manage().window().maximize();
        driver.findElement(ADD_PROBLEM_BUTTON).click();
        setVisibleView(latitude, longitude, 18);
        clickAtVisibleMapCenter(0);
        driver.findElement(ADD_PROBLEM_NEXT_TAB2_BUTTON).click();
        driver.findElement(PROBLEM_NAME_TEXT_BOX).sendKeys(problemName);

        // Finding problem type from drop-down menu. If problemType equals on something click on it if not choose "Інші проблеми"
        clickOnProblemType(problemType);

        driver.findElement(PROBLEM_DESCRIPTION_FIELD).sendKeys(problemDescription);
        driver.findElement(PROBLEM_PROPOSE_FIELD).sendKeys(problemPropose);
        driver.findElement(BODY).sendKeys(Keys.chord(Keys.CONTROL, Keys.HOME));
        driver.findElement(ADD_PROBLEM_TAB3_IMAGE).click();

        for (String imageUrl : imageUrls) {
            if (imageUrl == null || imageUrl.isEmpty()) {
                continue;
            }
            Thread thread = new ClipboardUploadThread(imageUrl);
            thread.start();
            driver.findElement(DROP_ZONE).click();
            try {
                Thread.sleep(4000);              // Зачем? лучше сделать в самом потоке тогда уж... хотя.. нужно спросить
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            thread.interrupt(); //Тут точно нужно новый поток создавать?
        }

        //add comments to our images
        List<WebElement> commentElements = driver.findElements(IMAGE_COMMENT_TEXT_BOX);
        for (int i = 0; i < commentElements.size(); i++) {
            commentElements.get(i).sendKeys(imageComments.get(i));
        }

        closeAlertIfPresent();
    }

    /**
     * Finding problem type from drop-down menu. If problemType equals on something click on it if not choose "Інші проблеми"
     *
     * @param problemType is type of the problem like landfill, illegal construction, poaching etc
     */
    private void clickOnProblemType(String problemType) {
        List<WebElement> elements = driver.findElements(PROBLEM_TYPE_DROP_DOWN_LIST);
        for (WebElement element : elements) {
            if (problemType.equals(element.getText())) {
                element.click();
                //if found element exit from this method
                return;
            }
        }
        //if not found by loop we choose last element = "Інші проблеми"
        elements.get(elements.size() - 1).click();
    }

    /**
     * @return user name if you logged on the website
     */
    public String checkUsernameInRightCorner() {
        explicitWaitForElement(5, USER_DROPDOWN); // а если не залогинен? может возращать что то тоже?
        return driver.findElement(USER_DROPDOWN).getText();
    }

    /**
     * @return true if user sees news menu
     */
    public boolean checkNewsAvailability() {
        List<WebElement> resources1 = driver.findElements(NEWS_ICON);
        for (WebElement listElement : resources1) {
            String searchText = listElement.getText();
            if (searchText.equals("НОВИНИ")) {                      //а где этот блок то? чет я не пойму))
                return true;
            }
        }
        return false;
    }

    /**
     * This method collects information from driver manage, put it in hashmap.
     *
     * @return HashMap with cookie data.
     * @throws UnsupportedEncodingException in URLDecoder.decode(...) if character encoding needs to be consulted,
     *                                      but named character encoding is not supported.
     */
    public Map getCookiesName() throws UnsupportedEncodingException {
        Map<String, String> result = new HashMap<String, String>();
        result.put("Name", driver.manage().getCookieNamed("userName").getValue());
        result.put("Surname", driver.manage().getCookieNamed("userSurname").getValue());
        result.put("Email", URLDecoder.decode(driver.manage().getCookieNamed("userEmail").getValue(), "UTF-8"));
        result.put("UserRole", driver.manage().getCookieNamed("userRole").getValue());
        return result;
    }

    /**
     * Exact time waiting for button. Public method for tests.
     *
     * @param time    It's time how much need wait (int)
     * @param byValue Element which need wait
     */
    public void explicitWaitForButton(int time, By byValue) {
        WebDriverWait wait1 = new WebDriverWait(driver, time);
        wait1.until(ExpectedConditions.elementToBeClickable(byValue));
    }

    /**
     * Exact time waiting for button. Private method for this class.
     *
     * @param time    It's time how much need wait (int)
     * @param byValue Element which need wait
     */
    public void explicitWaitForElement(int time, By byValue) {
        WebDriverWait wait1 = new WebDriverWait(driver, time);
        wait1.until(ExpectedConditions.visibilityOfElementLocated(byValue));
    }

    /**
     * This method change old password on new password.
     *
     * @param currentPassword It's password which now on your account.
     * @param newPassword     It's new password you want to change
     */
    public void changePassword(String currentPassword, String newPassword) {
        driver.findElement(USER_DROPDOWN).click();                        //проверить залогин ли юзер на сайте?
        driver.findElement(CHANGE_ITEM).click();
        driver.findElement(OLD_PASSWORD).sendKeys(currentPassword);
        driver.findElement(NEW_PASSWORD).sendKeys(newPassword);
        driver.findElement(NEW_PASSWORD_SECOND).sendKeys(newPassword);
        driver.findElement(CHANGE_BUTTON).click();
        driver.findElement(CLOSE).click();
    }

    /*--------------------Selenium 2.0 Web-driver and Sikuli-api integration------------------------------------------*/

    /**
     * This method adds problem to the website using forms and field for describe problem.
     *
     * @param latitude           is coordinate X where problem on the map.
     * @param longitude          is coordinate Y where problem on the map.
     * @param problemName        is title our problem.
     * @param problemType        is type of the problem like landfill, illegal construction, poaching etc
     * @param problemDescription is description your problem. Try to describe your problem in detail
     * @param problemPropose     How would you have solved this problem?
     * @param imagesPath         are urls to you pictures/photos this problem.
     * @param imagesComment      are comments to your photos. Size list should be same with imageUrls
     */
    public void addProblemSymbiosis(double latitude, double longitude, int zoom,
                                    String problemName, String problemType,
                                    String problemDescription, String problemPropose,
                                    List<String> imagesPath, List<String> imagesComment) throws IOException {

        driver.manage().window().maximize();
        driver.findElement(ADD_PROBLEM_BUTTON).click();
        setVisibleView(latitude, longitude, zoom);
        clickAtVisibleMapCenter(0);
        driver.findElement(ADD_PROBLEM_NEXT_TAB2_BUTTON).click();
        driver.findElement(PROBLEM_NAME_TEXT_BOX).sendKeys(problemName);

        List<WebElement> elements = driver.findElements(PROBLEM_TYPE_DROP_DOWN_LIST);
        for (WebElement element : elements) {
            if (problemType.equals(element.getText()))
                element.click();
        }

        driver.findElement(PROBLEM_DESCRIPTION_FIELD).sendKeys(problemDescription);
        driver.findElement(PROBLEM_PROPOSE_FIELD).sendKeys(problemPropose);
        driver.findElement(BODY).sendKeys(Keys.chord(Keys.CONTROL, Keys.HOME));
        driver.findElement(ADD_PROBLEM_TAB3_IMAGE).click();

        /*------------------------------------------Sikuli code block-------------------------------------------------*/

        DesktopKeyboard keyboard = new DesktopKeyboard();
        Mouse mouse = new DesktopMouse();
        Canvas canvas = new DesktopCanvas();
        ScreenRegion screenRegion = new DesktopScreenRegion();

        Target triangle = new ImageTarget(new File(".\\resources\\images\\Triangle.jpg"));
        Target dropZone = new ImageTarget(new File(".\\resources\\images\\Drop Zone.jpg"));
        Target cross = new ImageTarget(new File(".\\resources\\images\\Cross.jpg"));

        triangle.setMinScore(0.8);
        dropZone.setMinScore(0.6);
        cross.setMinScore(0.8);

        List<BufferedImage> resizeBufferedImageList = new ArrayList<>();    //list of images with new size
        BufferedImage bufferedImage;
        BufferedImage resizeBufferedImage;
        for (String path : imagesPath) {
            if (!path.isEmpty()) {
                bufferedImage = ImageIO.read(new File(path));
                resizeBufferedImage = resize(bufferedImage, 85, 64);
                resizeBufferedImageList.add(resizeBufferedImage);
            }
        }

        List<Target> uploadImageList = new ArrayList<>();                   //list of targets with new image size
        for (BufferedImage image : resizeBufferedImageList) {
            uploadImageList.add(new ImageTarget(image));
        }

        keyboard.keyDown(KeyEvent.VK_WINDOWS);
        keyboard.keyDown(KeyEvent.VK_E);
        keyboard.keyUp(KeyEvent.VK_E);
        keyboard.keyUp(KeyEvent.VK_WINDOWS);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        keyboard.keyDown(KeyEvent.VK_WINDOWS);
        keyboard.keyDown(KeyEvent.VK_UP);
        keyboard.keyUp(KeyEvent.VK_UP);
        keyboard.keyUp(KeyEvent.VK_WINDOWS);
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        keyboard.keyDown(KeyEvent.VK_WINDOWS);
        keyboard.keyDown(KeyEvent.VK_LEFT);
        keyboard.keyUp(KeyEvent.VK_LEFT);
        keyboard.keyUp(KeyEvent.VK_WINDOWS);
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<ScreenRegion> screenRegionList = screenRegion.findAll(triangle);
        for (ScreenRegion screen : screenRegionList) {
            canvas.addBox(screen);
        }
        canvas.display(1);
        canvas.clear();

        int x = 0;
        int y = 0;
        int width = 0;
        int height = 0;
        for (ScreenRegion screen : screenRegionList) {
            if (screen.getCenter().getX() > x) {
                x = screen.getCenter().getX();
                y = screen.getCenter().getY();
                width = screen.getBounds().width;
                height = screen.getBounds().height;
            }
        }
        screenRegion = new DesktopScreenRegion(x, y, width, height);

        canvas.addCircle(screenRegion.getCenter());
        canvas.addLabel(screenRegion.getCenter(), "We get this!");
        canvas.display(1);
        canvas.clear();

        mouse.click(screenRegion.getCenter().getRelativeScreenLocation(100, 0));
        keyboard.paste("C:\\Users\\Public\\Pictures\\Sample Pictures");
        keyboard.keyDown(KeyEvent.VK_ENTER);
        keyboard.keyUp(KeyEvent.VK_ENTER);

        ScreenRegion dropZoneScreenRegion = new DesktopScreenRegion().wait(dropZone, 10);
        canvas.addBox(dropZoneScreenRegion);
        canvas.addLabel(dropZoneScreenRegion, "We found Drop Zone!");
        canvas.display(1);
        canvas.clear();

        for (Target target : uploadImageList) {
            screenRegion = new DesktopScreenRegion().wait(target, 10);
            mouse.drag(screenRegion.getCenter());
            mouse.drop(dropZoneScreenRegion.getCenter());
        }

        ScreenRegion crossScreenRegion = new DesktopScreenRegion().wait(cross, 10);
        mouse.click(crossScreenRegion.getCenter());

        /*------------------------------------------------------------------------------------------------------------*/

        List<WebElement> commentElements = driver.findElements(IMAGE_COMMENT_TEXT_BOX);
        int i = 0;
        for (WebElement element : commentElements) {
            element.sendKeys(imagesComment.get(i));
            i++;
        }
        closeAlertIfPresent();

    }

    /**
     * This method click 'close' button if alert presents
     */
    private void closeAlertIfPresent() {
        driver.findElement(ADD_PROBLEM_SUBMIT_BUTTON).click();
    /*    driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        WebElement login = (new WebDriverWait(driver, 1))
                .until(ExpectedConditions.presenceOfElementLocated(LOGIN_LINK));
        WebElement alert = (new WebDriverWait(driver, 1))
                .until(ExpectedConditions.presenceOfElementLocated(ALERT_WINDOW));
        alert.findElement(CLOSE_CROSS_IN_ALERT_WINDOW).click();*/
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
    }

    /**
     * This is a private method helps to resize images. Usually used when the picture is very large
     * and it is necessary to reduce
     *
     * @param img    Image which need to change; may not be null
     * @param width  New width of your image; must be greater than zero
     * @param height New height of your image; must be greater than zero
     * @return new image with new size (width, height).
     */
    private BufferedImage resize(BufferedImage img, int width, int height) {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage dImg = new BufferedImage(width, height, img.getType());
        Graphics2D g = dImg.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(img, 0, 0, width, height, 0, 0, w, h, null);
        g.dispose();
        return dImg;
    }
}