package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class GetPropFromFile {

    public static String getProperty(String propName) {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("src/test/resources/credentials.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties.getProperty(propName);
    }
}
