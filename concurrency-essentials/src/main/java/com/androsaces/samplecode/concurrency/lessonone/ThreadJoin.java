/*
 * Copyright 2019. Androsaces. All rights reserved.
 */

package com.androsaces.samplecode.concurrency.lessonone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * @author Andrew Kearney
 */
public class ThreadJoin implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(ThreadJoin.class);
    private Random mRandom = new Random(System.currentTimeMillis());

    @Override
    public void run() {
        for (int i = 0; i < 1000000; i++) {
            mRandom.nextInt();
        }
        log.info("finished");
    }

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[5];

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new ThreadJoin(), "joinThread-" + i);
            threads[i].start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
        log.info("all threads have finished");
    }
}
