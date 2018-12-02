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
package ru.vadimdirsha.java.model.people;

import org.apache.log4j.Logger;
import ru.vadimdirsha.java.model.organization.Manager;
import ru.vadimdirsha.java.model.organization.Organization;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author = Vadim Dirsha
 * @date = 29.11.2018
 */
public class PhoneThread extends Thread {
    private static Logger logger = Logger.getLogger(Manager.class);
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    private Person person;
    private PersonThread personThread;

    public PhoneThread(PersonThread personThread) {
        super();
        this.person = personThread.getPerson();
        this.personThread = personThread;
    }

    public PersonThread getPersonThread() {
        return personThread;
    }

    public Person getPerson() {
        return person;
    }

    public void hungUp() {
        lock.lock();
        try {
            condition.signal();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void run() {
        setName(person.getName() + "Phone");
        while (!isInterrupted()) {
            lock.lock();
            try {
                condition.await();
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
                interrupt();
            } finally {
                lock.unlock();
            }
            interrupt();
        }
    }
}
