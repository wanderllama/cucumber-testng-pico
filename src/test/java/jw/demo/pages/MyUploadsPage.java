package jw.demo.pages;

import jw.demo.util.Util;
import lombok.Getter;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class MyUploadsPage extends BasePage {

    private static final Logger LOG = Util.loggerForClass();

    @FindBy
    public final By myUploadsHeader = By.xpath("//h1[text()='My uploads']");


    public MyUploadsPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

}
