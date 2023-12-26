package jw.demo.constantsAndEnums;

public enum BrowserType {
    FIREFOX("firefox"),
    CHROME("chrome"),
    EDGE("edge");

    @SuppressWarnings({"NonFinalFieldInEnum", "FieldMayBeFinal"})
    private String browser;

    BrowserType(String browser) {
        if (browser.equals("chrome-linux")) {
            browser = "chrome";
        }
        this.browser = browser;
    }

    public String getBrowser(BrowserType browser) {
        return this.browser;
    }
}
