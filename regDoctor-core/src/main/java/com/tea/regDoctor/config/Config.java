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
import java.util.Optional;
import java.util.Properties;

public class Config {
    private static volatile Config INSTANCE;

    private Logger logger = LoggerFactory.getLogger(getClass());

    private static Path configFilePath = Paths.get(System.getenv("REGDOCTOR_HOME"), "config", "user.properties");

    private Properties prop = new Properties();

    private Config() {
        try {
            prop.load(Files.newInputStream(configFilePath));
        } catch (IOException e) {
            logger.error("config error!Please Contact Administrator.", e);
            throw new RuntimeException("config error! config row not match! Please Contact Administrator.");
        }
        if (prop.size() != Key.values().length) {
            logger.error("config error! config row not match! Please Contact Administrator.");
            throw new RuntimeException("config error! config row not match! Please Contact Administrator.");
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

        /**
         * @return Key (might be a null value Optional)
         */
        public static Optional<Key> fromValue(String value) {
            Key k = null;
            for (Key key : Key.values()) {
                if (key.getValue().equals(value)) {
                    k = key;
                }
            }
            return Optional.ofNullable(k);
        }

        public String getValue() {
            return value;
        }


    }


}
