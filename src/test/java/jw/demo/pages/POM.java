package jw.demo.pages;

import jw.demo.util.Util;
import org.apache.logging.log4j.Logger;

/*
    this class manages the Page classes. When a WebElement from a Page class is needed it should be accessed
    directly from a POM instance. When accessed this way the POM instance will return an Object of the Pages
    class depending on the getter method used. The getter will check if an existing instance already exists,
    if not then POM will create an object. All Pages inherit the BasePage and will call super() when the obj
    is created. The BasePage constructor will call initElements() and initialize

 */

public class POM {

    private static final Logger LOG;

    static {
        LOG = Util.assignLoggerByClass();
    }

    private LoginPage loginPage;
    private Form1099Page form1099Page;
    private MyUploadsPage myUploadsPage;

    public LoginPage getLoginPage() {
        if (loginPage == null) {
            loginPage = new LoginPage();
        }
        return loginPage;
    }

    public Form1099Page getForm1099Page() {
        if (form1099Page == null) {
            form1099Page = new Form1099Page();
        }
        return form1099Page;
    }

    public MyUploadsPage getMyUploadsPage() {
        if (myUploadsPage == null) {
            myUploadsPage = new MyUploadsPage();
        }
        return myUploadsPage;
    }
}
