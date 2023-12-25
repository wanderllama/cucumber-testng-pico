package jw.demo.pages;

import jw.demo.util.driver.Driver;
import org.openqa.selenium.WebDriver;

public class POM {

    private final WebDriver driver;

    public POM() {
        driver = Driver.getDriver();
    }

    public POM(WebDriver driver) {
        this.driver = driver;
    }

    private LoginPage loginPage;
    private Form1099Page form1099Page;
    private MyUploadsPage myUploadsPage;

    public LoginPage getLoginPage() {
        if (loginPage == null) {
            loginPage = new LoginPage(driver);
        }
        return loginPage;
    }

    public Form1099Page getForm1099Page() {
        if (form1099Page == null) {
            form1099Page = new Form1099Page(driver);
        }
        return form1099Page;
    }

    public MyUploadsPage getMyUploadsPage() {
        if (myUploadsPage == null) {
            myUploadsPage = new MyUploadsPage(driver);
        }
        return myUploadsPage;
    }
}
