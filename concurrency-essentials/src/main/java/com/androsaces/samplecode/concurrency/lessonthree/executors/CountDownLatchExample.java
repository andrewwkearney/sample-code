/*
 * Copyright 2019. Androsaces. All rights reserved.
 */

package com.androsaces.samplecode.concurrency.lessonthree.executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Andrew Kearney
 */
public class CountDownLatchExample implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(CountDownLatchExample.class);
    private static final int NUMBER_OF_THREADS = 5;
    private static final CountDownLatch LATCH = new CountDownLatch(NUMBER_OF_THREADS);
    private static Random mRandom = new Random(System.currentTimeMillis());

    @Override
    public void run() {
        try {
            int randomSleepTime = mRandom.nextInt(20000);
            log.info("sleeping for {}ms", randomSleepTime);
            Thread.sleep(randomSleepTime);
            LATCH.countDown();
            log.info("waiting for latch");
            LATCH.await();
            log.info("finished");
        } catch (InterruptedException e) {
            log.warn("interrupted thread");
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            executorService.execute(new CountDownLatchExample());
        }
        executorService.shutdown();
    }
}
