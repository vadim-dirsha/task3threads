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
package ru.vadimdirsha.java.model;

import org.apache.log4j.Logger;

import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static ru.vadimdirsha.java.model.LoggerMessageConst.*;

/**
 * @author = Vadim Dirsha
 * @date = 25.11.2018
 */
public class People extends Thread implements IPeople {
    private static Logger logger = Logger.getLogger(People.class);
    private String name;
    private int delayBeforeCalling;
    private int waitForAnswer;
    private boolean randReCall;
    private boolean operatorAnswered = false;
    private boolean waitTimeOut = false;
    ReadWriteLock lock = new ReentrantReadWriteLock();

    public People(String name, int delayBeforeCalling, int waitForAnswer, boolean randReCall) {
        this.name = name;
        this.delayBeforeCalling = delayBeforeCalling;
        this.waitForAnswer = waitForAnswer;
        this.randReCall = randReCall;
    }

    @Override
    public void callInOrganization() {
        //TODO implementation
    }

    @Override
    public void hungUp() {
        //TODO implementation
    }

    @Override
    public void run() {
        setName(name);
        while (!waitTimeOut) {
            if (!isInterrupted()) {
                try {
                    logger.info(String.format(PEOPLE_WILL_CALL_THE_ORGANIZATION_THROUGH_MILS, name, delayBeforeCalling));
                    sleep(delayBeforeCalling);
                } catch (InterruptedException e) {
                    logger.error(e.getMessage(), e);
                    interrupt();
                }
            }

            if (!isInterrupted()) {
                callInOrganization();
                logger.info(String.format(PEOPLE_CALLED_THE_ORGANIZATION, name));
                Thread timeOut = new Thread(() -> {
                    setName(name+" wait");
                    try {
                        sleep(waitForAnswer);
                    } catch (InterruptedException e) {
                        logger.error(e.getMessage(), e);
                        interrupt();
                    } finally {
                        waitTimeOut = true;
                    }
                });
                timeOut.start();

                logger.info(String.format(PEOPLE_WAITING_A_RESPONSE_FROM_THE_OPERATOR_MILS, name, waitForAnswer));
                try {
                    while (!operatorAnswered && !waitTimeOut) {
                        wait();
                    }
                } catch (InterruptedException e) {
                    logger.error(e.getMessage(), e);
                    interrupt();
                }
            }

            if (!isInterrupted() && !waitTimeOut && operatorAnswered) {
                waitTimeOut = true;
                logger.info(String.format(PEOPLE_COMMUNICATE_WITH_OPERATOR, name));
                while (operatorAnswered) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        logger.error(e.getMessage(), e);
                        interrupt();
                    }
                }
                logger.info(String.format(PEOPLE_FINISH_COMMUNICATE_WITH_OPERATOR, name));
            }

            if (!isInterrupted()) {
                logger.info(String.format(PEOPLE_FINISH_CALL, name));
                hungUp();
            }

            if (!isInterrupted() && waitTimeOut && randReCall) {
                logger.info(String.format(PEOPLE_RE_CALL, name));
                waitTimeOut = false;
                randReCall = false;
                waitForAnswer = (new Random().nextInt(waitForAnswer) + waitForAnswer);
                operatorAnswered = false;
            }
        }

    }
}
