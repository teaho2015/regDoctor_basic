/**
 * @author teaho2015@gmail.com
 * since 2017/5/12
 */
package com.tea.regDoctor.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class Config {
    private static volatile Config INSTANCE;

    private Logger logger = LoggerFactory.getLogger(getClass());

    private Path configFilePath = Paths.get(System.getProperty("user.dir"), "user.properties");

    private Properties prop = new Properties();

    private Config() {
        try {
            if (Files.notExists(configFilePath)) {
                Files.createFile(configFilePath.normalize());
                Properties defaultProp = new Properties();
                defaultProp.put(Key.OCR.getValue(), "D:\\Program Files (x86)\\Tesseract-OCR\\tesseract.exe");
                defaultProp.put(Key.USERID.getValue(), "4406000001000271401");
                defaultProp.put(Key.USERPASSWORD.getValue(), "69d5c50df527a4b8cb1cd00c19dec17e");
                defaultProp.put(Key.TEMPDIR.getValue(), Paths.get(System.getProperty("user.dir"), ".TEMP").toString());
                defaultProp.store(Files.newOutputStream(configFilePath), "Init by program!! please do not delete any row, it will cause unexpected error.");
            }
            prop.load(Files.newInputStream(configFilePath));
        } catch (IOException e) {
            logger.error("config error!Please Contact Administrator.", e);
            throw new RuntimeException();
        }
        if (prop.size() != Key.values().length) {
            logger.error("config error! config row not match! Please Contact Administrator.");
            throw new RuntimeException();
        }

    }

    public static Config getInstance() {
        if (INSTANCE == null) {
            synchronized (Config.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Config();
                }
            }
        }
        return INSTANCE;
    }

    public String getProperty(Key key) {
        return prop.getProperty(key.getValue());
    }


    public enum Key {
        OCR("regDoctor.ocr.path"),
        USERID("regDoctor.login.id"),
        USERPASSWORD("regDoctor.login.password"),
        TEMPDIR("regDoctor.path.temp");

        private String value;

        private Key(String key) {
            value = key;
        }

        public String getValue() {
            return value;
        }

        /**
         * @return Key (might be a null value)
         */
        public static Key fromValue(String value) {
            for (Key key : Key.values()) {
                if (key.getValue().equals(value)) {
                    return key;
                }
            }
//            throw new NullPointerException();
            //give user a chance
            return null;
        }


    }




}
