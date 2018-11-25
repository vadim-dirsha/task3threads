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

import static ru.vadimdirsha.java.model.LoggerMessageConst.SINGLETON_CLASS_NAME_CREATED_FORMAT;

/**
 * @author = Vadim Dirsha
 * @date = 24.11.2018
 */
public final class CallCenter implements ICallCenter {

    private static Logger logger = Logger.getLogger(CallCenter.class);

    public static CallCenter getInstance() {
        logger.info(String.format(SINGLETON_CLASS_NAME_CREATED_FORMAT, CallCenter.class.toString()));
        return SingletonHolder.HOLDER_INSTANCE;
    }

    @Override
    public boolean isCallQueueNotEmpty() {
        return false;
    }

    private static class SingletonHolder {
        static final CallCenter HOLDER_INSTANCE = new CallCenter();
    }
}
