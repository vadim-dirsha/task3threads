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

/**
 * @author = Vadim Dirsha
 * @date = 24.11.2018
 */
public class Call {
    private static Logger logger = Logger.getLogger(Call.class);
    private int id;
    private Client client;

    public Call(int id, Client client) {
        this.id = id;
        this.client = client;
    }

    public Client getClient() {
        return client;
    }

    public int getId() {
        return id;
    }

    public boolean isActive() {
        return client.getPhoneThread().isAlive();
    }
}
