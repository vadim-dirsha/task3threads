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

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author = Vadim Dirsha
 * @date = 27.11.2018
 */
public class Manager extends Thread {
    public static final String END_OF_WORK_DAY = "end of work day";
    private static Logger logger = Logger.getLogger(Manager.class);
    private Organization organization = Organization.getInstance();
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void lookAtIt() {
        lock.lock();
        try {
            condition.signal();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void run() {
        setName("Manager");
        OperatorsRoom operatorsRoom = organization.getOperatorsRoom();
        CallCenter callCenter = organization.getCallCenter();
        while (!isInterrupted()) {
            lock.lock();
            try {
                while (callCenter.isQueueEmpty() || !operatorsRoom.isSameOperatorFree()) {
                    if (!condition.await(120000, TimeUnit.MILLISECONDS)) {
                        throw new InterruptedException(END_OF_WORK_DAY);
                    }
                }
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
                interrupt();
            } finally {
                lock.unlock();
            }
            createTask();
        }
    }

    private void createTask() {
        organization.getOperatorsRoom().createTask(organization.getCallCenter().getCalls().poll());
    }
}