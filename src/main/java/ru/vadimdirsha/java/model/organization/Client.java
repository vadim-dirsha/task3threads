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
import ru.vadimdirsha.java.model.people.Person;
import ru.vadimdirsha.java.unit_behavior.PhoneThread;

/**
 * @author = Vadim Dirsha
 * @date = 24.11.2018
 */
public class Client implements IClient {
    private static Logger logger = Logger.getLogger(Client.class);
    private int id;
    private Person person;
    private PhoneThread phoneThread;

    public Client(int id, PhoneThread phoneThread) {
        this.id = id;
        this.phoneThread = phoneThread;
        this.person = phoneThread.getPerson();
    }

    public Person getPerson() {
        return person;
    }

    public PhoneThread getPhoneThread() {
        return phoneThread;
    }

}
