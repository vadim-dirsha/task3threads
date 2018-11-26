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
import ru.vadimdirsha.java.model.organization.Organization;

import static ru.vadimdirsha.java.consts.LoggerMessageConst.SINGLETON_CLASS_NAME_CREATED_FORMAT;

/**
 * @author = Vadim Dirsha
 * @date = 25.11.2018
 */
public final class Hive implements IHive {
    private static Logger logger = Logger.getLogger(Hive.class);

    public static Hive getInstance() {
        logger.info(String.format(SINGLETON_CLASS_NAME_CREATED_FORMAT, Hive.class.toString()));
        return Hive.SingletonHolder.HOLDER_INSTANCE;
    }

    public Organization.IPeople createClient() {
        //TODO implementation
        return null;
    }

    private static class SingletonHolder {
        static final Hive HOLDER_INSTANCE = new Hive();
    }
}
