package jw.demo.util;

import java.util.Properties;


@SuppressWarnings({"LombokGetterMayBeUsed", "LombokSetterMayBeUsed"})
public class TestContext {


    private static ThreadLocal<ScenarioCtx> scenarioCtxThreadLocal = new ThreadLocal<>();

    private static Properties properties;

    private static String global;

    static {
        scenarioCtxThreadLocal.set(new ScenarioCtx());
        global = "test";
    }

    public static ScenarioCtx getScenarioCtx() {
        return scenarioCtxThreadLocal.get();
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

    public static void setScenarioCtx(ScenarioCtx s) {
        System.out.println("set ctx");
        scenarioCtxThreadLocal.set(s);
    }

}