package jw.demo.pages;

import jw.demo.constantsAndEnums.WaitTime;
import jw.demo.util.Util;
import jw.demo.util.driver.Driver;
import lombok.Getter;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

@Getter
public class BasePage {

    static final POM pom;
    private static final Logger LOG;

    static {
        LOG = Util.assignLoggerByClass();
        pom = new POM();
    }

    private final By progressBar = By.xpath("//div[@role='progressbar']");

    public BasePage() {
        PageFactory.initElements(getDriver(), this);
    }

    public WebDriver getDriver() {
        return Driver.getDriver();
    }

    public void tryAgainAndWaitBeforeException(WaitTime waitFor) {
        PageFactory.initElements(new AjaxElementLocatorFactory(getDriver(), waitFor.amountOfSeconds()), this);
    }
}
