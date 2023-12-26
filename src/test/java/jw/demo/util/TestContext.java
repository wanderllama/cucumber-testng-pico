package jw.demo.util;

import org.apache.logging.log4j.Logger;

import java.util.Properties;


@SuppressWarnings({"LombokGetterMayBeUsed", "LombokSetterMayBeUsed"})
public class TestContext {

    private static final Logger LOG;
    @SuppressWarnings("FieldMayBeFinal")
    private static ThreadLocal<ScenarioCtx> scenarioCtxThreadLocal = new ThreadLocal<>();

    static {
        LOG = Util.assignLoggerByClass();
    }


    private static Properties properties;

    private static String global;

    public static ScenarioCtx getScenarioCtx() {
        return scenarioCtxThreadLocal.get();
    }

    public static void setScenarioCtx(ScenarioCtx s) {
        scenarioCtxThreadLocal.set(s);
        LOG.info("ScenarioCtx set");
    }

    public static Properties getProperties() {
        return properties;
    }

    public static void setProperties(Properties prop) {
        properties = prop;
    }

    public static String getGlobal() {
        return global;
    }

    public static void setGlobal(String prop) {
        global = prop;
    }

}