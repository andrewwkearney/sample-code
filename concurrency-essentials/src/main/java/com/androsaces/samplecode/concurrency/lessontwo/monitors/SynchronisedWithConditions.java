/*
 * Copyright 2019. Androsaces. All rights reserved.
 */

package com.androsaces.samplecode.concurrency.lessontwo.monitors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Often you will have to check that some condition is fulfilled, before you
 * execute some action on a synchronized object. When you have for example a
 * queue you want to wait until this queue is filled. Hence you can write a
 * method that checks if the queue is filled. If not you put the current thread
 * asleep until it is woken up.
 *
 * The code above synchronizes on the queue before calling wait() and then waits
 * within the while loop until the queue has at least one entry. The second
 * synchronized block again uses the queue as object monitor. It polls() the
 * queue for the value inside. For demonstration purposes an
 * IllegalStateException is thrown when poll() returns null. This is the case
 * when there are no values inside the queue to poll.
 *
 * @author Andrew Kearney
 */
public class SynchronisedWithConditions {
    private static final Logger log = LoggerFactory.getLogger(SynchronisedWithConditions.class);
    private static final Queue<Integer> mQueue = new ConcurrentLinkedQueue<>();

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
                String errorString = "retVal is null";
                log.error(errorString);
                throw new IllegalStateException(errorString);
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
        SynchronisedWithConditions queue = new SynchronisedWithConditions();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    queue.putInt(i);
                }
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
