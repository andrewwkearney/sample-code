/*
 * Copyright 2019. Androsaces. All rights reserved.
 */

package com.androsaces.samplecode.concurrency.lessonone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andrew Kearney
 */
public class NotSynchronized implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(NotSynchronized.class);
    private static int mCounter = 0;

    @Override
    public void run() {
        while (mCounter < 10) {
            synchronized (NotSynchronized.class) {
                log.info("[{}] before: {}", Thread.currentThread().getName(), mCounter);
                mCounter++;
                log.info("[{}] after: {}", Thread.currentThread().getName(), mCounter);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[5];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new NotSynchronized(), "thread-" + i);
            threads[i].start();
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i].join();
        }
    }
}
