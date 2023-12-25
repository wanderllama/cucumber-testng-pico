package jw.demo.util;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class TestContext {

    private static ThreadLocal<ScenarioCtx> scenarioCtxThreadLocal = new ThreadLocal<>();

    @Setter
    @Getter
    private static String global;

    static {
        scenarioCtxThreadLocal.set(new ScenarioCtx());
        global = "test";
    }

    public static ScenarioCtx getScenarioCtx() {
        return scenarioCtxThreadLocal.get();
    }

    public static void setScenarioCtx(ScenarioCtx s) {
        System.out.println("set ctx");
        scenarioCtxThreadLocal.set(s);
    }

}