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
        /*
        TODO предварительно, клиент и коллцентр потоки которые управляют звонками, оператор и коллцентор части организации,
        возможно нужна фабрика для килентов которая задает им парметры типа предварительной длинны звонка и проч
        коллцентр управляет очередью звонков, организация управялет операторами и коллценгтром
        reorganization(reorgProt) class reorganizationProtocol.BANKROT kappa
        */
        //Condition.await/signal решит проблему

        ArrayList<Operator> operators = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            operators.add(new Operator(i, "Operator" + i));
        }
        ArrayList<Person> people = new ArrayList<>();
        Random random = new Random(new Date().getTime());
        for (int i = 0; i < 25; i++) {
            people.add(new Person("Man" + i, random.nextInt(2000), random.nextInt(30000), random.nextInt(12000), true));
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
        //TODO  проверка на то что бы звонок был активный нету
    }
}
