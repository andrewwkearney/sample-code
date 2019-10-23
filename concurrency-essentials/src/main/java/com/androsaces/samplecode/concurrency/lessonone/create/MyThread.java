/*
 * Copyright 2019. Androsaces. All rights reserved.
 */

package com.androsaces.samplecode.concurrency.lessonone.create;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * There are two ways to create a new thread in Java. The first way is to write
 * a class who extends the JDK class {@link java.lang.Thread}.
 *
 * @author Andrew Kearney
 */
@SuppressWarnings("WeakerAccess")
public class MyThread extends Thread {
    private static final Logger log = LoggerFactory.getLogger(MyThread.class);

    public MyThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        log.info("Executing thread {}", Thread.currentThread().getName());
    }
}
