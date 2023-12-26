package jw.demo.pages;

import jw.demo.util.ScenarioCtx;
import lombok.Getter;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static jw.demo.util.Util.*;

@Getter
public final class LoginPage extends BasePage {

    private static final Logger LOG = assignLoggerByClass();

    @FindBy
    private final By emailTextField = By.id("input-14");
    @FindBy
    private final By passwordTextField = By.id("input-15");
    @FindBy
    private final By submitBtn = By.className("v-btn__content");
    @FindBy
    private final By continueBtnAfterLogin = By.xpath("//span[text()=' Continue ']/parent::button[@type='submit']");

    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public static void login(POM pom, ScenarioCtx context) {
        getDriver().findElement(pom.getLoginPage().getEmailTextField()).sendKeys(getUsername(context));
        getDriver().findElement(pom.getLoginPage().getPasswordTextField()).sendKeys(getPassword(context));
        getDriver().findElement(pom.getLoginPage().getSubmitBtn()).click();
    }
}
