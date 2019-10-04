package com.androsaces.samplecode.concurrency.introduction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InterruptExample implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(InterruptExample.class);

    @Override
    public void run() {
        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            log.info("[{}] interrupted by exception", Thread.currentThread().getName());
        }

        while (!Thread.interrupted()) {
            // do nothing here
        }
        log.info("[{}] interrupted a second time", Thread.currentThread().getName());
    }

    public static void main(String[] args) throws InterruptedException {
        Thread myThread = new Thread(new InterruptExample(), "myThread");
        myThread.start();

        log.info("[{}] sleeping in main thread for 5sec...", Thread.currentThread().getName());
        Thread.sleep(5000);

        log.info("[{}] interrupted myThread", Thread.currentThread().getName());
        myThread.interrupt();

        log.info("[{}] sleeping in main thread for 5sec...", Thread.currentThread().getName());
        Thread.sleep(5000);

        log.info("[{}] interrupted myThread", Thread.currentThread().getName());
        myThread.interrupt();
    }
}
