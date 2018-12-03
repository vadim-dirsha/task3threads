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

import ru.vadimdirsha.java.model.organization.Organization;
import ru.vadimdirsha.java.unit_behavior.PersonThread;
import ru.vadimdirsha.java.unit_behavior.PhoneThread;

/**
 * @author = Vadim Dirsha
 * @date = 29.11.2018
 */
public class Phone implements IPhone{
    private PhoneThread phoneThread;

    public void callInOrganization(PersonThread personThread) {
        phoneThread = new PhoneThread(personThread);
        phoneThread.start();
        if (!Organization.getInstance().callUp(phoneThread)) {
            phoneThread.interrupt();
        }

    }

    public void hungUp() {
        if (phoneThread != null)
            phoneThread.hungUp();
    }
}
