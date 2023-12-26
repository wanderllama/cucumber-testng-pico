/*
 * Copyright (c) 2022 Anh Tester
 * Automation Framework Selenium
 */

package jw.demo.util;

import lombok.Getter;
import lombok.SneakyThrows;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigProperties {

    private static Logger LOG;

    static {
        LOG = Util.assignLoggerByClass();
    }
    @Getter
    private static Properties properties;

    @SneakyThrows
    public static Properties setupProperties() {
        try {
            properties = new Properties();
//            file = new FileInputStream("src/main/resources/config/config.properties");
            // local file with credentials below 
            FileInputStream file = new FileInputStream("src/test/resources/configuration.properties");
            properties.load(file);
            file.close();
        } catch (IOException e) {
            throw new IOException(Log.exceptionErrorMsg("Occurred While Reading Configuration File", e));
        }
        LOG.info("Configuration.properties file loaded to properties obj");
        return properties;
    }

    public static String getData(String key) {
        System.out.println(key);
        return properties.getProperty(key);
    }

}
