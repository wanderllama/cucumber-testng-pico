package jw.demo.pages;

import jw.demo.util.Util;
import lombok.Getter;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

@Getter
public class MyUploadsPage {

    private static final Logger LOG = Util.loggerForClass();

    @FindBy
    public final By myUploadsHeader = By.xpath("//h1[text()='My uploads']");

}
