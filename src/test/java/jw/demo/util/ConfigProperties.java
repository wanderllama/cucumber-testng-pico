/*
 * Copyright (c) 2022 Anh Tester
 * Automation Framework Selenium
 */

package jw.demo.util;

import lombok.Getter;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigProperties {

    @Getter
    private static Properties properties;
    private static FileInputStream file;

    public static void setupProperties() {
        try {
            properties = new Properties();
//            file = new FileInputStream("src/main/resources/config/config.properties");
            // local file with credentials below 
            file = new FileInputStream("src/test/resources/configuration.properties");
            properties.load(file);
            file.close();
        } catch (IOException e) {
            System.out.println("Error Occurred While Reading Configuration File");
            e.printStackTrace();
        }
    }

    public static String getData(String key) {
        System.out.println(key);
        return properties.getProperty(key);
    }

}
