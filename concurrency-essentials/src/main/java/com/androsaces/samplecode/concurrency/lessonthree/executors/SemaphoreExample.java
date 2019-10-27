/*
 * Copyright 2019. Androsaces. All rights reserved.
 */

package com.androsaces.samplecode.concurrency.lessonthree.executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Andrew Kearney
 */
public class SemaphoreExample implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(SemaphoreExample.class);
    private static final Semaphore SEMAPHORE = new Semaphore(3, true);
    private static final AtomicInteger COUNTER = new AtomicInteger();
    private static final long END_MILLIS = System.currentTimeMillis() + 10000L;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            executorService.execute(new SemaphoreExample());
        }
        executorService.shutdown();
    }

    @Override
    public void run() {
        while (System.currentTimeMillis() < END_MILLIS) {
            try {
                SEMAPHORE.acquire();
            } catch (InterruptedException e) {
                log.info("interrupted in acquire()");
                Thread.currentThread().interrupt();
            }
            int counterValue = COUNTER.incrementAndGet();
            log.info("semaphore acquired: {}", counterValue);
            if (counterValue > 3) {
                throw new IllegalStateException("More than three threads acquired the lock");
            }
            COUNTER.decrementAndGet();
            SEMAPHORE.release();
        }
    }
}
