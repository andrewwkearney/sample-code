/*
 * Copyright 2019. Androsaces. All rights reserved.
 */

package com.androsaces.functional.concurrency.lessonthree.executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Andrew Kearney
 */
public class CyclicBarrierExample implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(CyclicBarrierExample.class);
    private static final int NUMBER_OF_THREADS = 5;
    private static AtomicInteger mCounter = new AtomicInteger();
    private static Random mRandom = new Random(System.currentTimeMillis());
    private static final CyclicBarrier BARRIER = new CyclicBarrier(5, mCounter::incrementAndGet);

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            executorService.execute(new CyclicBarrierExample());
        }
        executorService.shutdown();
    }

    @Override
    public void run() {
        try {
            while (mCounter.get() < 3) {
                int randomSleepTime = mRandom.nextInt(10000);
                log.info("sleeping for {}", randomSleepTime);
                Thread.sleep(randomSleepTime);
                log.info("waiting for barrier");
                BARRIER.await();
                log.info("finished.");
            }
        } catch (Exception e) {
            log.warn("error due to :", e);
        }
    }
}
