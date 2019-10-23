/*
 * Copyright 2019. Androsaces. All rights reserved.
 */

package com.androsaces.samplecode.concurrency.lessonone.joining;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * As we have seen in the last section we can let our thread sleep until it is
 * woken up by another thread. Another important feature of threads that you
 * will have to use from time to time is the ability of a thread to wait for
 * the termination of another thread.
 *
 * Let’s assume you have to implement some kind of number crunching operation
 * that can be divided into several parallel running threads. The main thread
 * that starts the so called worker threads has to wait until all its child
 * threads have terminated.
 *
 * @author Andrew Kearney
 */
public class ThreadJoin implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(ThreadJoin.class);
    private Random mRandom = new Random(System.currentTimeMillis());

    @Override
    public void run() {
        for (int i = 0; i < 100000000; i++) {
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
