/*
 * Copyright 2019. Androsaces. All rights reserved.
 */

package com.androsaces.samplecode.concurrency.lessonone.atomicaccess;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Andrew Kearney
 */
public class AtomicAccess implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(AtomicAccess.class);
    private static volatile Map<String, String> mConfiguration = new HashMap<>();

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            Map<String, String> currentConfiguration = mConfiguration;
            String value1 = currentConfiguration.get("key-1");
            String value2 = currentConfiguration.get("key-2");
            String value3 = currentConfiguration.get("key-3");
            if (!(value1.equals(value2) && value2.equals(value3))) {
                throw new IllegalStateException("Values are not equal.");
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                log.warn("interrupted by {}", e.getMessage());
            }
        }
    }

    public static void readConfig() {
        Map<String, String> newConfig = new HashMap<>();
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
        // newConfig.put("key-1", simpleDateFormat.format(now));
        // newConfig.put("key-2", simpleDateFormat.format(now));
        // newConfig.put("key-3", simpleDateFormat.format(now));
        // mConfiguration = newConfig;

        mConfiguration.put("key-1", simpleDateFormat.format(now));
        mConfiguration.put("key-2", simpleDateFormat.format(now));
        mConfiguration.put("key-3", simpleDateFormat.format(now));
    }

    public static void main(String[] args) throws InterruptedException {
        readConfig();
        Thread configThread = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                readConfig();
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "configuration-thread");
        configThread.start();
        Thread[] threads = new Thread[5];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new AtomicAccess(), "thread-" + i);
            threads[i].start();
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i].join();
        }
        configThread.join();
        log.info("[{}}] All threads have finished.", Thread.currentThread().getName());
    }
}
