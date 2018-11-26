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

import static ru.vadimdirsha.java.consts.LoggerMessageConst.PEOPLE_DIDN_T_WAIT_OPERATOR_RESPONSE;
import static ru.vadimdirsha.java.consts.LoggerMessageConst.PEOPLE_WAITING_A_RESPONSE_FROM_THE_OPERATOR_MILS;

/**
 * @author = Vadim Dirsha
 * @date = 26.11.2018
 */
public class WaitOperatorResponseThread extends Thread {
    private static Logger logger = Logger.getLogger(WaitOperatorResponseThread.class);
    PersonThread peopleThread;

    public WaitOperatorResponseThread(PersonThread peopleThread) {
        super();
        this.peopleThread = peopleThread;
    }

    @Override
    public void run() {
        this.setName(peopleThread.getName() + "Wait");

        logger.info(String.format(PEOPLE_WAITING_A_RESPONSE_FROM_THE_OPERATOR_MILS, peopleThread.getName(), peopleThread.getPeople().getWaitOperator()));
        try {
            sleep(peopleThread.getPeople().getWaitOperator());
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
            interrupt();
        }
        if (!isInterrupted()) {
            peopleThread.getLock().lock();
            try {
                peopleThread.getPeople().setTimeOut(true);
                logger.info(String.format(PEOPLE_DIDN_T_WAIT_OPERATOR_RESPONSE, peopleThread.getName()));
                peopleThread.getCondition().signal();
            } finally {
                peopleThread.getLock().unlock();
            }
        }
    }
}
