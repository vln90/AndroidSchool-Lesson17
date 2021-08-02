package com.vleonidov.lesson_17.utils;

import io.reactivex.Scheduler;

/**
 * @author Леонидов Василий on 8/3/21
 */
public interface ISchedulersProvider {

    Scheduler io();

    Scheduler ui();
}
