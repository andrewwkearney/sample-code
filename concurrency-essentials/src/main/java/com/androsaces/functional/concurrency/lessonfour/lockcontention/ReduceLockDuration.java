/*
 * Copyright 2019. Androsaces. All rights reserved.
 */

package com.androsaces.functional.concurrency.lessonfour.lockcontention;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Andrew Kearney
 */
public class ReduceLockDuration implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(ReduceLockDuration.class);
    private static final int NUMBER_OF_THREADS = 5;
    private static final Map<String, Integer> MAP = new HashMap<>();

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            // synchronized (MAP) {
                UUID randromUUID = UUID.randomUUID();
                Integer value = 42;
                String key = randromUUID.toString();
                synchronized (MAP) {
                MAP.put(key, value);
            }
            Thread.yield();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[NUMBER_OF_THREADS];
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            threads[i] = new Thread(new ReduceLockDuration());
        }
        long startMillis = System.currentTimeMillis();
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            threads[i].start();
        }
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            threads[i].join();
        }
        long endMillis = System.currentTimeMillis();
        log.info("completion time: {}", endMillis - startMillis);
    }
}
