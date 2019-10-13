/*
 * Copyright 2019. Androsaces. All rights reserved.
 */

package com.androsaces.samplecode.concurrency.lessontwo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * @author Andrew Kearney
 */
public class Deadlock implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(Deadlock.class);

    private static final Object mResource1 = new Object();
    private static final Object mResource2 = new Object();
    private final Random mRandom = new Random(System.currentTimeMillis());

    public static void main(String[] args) {
        Thread myThread1 = new Thread(new Deadlock(), "thread-1");
        Thread myThread2 = new Thread(new Deadlock(), "thread-2");
        myThread1.start();
        myThread2.start();
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            boolean b = mRandom.nextBoolean();
            if (b) {
                log.info("[{}}] Trying to lock resource 1.", Thread.currentThread().getName());
                synchronized (mResource1) {
                    log.info("[{}}] Locked resource 1.", Thread.currentThread().getName());
                    log.info("[{}}] Trying to lock resource 2.", Thread.currentThread().getName());
                    synchronized (mResource2) {
                        log.info("[{}}] Locked resource 2.", Thread.currentThread().getName());
                    }
                }
            } else {
                log.info("[{}}] Trying to lock resource 2.", Thread.currentThread().getName());
                synchronized (mResource2) {
                    log.info("[{}}] Locked resource 2.", Thread.currentThread().getName());
                    log.info("[{}}] Trying to lock resource 1.", Thread.currentThread().getName());
                    synchronized (mResource1) {
                        log.info("[{}}] Locked resource 1.", Thread.currentThread().getName());
                    }
                }
            }
        }
    }
}
