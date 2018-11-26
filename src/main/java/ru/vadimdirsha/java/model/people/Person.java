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

/**
 * @author = Vadim Dirsha
 * @date = 26.11.2018
 */
public class Person {
    private static Logger logger = Logger.getLogger(Person.class);
    private String name;
    private int delayCalling;
    private int waitOperator;
    private boolean reCall;
    private boolean isOperatorAnswered = false;
    private boolean isTimeOut = false;

    public Person(String name, int callingDelay, int waitOperator, boolean reCall) {
        this.name = name;
        this.delayCalling = callingDelay;
        this.waitOperator = waitOperator;
        this.reCall = reCall;
    }

    public String getName() {
        return name;
    }

    public int getDelayCalling() {
        return delayCalling;
    }

    public void setDelayCalling(int delayCalling) {
        this.delayCalling = delayCalling;
    }

    public int getWaitOperator() {
        return waitOperator;
    }

    public void setWaitOperator(int waitOperator) {
        this.waitOperator = waitOperator;
    }

    public boolean isReCall() {
        return reCall;
    }

    public void setReCall(boolean reCall) {
        this.reCall = reCall;
    }

    public boolean isOperatorAnswered() {
        return isOperatorAnswered;
    }

    public void setOperatorAnswered(boolean operatorAnswered) {
        isOperatorAnswered = operatorAnswered;
    }

    public boolean isTimeOut() {
        return isTimeOut;
    }

    public void setTimeOut(boolean timeOut) {
        isTimeOut = timeOut;
    }
}
