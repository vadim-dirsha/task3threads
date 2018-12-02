package ru.vadimdirsha.java;

import org.apache.log4j.Logger;
import ru.vadimdirsha.java.model.organization.Organization;
import ru.vadimdirsha.java.model.organization.operators.Operator;
import ru.vadimdirsha.java.model.people.Person;
import ru.vadimdirsha.java.model.people.PersonThread;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Random;

public class App {
    private static Logger logger = Logger.getLogger(App.class);

    public static void main(String[] args) {
        //ide doesn't copy log4j.properties to classes folder (classpath)
        //BasicConfigurator.configure();

        ArrayList<Operator> operators = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            operators.add(new Operator(i, "Operator" + i));
        }
        ArrayList<Person> people = new ArrayList<>();
        Random random = new Random(new Date().getTime());
        for (int i = 0; i < 25; i++) {
            people.add(new Person("Man" + i, 1000 + i * 100, 200000, 10000, false));
        }

        Organization organization = Organization.getInstance();
        organization.setUp();
        LinkedList<Thread> threads = new LinkedList<>();
        for (int i = 0; i < 1; i++) {
            Thread thread = new PersonThread(new Person("CrazyMan" + i, 2000 + i * 10, 5000, false));
            threads.add(thread);
        }
        people.forEach(e -> threads.add(new PersonThread(e)));
        operators.forEach(e -> organization.getOperatorsRoom().addFreeOperator(e));
        organization.startWork();
        threads.forEach(Thread::start);
        logger.info("main tread");

    }
}
