package utils;

import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public class ConfigReader {

    private static final Properties properties = new Properties();

    static {
        try {
            InputStream input =
                    ConfigReader.class
                            .getClassLoader()
                            .getResourceAsStream("config.properties");

            if (input == null) {
                throw new RuntimeException("Config file not found!");
            }

            properties.load(input);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load config file.");
        }
    }

    public static String getProperty(String key) {
        if (Objects.equals(key, "")) return "";
        return properties.getProperty(key);
    }
}