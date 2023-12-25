package jw.demo.pages;

public class POM {

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
