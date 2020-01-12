/*
 * Copyright 2019. Androsaces. All rights reserved.
 */

package com.androsaces.functional.concurrency.lessontwo.liveness;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * As can be seen from the code above, two threads are started and try to lock
 * the two static resources. But for a deadlock we need a different sequence
 * for both threads, hence we utilize the Random instance to choose what
 * resource the thread wants to lock first. If the boolean variable b is true,
 * the resource1 is locked first and the threads then tries to get the lock
 * for resource 2. If b is false, the thread locks resource2 first and then
 * tries to lock resource1. This program does not have to run long until we
 * reach the first deadlock, i.e. the program hangs forever if we would not
 * terminate it:
 * <pre>
 *     01 | [thread-1] Trying to lock resource 1.
 *     02 |
 *     03 | [thread-1] Locked resource 1.
 *     04 |
 *     05 | [thread-1] Trying to lock resource 2.
 *     06 |
 *     07 | [thread-1] Locked resource 2.
 *     08 |
 *     09 | [thread-2] Trying to lock resource 1.
 *     10 |
 *     11 | [thread-2] Locked resource 1.
 *     12 |
 *     13 | [thread-1] Trying to lock resource 2.
 *     14 |
 *     15 | [thread-1] Locked resource 2.
 *     16 |
 *     17 | [thread-2] Trying to lock resource 2.
 *     18 |
 *     19 | [thread-1] Trying to lock resource 1.
 * </pre>
 *
 * In this execution thread-1 holds the lock for resource2 and waits for the
 * lock on resource1, whereas thread-2 holds the lock for resource1 and waits
 * for resource2.
 *
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
