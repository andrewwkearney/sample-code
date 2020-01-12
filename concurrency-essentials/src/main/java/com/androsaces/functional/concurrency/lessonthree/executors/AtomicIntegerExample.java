/*
 * Copyright 2019. Androsaces. All rights reserved.
 */

package com.androsaces.functional.concurrency.lessonthree.executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Andrew Kearney
 */
public class AtomicIntegerExample implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(AtomicIntegerExample.class);
    private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger();

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            executorService.execute(new AtomicIntegerExample());
        }
        executorService.shutdown();
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            int newValue = ATOMIC_INTEGER.getAndIncrement();
            if (newValue == 42) {
                log.info("{}", newValue);
            }
        }
    }
}
