package jw.demo.util.driver;

import jw.demo.util.Log;
import jw.demo.util.Util;
import lombok.SneakyThrows;
import org.apache.logging.log4j.Logger;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;
import org.testng.internal.BaseTestMethod;

import java.lang.reflect.Field;

@SuppressWarnings("DataFlowIssue")
public class DriverListener implements IInvokedMethodListener {

    private static final Logger LOG;

    static {
        LOG = Util.assignLoggerByClass();
    }

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        LOG.error("BEGINNING: DriverListener.beforeInvocation");
        if (method.isTestMethod()) {
            Driver.setDriver();
        } else {
            LOG.error("Provided method is NOT a TestNG testMethod!!!");
        }
        LOG.error("END: DriverListener.beforeInvocation");
    }


    @Override @SneakyThrows
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        LOG.error("BEGINNING: DriverListener.afterInvocation");
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
                LOG.error("Renaming test method name from: '" + bm.getMethodName() + "' to: '" + newTestName + "'");
                f.set(bm, newTestName);
            } catch (Exception e) {
                throw new Exception(Log.exceptionErrorMsg("DriverListener: afterInvocation exception", e));
            } finally {
                Driver.quitDriver();
            }
        }
        LOG.error("END: DriverListener.afterInvocation");
    }
}