package ru.vadimdirsha.java;

import org.testng.annotations.Test;
import ru.vadimdirsha.java.model.organization.Operator;
import ru.vadimdirsha.java.model.organization.Organization;
import ru.vadimdirsha.java.model.people.Person;
import ru.vadimdirsha.java.unit_behavior.Manager;
import ru.vadimdirsha.java.unit_behavior.PersonThread;

import java.util.ArrayList;
import java.util.LinkedList;

import static java.lang.Thread.sleep;
import static org.testng.Assert.assertEquals;

/**
 * @author = Vadim Dirsha
 * @date = 24.11.2018
 */
public class OnePersonAndOneOperatorTest {
    private ArrayList<Person> people = new ArrayList<>();

    @Test
    public void testInitiate() {
        ArrayList<Operator> operators = new ArrayList<>();
        operators.add(new Operator(0, "Operator"));
        people.add(new Person("Man", 500, 200000, 1000, false));
        LinkedList<Thread> threads = new LinkedList<>();
        people.forEach(e -> threads.add(new PersonThread(e)));


        Organization organization = Organization.getInstance();
        organization.setUp();
        ((Manager) Organization.getInstance().getManager()).setWaitWithoutWork(2000);
        operators.forEach(e -> organization.getOperatorsRoom().addFreeOperator(e));
        organization.startWork();
        threads.forEach(Thread::start);

        try {
            sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test(dependsOnMethods = {"testInitiate"})
    public void testPersonSatisfied() {
        people.forEach(e -> assertEquals(e.isSatisfied(), true));
    }
}