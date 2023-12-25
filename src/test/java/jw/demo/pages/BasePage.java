package jw.demo.pages;

import jw.demo.util.Util;
import lombok.Getter;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

@Getter
public class BasePage {

    private final WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    private static final Logger LOG = Util.loggerForClass();

    private final By progressBar = By.xpath("//div[@role='progressbar']");
}
