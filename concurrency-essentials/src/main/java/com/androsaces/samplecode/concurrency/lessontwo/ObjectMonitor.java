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
public class ObjectMonitor {
    private static final Logger log = LoggerFactory.getLogger(ObjectMonitor.class);
    private static final Queue<Integer> mQueue = new ConcurrentLinkedQueue<>();
    private static final long mStartTime = System.currentTimeMillis();

    public static class Consumer implements Runnable {
        @Override
        public void run() {
            while (System.currentTimeMillis() < (mStartTime + 10000)) {
                synchronized (mQueue) {
                    try {
                        mQueue.wait();
                    } catch (InterruptedException e) {
                        log.info("{}", e.getMessage());
                        Thread.currentThread().interrupt();
                    }
                    if (!mQueue.isEmpty()) {
                        Integer integer = mQueue.poll();
                        log.info("polled {} from the queue", integer);
                    }
                }
            }
        }
    }

    public static class Producer implements Runnable {
        @Override
        public void run() {
            int i = 0;
            while (System.currentTimeMillis() < (mStartTime + 10000)) {
                log.info("adding {} to the queue", i);
                mQueue.add(i++);
                synchronized (mQueue) {
                    mQueue.notify();
                }
                try {
                    Thread.sleep(100L);
                } catch (InterruptedException e) {
                    log.info("{}", e.getMessage());
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread[] consumerThreads = new Thread[5];
        for (int i = 0; i < consumerThreads.length; i++) {
            consumerThreads[i] = new Thread(new Consumer(), "consumer-" + i);
            consumerThreads[i].start();
        }
        Thread producerThread = new Thread(new Producer(), "producer");
        producerThread.start();
        for (Thread consumerThread : consumerThreads) {
            consumerThread.join();
        }
        producerThread.join();
    }
}
