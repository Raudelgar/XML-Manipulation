package com.company;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Path {

    private FileInputStream fis = null;
    private Properties properties = new Properties();

    public Path () {


    }

    public void init() {
        try {
            fis = new FileInputStream("..\\XML_Manipulation\\src\\main\\resources\\path.properties");
            properties.load(fis);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getPathValue (String pathKey) {
        init();
        return properties.get(pathKey).toString();
    }
}
