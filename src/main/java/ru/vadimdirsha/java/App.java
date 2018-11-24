package ru.vadimdirsha.java;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 * Hello world!
 */
public class App {
    private static Logger logger = Logger.getLogger(App.class);

    public static void main(String[] args) {
        BasicConfigurator.configure();
    }
}
