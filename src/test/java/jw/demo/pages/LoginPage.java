package jw.demo.pages;

import jw.demo.util.ScenarioCtx;
import lombok.Getter;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import static jw.demo.util.Util.*;

@Getter
public final class LoginPage extends BasePage {

    private static final Logger LOG;

    static {
        LOG = assignLoggerByClass();
    }

    @FindBy
    final By emailTextField = By.id("input-14");
    @FindBy
    final By passwordTextField = By.id("input-15");
    @FindBy
    final By submitBtn = By.className("v-btn__content");
    @FindBy
    final By continueBtnAfterLogin = By.xpath("//span[text()=' Continue ']/parent::button[@type='submit']");

    public void login(ScenarioCtx context) {
        getDriver().findElement(emailTextField).sendKeys(getUsername(context));
        getDriver().findElement(passwordTextField).sendKeys(getPassword(context));
        getDriver().findElement(submitBtn).click();
    }
}
