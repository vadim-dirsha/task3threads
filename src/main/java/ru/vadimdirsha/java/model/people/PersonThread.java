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

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import static ru.vadimdirsha.java.consts.LoggerMessageConst.*;

/**
 * @author = Vadim Dirsha
 * @date = 25.11.2018
 */
public class PersonThread extends Thread {
    private static Logger logger = Logger.getLogger(PersonThread.class);
    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    private Person people;

    public PersonThread(Person people) {
        super();
        this.people = people;
    }

    public Person getPeople() {
        return people;
    }

    public Condition getCondition() {
        return condition;
    }

    public ReentrantLock getLock() {
        return lock;
    }

    public void callInOrganization() {
        //TODO implementation
    }

    public void hungUp() {
        //TODO implementation
    }

    @Override
    public void run() {
        setName(people.getName());
        while (!people.isTimeOut()) {
            if (!isInterrupted()) {
                waitDelayBeforeCalling();
            }

            if (!isInterrupted()) {
                logger.info(String.format(PEOPLE_CALLED_THE_ORGANIZATION, people.getName()));
                callInOrganization();
                waitOperator();
            }

            if (!isInterrupted() && !people.isTimeOut() && people.isOperatorAnswered()) {
                communicateWithOperator();
            }

            if (!isInterrupted()) {
                logger.info(String.format(PEOPLE_FINISH_CALL, people.getName()));
                hungUp();
            }

            if (!isInterrupted() && people.isTimeOut() && people.isReCall()) {
                reCallInitiate();
            }
        }

    }

    private void reCallInitiate() {
        logger.info(String.format(PEOPLE_RE_CALL, people.getName()));
        people.setTimeOut(false);
        people.setReCall(false);
        people.setWaitOperator(new Random().nextInt(people.getWaitOperator()) + people.getWaitOperator());
        people.setOperatorAnswered(false);
    }

    private void communicateWithOperator() {
        people.setTimeOut(true);
        logger.info(String.format(PEOPLE_COMMUNICATE_WITH_OPERATOR, people.getName()));
        while (people.isOperatorAnswered()) {
            try {
                condition.await();
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
                interrupt();
            }
        }
        logger.info(String.format(PEOPLE_FINISH_COMMUNICATE_WITH_OPERATOR, people.getName()));
    }

    private void waitOperator() {
        Thread timeOut = new WaitOperatorResponseThread(this);
        timeOut.start();

        lock.lock();
        try {
            while (!people.isOperatorAnswered() && !people.isTimeOut()) {
                condition.await();
            }
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
            interrupt();
        } finally {
            lock.unlock();
        }
    }

    private void waitDelayBeforeCalling() {
        try {
            logger.info(String.format(PEOPLE_WILL_CALL_THE_ORGANIZATION_THROUGH_MILS, people.getName(), people.getDelayCalling()));
            sleep(people.getDelayCalling());
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
            interrupt();
        }
    }
}
