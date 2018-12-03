package ru.vadimdirsha.java;

import org.testng.annotations.Test;
import ru.vadimdirsha.java.model.organization.Operator;
import ru.vadimdirsha.java.model.organization.Organization;
import ru.vadimdirsha.java.model.people.Person;
import ru.vadimdirsha.java.unit_behavior.PersonThread;

import java.util.ArrayList;
import java.util.LinkedList;

import static org.testng.Assert.*;

/**
 * @author = Vadim Dirsha
 * @date = 24.11.2018
 */
public class AppTest {

    @Test
    public void testMain() {
        ArrayList<Operator> operators = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            operators.add(new Operator(i, "Operator" + i));
        }
        ArrayList<Person> people = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
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
        assertTrue(true);
    }
}