/*
 * Copyright 2019. Androsaces. All rights reserved.
 */

package com.androsaces.samplecode.concurrency.lessonone.synchronization;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SynchronizedCounter implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(SynchronizedCounter.class);
    private static int mCounter = 0;

    @Override
    public void run() {
        while (mCounter < 10) {
            synchronized (SynchronizedCounter.class) {
                log.info("[{}] before: {}", Thread.currentThread().getName(), mCounter);
                mCounter++;
                log.info("[{}] after: {}", Thread.currentThread().getName(), mCounter);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[5];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new SynchronizedCounter(), "thread-" + i);
            threads[i].start();
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i].join();
        }
    }
}
