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
package ru.vadimdirsha.java.model.organization;

import org.apache.log4j.Logger;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static ru.vadimdirsha.java.consts.LoggerMessageConst.CALL_ADDED_IN_CALL_QUEUE_RESULT_ID_NAME;

/**
 * @author = Vadim Dirsha
 * @date = 24.11.2018
 */
public class CallCenter {
    private static Logger logger = Logger.getLogger(CallCenter.class);
    private Organization organization = Organization.getInstance();
    private Lock lock = new ReentrantLock();

    private Queue<Call> calls = new ConcurrentLinkedQueue<>();
    private int callCounter;


    public boolean isQueueEmpty() {
        return calls.isEmpty();
    }

    public boolean clientAddInQueue(Client e) {
        boolean result = calls.add(new Call(callCounter++, e));
        logger.info(String.format(CALL_ADDED_IN_CALL_QUEUE_RESULT_ID_NAME, result, callCounter - 1, e.getPersonThread().getPerson().getName()));

        organization.getManager().lookAtIt();
        return result;
    }

    public Queue<Call> getCalls() {
        return calls;
    }
}
