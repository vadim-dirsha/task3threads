package ru.vadimdirsha.java;

import org.apache.log4j.Logger;
import ru.vadimdirsha.java.model.organization.Organization;
import ru.vadimdirsha.java.model.organization.Operator;
import ru.vadimdirsha.java.model.people.Person;
import ru.vadimdirsha.java.unit_behavior.PersonThread;

import java.util.ArrayList;
import java.util.LinkedList;

public class App {
    private static Logger logger = Logger.getLogger(App.class);

    public static void main(String[] args) {
        //ide doesn't copy log4j.properties to classes folder (classpath)
        //BasicConfigurator.configure();

    }
}
