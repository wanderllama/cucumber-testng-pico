package jw.demo.util;

import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class ScenarioCtx {

    private static final Logger LOG = Util.loggerForClass();
    private ScenarioCtx scenarioCtx;

    private Map<String, Object> contextMap = new HashMap<>();


    private ScenarioCtx getInstance() throws Throwable {
        if (scenarioCtx == null) {
            scenarioCtx = new ScenarioCtx();
        }
        return scenarioCtx;
    }

    public void setProperty(String key, Object value) {
        contextMap.put(key, value);
    }

    public void setProperty(String key, String value) {
        contextMap.put(key, value);
    }

    public void setProperty(String key, int value) {
        contextMap.put(key, value);
    }

    public Object getProperty(String key) {
        return contextMap.get(key);
    }

}
