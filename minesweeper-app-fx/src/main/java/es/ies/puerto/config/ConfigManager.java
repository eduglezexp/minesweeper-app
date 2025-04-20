package es.ies.puerto.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @author eduglezexp
 * @version 1.0.0
 */

public class ConfigManager {

    public static class ConfigProperties {

        static String path;
        private static final Properties properties = new Properties();
        private static String idiomaActual = "es"; 
        
        static {

        }

        /**
         *  Getters and Setters
         **/
        public static String getProperty(String key) {
            return properties.getProperty(key);
        }

        public static void setPath(String rutaPath) {
            File file = new File(rutaPath);
            if (!file.exists() || !file.isFile()) {
                System.out.println("Path: " +file.getAbsolutePath());
            }
            path = rutaPath;
            try {
                FileInputStream input = new FileInputStream(path);
                InputStreamReader isr = new InputStreamReader(input, "UTF-8");
                properties.load(isr);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    
        public static String getIdiomaActual() {
            return idiomaActual;
        }
        
        public static void setIdiomaActual(String idioma) {
            idiomaActual = idioma;
        }
    }
}