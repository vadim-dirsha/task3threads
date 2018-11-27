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
import ru.vadimdirsha.java.model.organization.operators.Operator;
import ru.vadimdirsha.java.model.organization.operators.OperatorThread;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author = Vadim Dirsha
 * @date = 27.11.2018
 */
public class OperatorsRoom {
    private static Logger logger = Logger.getLogger(OperatorsRoom.class);
    private Organization organization = Organization.getInstance();
    private Queue<Operator> freeOperators = new ConcurrentLinkedQueue<>();
    private List<OperatorThread> tasks = new ArrayList<>();

    public boolean isSameOperatorFree() {
        return !freeOperators.isEmpty();
    }

    public void addFreeOperator(Operator e) {
        freeOperators.add(e);

        Manager manager = organization.getManager();
        manager.getLock().lock();
        try {
            manager.getCondition().signal();
        } finally {
            manager.getLock().unlock();
        }
    }

    public void createTask(Call call) {
        if (!freeOperators.isEmpty()) {
            OperatorThread thread = new OperatorThread(call, freeOperators.poll(), this);
            tasks.add(thread);
            thread.start();
        }
    }

    public List<String> Print() {
        ArrayList<String> result = new ArrayList<>();
        tasks.forEach(e -> result.add(e.toString()));
        return result;
    }
}
