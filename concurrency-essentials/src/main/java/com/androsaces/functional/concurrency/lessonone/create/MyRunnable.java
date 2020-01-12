/*
 * Copyright 2019. Androsaces. All rights reserved.
 */

package com.androsaces.functional.concurrency.lessonone.create;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * There are two ways to create a new thread in Java. The second way is to
 * write a class who implements the JDK interface {@link java.lang.Runnable}.
 *
 * @author Andrew Kearney
 */
public class MyRunnable implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(MyRunnable.class);

    @Override
    public void run() {
        log.info("Executing thread {}", Thread.currentThread().getName());
    }
}
