package jw.demo.util.driver;

import jw.demo.util.Util;
import org.apache.logging.log4j.Logger;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;
import org.testng.internal.BaseTestMethod;

import java.lang.reflect.Field;

public class DriverListener implements IInvokedMethodListener {

    private static final Logger LOG = Util.loggerForClass();

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        LOG.debug("BEGINNING: DriverListener.beforeInvocation");
        if (method.isTestMethod()) {
            Driver.setDriver();
        } else {
            LOG.warn("Provided method is NOT a TestNG testMethod!!!");
        }
        LOG.debug("END: DriverListener.beforeInvocation");
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        LOG.debug("BEGINNING: DriverListener.afterInvocation");
        if (method.isTestMethod()) {
            try {
                String browser = Driver.getDriverBrowser();
                // change the name of the test method that will appear in the report to one that will contain
                // also browser name, version and OS.
                // very handy when analysing results.
                BaseTestMethod bm = (BaseTestMethod) testResult.getMethod();
                Field f = bm.getClass().getSuperclass().getDeclaredField("m_methodName");
                f.setAccessible(true);
                String newTestName = testResult.getTestContext().getCurrentXmlTest().getName() + " - " + bm.getMethodName() + " - " + browser;
                LOG.info("Renaming test method name from: '" + bm.getMethodName() + "' to: '" + newTestName + "'");
                f.set(bm, newTestName);
            } catch (Exception ex) {
                LOG.error("afterInvocation exception:\n" + ex.getMessage());
                ex.printStackTrace();
            } finally {
                // close the browser
                Driver.quitDriver();
            }
        }
        LOG.debug("END:DriverListener.afterInvocation");
    }
}