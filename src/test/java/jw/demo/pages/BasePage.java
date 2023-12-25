package jw.demo.pages;

import jw.demo.util.Util;
import lombok.Getter;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

@Getter
public class BasePage {

    private static final Logger LOG = Util.loggerForClass();
    private final By progressBar = By.xpath("//div[@role='progressbar']");
    POM pom = new POM();

}
