package jw.demo.util;


import jw.demo.pages.POM;
import jw.demo.util.driver.Driver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.NoSuchElementException;

import static jw.demo.enums.WaitTime.REGULAR;

public class Util extends Driver {

    POM pom = new POM();

    // create WebDriverWait object
    public static WebDriverWait getWait() {
        return getWait(REGULAR.waitTime());
    }

    public static WebDriverWait getWait(Duration d) {
        return new WebDriverWait(Driver.getDriver(), d);
    }

    public static void waitTillInvisible(WebElement element, Duration d) {
        try {
            Util.getWait(d).until(ExpectedConditions.invisibilityOf(element));
        } catch (TimeoutException e) {
            System.out.printf("%s failed invisibilityOf wait after waiting %s%n", element.toString(), d);
        }
    }

    public static void waitTillInvisible(WebElement element) {
        try {
            Util.getWait(REGULAR.waitTime()).until(ExpectedConditions.invisibilityOf(element));
        } catch (TimeoutException e) {
            System.out.printf("%s failed invisibilityOf wait after waiting %s%n", element.toString(), REGULAR.getSeconds());
        }
    }

    public static WebElement waitTillVisible(By element) {
        WebElement webElement = null;
        try {
            webElement = Util.getWait(REGULAR.waitTime()).until(ExpectedConditions.visibilityOf(Driver.getDriver().findElement(element)));
        } catch (TimeoutException e) {
            System.out.printf("%s failed visibilityOf wait after waiting %s%n", element.toString(), REGULAR.getSeconds());
        }
        return webElement;
    }

    public static WebElement waitTillVisible(By element, Duration d) {
        WebElement webElement = null;
        try {
            webElement = Driver.getDriver().findElement(element);
            Util.getWait(d).until(ExpectedConditions.visibilityOf(webElement));
        } catch (TimeoutException e) {
            System.out.printf("%s failed visibilityOf wait after waiting %s%n", element.toString(), d.getSeconds());
        } catch (NoSuchElementException e) {

        }
        return webElement;
    }

    public static void jsClick(WebElement element) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
            js.executeScript("arguments[0].click();", element);
        } catch (Exception e) {
            System.out.printf("JavascriptExecutor error while clicking locator %s%n", element.toString());
        }
    }

    public static Logger loggerForClass() {
        String callerName = Thread.currentThread().getStackTrace()[2].getClassName();
        Class<?> caller = null;
        try {
            caller = Class.forName(callerName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return caller != null ? LogManager.getLogger(caller.getSimpleName()) : null;
    }

    public static void login(POM pom) {
        getDriver().findElement(pom.getLoginPage().getEmailTextField()).sendKeys(getUsername());
        getDriver().findElement(pom.getLoginPage().getPasswordTextField()).sendKeys(getPassword());
        getDriver().findElement(pom.getLoginPage().getSubmitBtn()).click();
    }

    public static String getUsername() {
        return ConfigProperties.getData("username");
    }

    public static String getPassword() {
        return ConfigProperties.getData("password");
    }


//
//
//    // create Actions object
//    public Actions createActions() {
//        return new Actions(getDriver());
//    }
//
//    // create select object
//    public Select createSelect(String xpath) {
//        return new Select(getDriver().findElement(By.xpath(xpath)));
//    }
//
//    // Assert methods
//    // title verification
//    public void titleVerification(String expectedTitle) {
//        Assert.assertEquals(getDriver().getTitle(), expectedTitle);
//    }
//
//    // title verification contains
//    public void titleVerificationContains(String expectedTitle) {
//        Assert.assertTrue(getDriver().getTitle().contains(expectedTitle));
//    }
//
//    // url verification
//    public void urlVerification(String url) {
//        Assert.assertEquals(getDriver().getCurrentUrl(), url);
//    }
//
//    // url verification contains
//    public void urlVerificationContains(String url) {
//        Assert.assertTrue(getDriver().getCurrentUrl().contains(url));
//    }
//
//    // text verification
//    public void textVerification(String xpath, String text) {
//        Assert.assertEquals(Driver.getDriver().findElement(By.xpath(xpath)).getText(), text);
//    }
//
//    // text verification contains
//    public void textVerificationContains(String xpath, String text) {
//        Assert.assertTrue(getDriver().findElement(By.xpath(xpath)).getText().contains(text));
//    }
//
//    // is displayed verification
//    public void isDisplayed(String xpath) {
//        Assert.assertTrue(getDriver().findElement(By.xpath(xpath)).isDisplayed());
//    }
//
//    // is selected verification
//    public void isSelected(String xpath) {
//        Assert.assertTrue(getDriver().findElement(By.xpath(xpath)).isSelected());
//    }
//
//    // is enabled verification
//    public void isEnabled(String xpath) {
//        Assert.assertTrue(getDriver().findElement(By.xpath(xpath)).isEnabled());
//    }
//
//    // WebDriverWait methods
//    // clickable
//    public void isClickable(String xpath) {
//        if (wait == null) {
//            new Util();
//        }
//        wait.until(ExpectedConditions.elementToBeClickable(getDriver().findElement(By.xpath(xpath))));
//    }
//
    // visible
//    public void waitVisible(String xpath) {
//        if (wait == null) {
//            createWait();
//        }
//        wait.until(ExpectedConditions.visibilityOf(getDriver().findElement(By.xpath(xpath))));
//    }
//
//    // select
//    public void waitSelect(String xpath) {
//        if (wait == null) {
//            createWait();
//        }
//        wait.until(ExpectedConditions.elementToBeSelected(getDriver().findElement(By.xpath(xpath))));
//    }
//
//    // alert
//    public void waitAlert() {
//        if (wait == null) {
//            createWait();
//        }
//        wait.until(ExpectedConditions.alertIsPresent());
//    }
//
//    // url
//    public void waitUrl(String url) {
//        if (wait == null) {
//            createWait();
//        }
//        wait.until(ExpectedConditions.urlMatches(url));
//    }
//
//    // title is
//    public void waitTitleIs(String title) {
//        if (wait == null) {
//            createWait();
//        }
//        wait.until(ExpectedConditions.titleIs(title));
//    }
//
//    // title contains
//    public void waitTitleContains(String title) {
//        if (wait == null) {
//            createWait();
//        }
//        wait.until(ExpectedConditions.titleContains(title));
//    }
//
//
//    // list of texts from list of WebElements
//    public List<String> getTexts(List<WebElement> elements) {
//        List<String> texts = new ArrayList<>();
//        for (WebElement element : elements) {
//            texts.add(element.getText());
//        }
//        return texts;
//    }

}
