package jw.demo.pages;

import jw.demo.util.Util;
import lombok.Getter;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

@Getter
public final class LoginPage extends BasePage {

    private static final Logger LOG = Util.loggerForClass();

    @FindBy
    private final By emailTextField = By.id("input-14");
    @FindBy
    private final By passwordTextField = By.id("input-15");
    @FindBy
    private final By submitBtn = By.className("v-btn__content");
    @FindBy
    private final By continueBtnAfterLogin = By.xpath("//span[text()=' Continue ']/parent::button[@type='submit']");

}
