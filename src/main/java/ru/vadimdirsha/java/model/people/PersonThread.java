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
import java.util.concurrent.TimeUnit;
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
    private Condition conditionWaitSignal = lock.newCondition();
    private Condition replaceSleep = lock.newCondition();
    private boolean isSomeObscureHappened = true;
    private Person person;

    public PersonThread(Person person) {
        super();
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }

    public Condition getConditionWaitSignal() {
        return conditionWaitSignal;
    }

    public ReentrantLock getLock() {
        return lock;
    }

    private void callInOrganization() {
        logger.info(String.format(PEOPLE_CALLED_THE_ORGANIZATION, person.getName()));
        //TODO implementation
    }

    private void hungUp() {
        logger.info(String.format(PEOPLE_FINISH_CALL, person.getName()));
        //TODO implementation
    }

    @Override
    public void run() {
        setName(person.getName());
        while (!person.isTimeOut() && !isInterrupted()) {
            waitDelayBeforeCalling();

            callInOrganization();

            waitOperator();

            if (!person.isTimeOut() && person.isOperatorAnswered()) {
                communicateWithOperator();
            }

            hungUp();

            if (person.isTimeOut() && person.isReCall()) {
                reCallInitiate();
            }
        }

    }

    private void reCallInitiate() {
        logger.info(String.format(PEOPLE_RE_CALL, person.getName()));
        person.setTimeOut(false);
        person.setReCall(false);
        person.setWaitOperator(new Random().nextInt(person.getWaitOperator()) + person.getWaitOperator());
        person.setOperatorAnswered(false);
    }

    private void communicateWithOperator() {
        person.setTimeOut(true);
        logger.info(String.format(PEOPLE_COMMUNICATE_WITH_OPERATOR, person.getName()));
        while (person.isOperatorAnswered()) {
            try {
                conditionWaitSignal.await();
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
                interrupt();
            }
        }
        logger.info(String.format(PEOPLE_FINISH_COMMUNICATE_WITH_OPERATOR, person.getName()));
    }

    private void waitOperator() {
        Thread timeOut = new WaitOperatorResponseThread(this);
        timeOut.start();

        lock.lock();
        try {
            while (!person.isOperatorAnswered() && !person.isTimeOut()) {
                conditionWaitSignal.await();
            }
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
            interrupt();
        } finally {
            lock.unlock();
        }
    }

    private void waitDelayBeforeCalling() {
        logger.info(String.format(PEOPLE_WILL_CALL_THE_ORGANIZATION_THROUGH_MILS, person.getName(), person.getDelayCalling()));
        try {
            lock.lock();
            while (isSomeObscureHappened) {
                if (replaceSleep.await(person.getDelayCalling(), TimeUnit.MILLISECONDS)) {
                    isSomeObscureHappened = true;
                } else {
                    throw new InterruptedException();
                }
            }
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
            interrupt();
        } finally {
            lock.unlock();
        }
    }
}
