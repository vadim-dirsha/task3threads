/**
 * Vadim Dirsha
 * Copyright (c) 2018 Java Open Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */
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
 * @date = 03.12.2018
 */
public class FewOperatorsManyPeopleTest {
    private ArrayList<Person> people = new ArrayList<>();

    @Test
    public void testInitiate() {
        ArrayList<Operator> operators = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            operators.add(new Operator(i, "Operator"));
        }

        for (int i = 0; i < 10; i++) {
            people.add(new Person("Man" + i, 500, 200000, 1000, false));
        }
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
