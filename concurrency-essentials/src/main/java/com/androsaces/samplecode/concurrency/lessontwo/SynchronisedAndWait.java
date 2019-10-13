/*
 * Copyright 2019. Androsaces. All rights reserved.
 */

package com.androsaces.samplecode.concurrency.lessontwo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author Andrew Kearney
 */
public class SynchronisedAndWait {
    private static final Logger log = LoggerFactory.getLogger(SynchronisedAndWait.class);
    private static final Queue<Integer> mQueue = new ConcurrentLinkedQueue<>();

/*
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
*/

    public Integer getNextInt() {
        Integer retVal = null;
        synchronized (mQueue) {
            try {
                while (mQueue.isEmpty()) {
                    mQueue.wait();
                }
            } catch (InterruptedException e) {
                log.info("{}", e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
        synchronized (mQueue) {
            retVal = mQueue.poll();
            if (retVal == null) {
                log.info("retVal is null");
                throw new IllegalStateException();
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
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                Integer nextInt = queue.getNextInt();
                log.info("next int: {}", nextInt);
            }
        });

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
    }
}
