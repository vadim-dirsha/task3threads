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

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author = Vadim Dirsha
 * @date = 26.11.2018
 */
public class OperatorThread extends Thread {
    private static Logger logger = Logger.getLogger(Client.class);
    OperatorsRoom operatorsRoom;
    private Operator operator;
    private Call call;
    private Lock lock = new ReentrantLock();

    public OperatorThread(Call call, Operator operator, OperatorsRoom operatorsRoom) {
        super();
        this.call = call;
        this.operator = operator;
        this.operatorsRoom = operatorsRoom;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            call.getClient().getPerson().setOperatorAnswered(true);
            call.getClient().getPersonThread().getConditionCommunicate().signal();
            while (call.getClient().getPerson().isOperatorAnswered()) {
                lock.lock();
                try {
                    call.getClient().getPersonThread().getConditionCommunicate().await();
                } catch (InterruptedException e) {
                    logger.error(e.getMessage(), e);
                    interrupt();
                } finally {
                    lock.unlock();
                }
            }
            operatorsRoom.addFreeOperator(operator);
            interrupt();
        }
    }
}