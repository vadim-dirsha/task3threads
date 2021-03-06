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
package ru.vadimdirsha.java.consts;

/**
 * @author = Vadim Dirsha
 * @date = 24.11.2018
 */
public final class LoggerMessageConst {
    public static final String PEOPLE_CALLED_THE_ORGANIZATION = "%1$s called the Organization.";
    public static final String PEOPLE_WAITING_A_RESPONSE_FROM_THE_OPERATOR_MILS = "%1$s waiting a response from the operator - %2$d ms.";
    public static final String PEOPLE_WILL_CALL_THE_ORGANIZATION_THROUGH_MILS = "%1$s will call the Organization through - %2$d ms.";
    public static final String SINGLETON_CLASS_NAME_CREATED_FORMAT = "Singleton %1$s created.";
    public static final String PEOPLE_COMMUNICATE_WITH_OPERATOR = "%1$s communicate with operator.";
    public static final String PEOPLE_FINISH_COMMUNICATE_WITH_OPERATOR = "%1$s finish communicate with operator.";
    public static final String PEOPLE_RE_CALL = "%1$s recall %2$s time.";
    public static final String PEOPLE_FINISH_CALL = "%1$s finish call.";
    public static final String PEOPLE_STOPPED_WAITING_OPERATOR_RESPONSE = "%1$s stopped waiting operator response.";
    public static final String CALL_ADDED_IN_CALL_QUEUE_RESULT_ID_NAME = "Call added in CallQueue - %1$s, id - %2$s, name - %3$s";
    public static final String PERSON_HUNG_UP_BEFORE_OPERATOR_ANSWER = "%1$s hung up before operator answer.";
    public static final String OPERATOR_ANSWERED_PERSON = "%1$s answered %2$s";
    public static final String CALL_ID_ENDED = "Call %1$s ended";

    private LoggerMessageConst() {
    }
}
