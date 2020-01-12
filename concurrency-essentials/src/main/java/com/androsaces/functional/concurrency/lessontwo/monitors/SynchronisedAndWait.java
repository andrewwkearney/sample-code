/*
 * Copyright 2019. Androsaces. All rights reserved.
 */

package com.androsaces.functional.concurrency.lessontwo.monitors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * As mentioned in previous code, calling wait() on an object monitor only frees
 * the lock on this object monitor. Other locks which are being hold by the sam
 * e thread are not freed. As this is easy to understand, it may happen in
 * day-to-day work that the thread that calls wait() holds further locks. And if
 * other threads are also waiting for these locks a deadlock situation can happen.
 *
 * Adding the {@code synchronized} keyword to a method signature is the same as:
 * <pre>
 * synchronized (this) {}
 * </pre>
 * @author Andrew Kearney
 */
public class SynchronisedAndWait {
    private static final Logger log = LoggerFactory.getLogger(SynchronisedAndWait.class);
    private static final Queue<Integer> mQueue = new ConcurrentLinkedQueue<>();

    public synchronized Integer getNextInt() {
        Integer retVal = null;
        while (retVal == null) {
            synchronized (mQueue) {
                try {
                    mQueue.wait();
                } catch (InterruptedException e) {
                    log.info("{}", e.getMessage());
                    Thread.currentThread().interrupt();
                }
                retVal = mQueue.poll();
            }
        }
        return retVal;
    }

    public synchronized void putInt(Integer value) {
        synchronized (mQueue) {
            log.info("putting {} into queue", value);
            mQueue.add(value);
            mQueue.notify();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SynchronisedAndWait queue = new SynchronisedAndWait();
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                queue.putInt(i);
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    Integer nextInt = queue.getNextInt();
                    log.info("next int: {}", nextInt);
                }
            }
        });

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
    }
}
