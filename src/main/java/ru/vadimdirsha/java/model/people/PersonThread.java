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
    private Condition conditionCommunicate = lock.newCondition();
    private boolean isWaiting = true;
    private Person person;
    private Phone phone = new Phone();

    public Person getPerson() {
        return person;
    }

    public PersonThread(Person person) {
        super();
        this.person = person;
    }

    public void setCommunicationState(boolean e) {
        lock.lock();
        person.setCommunicationState(e);
        try {
            conditionWaitSignal.signal();
        } finally {
            lock.unlock();
        }
    }

    private void callInOrganization() {
        logger.info(String.format(PEOPLE_CALLED_THE_ORGANIZATION, person.getName()));
        phone.callInOrganization(this);
    }

    private void hungUp() {
        logger.info(String.format(PEOPLE_FINISH_CALL, person.getName()));
        phone.hungUp();

    }

    @Override
    public void run() {
        setName(person.getName());
        waitDelayBeforeCalling();

        callInOrganization();

        waitOperator();

        if (person.isCommunicationState()) {
            communicateWithOperator();
        }

        hungUp();

        if (person.isReCall()) {
            reCallInitiate();
        }


    }

    private void reCallInitiate() {
        logger.info(String.format(PEOPLE_RE_CALL, person.getName()));
        person.setReCall(false);
        person.setWaitOperator(new Random().nextInt(person.getWaitOperator()) + person.getWaitOperator());
        person.setCommunicationState(false);
        this.run();
    }

    private void communicateWithOperator() {
        logger.info(String.format(PEOPLE_COMMUNICATE_WITH_OPERATOR, person.getName()));
        lock.lock();
        try {
            while (person.isCommunicationState()) {
                if (conditionCommunicate.await(person.getCommunicationTime(), TimeUnit.MILLISECONDS)) {
                    throw new InterruptedException();
                } else {
                    person.setCommunicationState(false);
                    phone.hungUp();
                }
            }
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
            interrupt();
        } finally {
            lock.unlock();
        }
        logger.info(String.format(PEOPLE_FINISH_COMMUNICATE_WITH_OPERATOR, person.getName()));
    }

    private void waitOperator() {
        logger.info(String.format(PEOPLE_WAITING_A_RESPONSE_FROM_THE_OPERATOR_MILS, person.getName(), person.getWaitOperator()));
        lock.lock();
        isWaiting = true;
        try {
            while (!person.isCommunicationState() && isWaiting) {
                if (!conditionWaitSignal.await(person.getWaitOperator(), TimeUnit.MILLISECONDS) && !person.isCommunicationState()) {
                    phone.hungUp();
                    isWaiting = false;
                    logger.info(String.format(PEOPLE_STOPPED_WAITING_OPERATOR_RESPONSE, person.getName()));
                }
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
        lock.lock();
        isWaiting = true;
        try {
            while (isWaiting) {
                if (replaceSleep.await(person.getDelayCalling(), TimeUnit.MILLISECONDS)) {
                    throw new InterruptedException();
                } else {
                    isWaiting = false;
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
