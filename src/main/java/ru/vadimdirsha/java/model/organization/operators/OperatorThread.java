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
package ru.vadimdirsha.java.model.organization.operators;

import org.apache.log4j.Logger;
import ru.vadimdirsha.java.model.organization.Call;
import ru.vadimdirsha.java.model.organization.Client;
import ru.vadimdirsha.java.model.organization.OperatorsRoom;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static ru.vadimdirsha.java.consts.LoggerMessageConst.*;

/**
 * @author = Vadim Dirsha
 * @date = 26.11.2018
 */
public class OperatorThread extends Thread {
    private static Logger logger = Logger.getLogger(Client.class);
    private OperatorsRoom operatorsRoom;
    private Operator operator;
    private Call call;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public OperatorThread(Call call, Operator operator, OperatorsRoom operatorsRoom) {
        super();
        this.call = call;
        this.operator = operator;
        this.operatorsRoom = operatorsRoom;
    }

    public void endCommunicate() {
        lock.lock();
        try {
            condition.signal();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void run() {
        setName(operator.getName() + "-" + call.getClient().getPerson().getName());
        while (!isInterrupted()) {
            if (call.isActive()) {
                call.getClient().getPersonThread().setCommunication(true, this);
                lock.lock();
                logger.info(String.format(OPERATOR_ANSWERED_PERSON, call.getClient().getPerson().getName(), operator.getName()));
                try {
                    while (call.getClient().getPerson().isCommunicationState()) {
                        condition.await();
                    }
                    logger.info(String.format(CALL_ID_ENDED, call.getId() + ""));
                } catch (InterruptedException e) {
                    logger.error(e.getMessage(), e);
                    interrupt();
                } finally {
                    lock.unlock();
                }
            } else {
                logger.info(String.format(PERSON_HUNG_UP_BEFORE_OPERATOR_ANSWER, call.getClient().getPerson().getName()));
            }
            operatorsRoom.addFreeOperator(operator);
            interrupt();
        }
    }
}
