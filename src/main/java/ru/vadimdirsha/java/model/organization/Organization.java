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
import ru.vadimdirsha.java.model.people.PersonThread;
import ru.vadimdirsha.java.model.people.PhoneThread;

/**
 * @author = Vadim Dirsha
 * @date = 24.11.2018
 */
public final class Organization {
    private static Logger logger = Logger.getLogger(Organization.class);
    private int clientCounter = 0;
    private CallCenter callCenter;
    private Manager manager;
    private OperatorsRoom operatorsRoom;

    public static Organization getInstance() {
        return Organization.SingletonHolder.HOLDER_INSTANCE;
    }

    public void setUp() {
        this.manager = new Manager();
        this.callCenter = new CallCenter();
        this.operatorsRoom = new OperatorsRoom();
    }

    public Manager getManager() {
        return manager;
    }

    public OperatorsRoom getOperatorsRoom() {
        return operatorsRoom;
    }

    public CallCenter getCallCenter() {
        return callCenter;
    }

    public boolean callUp(PhoneThread e) {
        boolean result = callCenter.clientAddInQueue(new Client(clientCounter++, e));
        return result;
    }

    public void startWork() {
        manager.start();
    }

    private static class SingletonHolder {
        static final Organization HOLDER_INSTANCE = new Organization();
    }
}
