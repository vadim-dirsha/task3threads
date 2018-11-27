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

import static ru.vadimdirsha.java.consts.LoggerMessageConst.SINGLETON_CLASS_NAME_CREATED_FORMAT;

/**
 * @author = Vadim Dirsha
 * @date = 24.11.2018
 */
public final class Organization {
    private static Logger logger = Logger.getLogger(Organization.class);
    private int clientCounter = 0;
    private CallCenter callCenter;
    private Manager manager;

    public void setManager(Manager manager) {
        this.manager = manager;
        callCenter = new CallCenter(manager);
    }

    public static Organization getInstance() {
        return Organization.SingletonHolder.HOLDER_INSTANCE;
    }

    public CallCenter getCallCenter() {
        return callCenter;
    }

    public boolean callUp(PersonThread e) {
        return callCenter.clientAddInQueue(new Client(clientCounter++, e));
    }

    private static class SingletonHolder {
        static final Organization HOLDER_INSTANCE = new Organization();
    }

    public void startWork(){
        manager.start();
    }
}
