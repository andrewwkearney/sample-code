/*
 * Copyright 2021. Androsaces. All rights reserved.
 */

package com.androsaces.javaessentials.issue262;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class BruteForceParallel extends BruteForceBase {
   private static final Logger log = LoggerFactory.getLogger(BruteForceParallel.class);

    public static void main(String[] args) {
        ThreadFactory factory = Executors.defaultThreadFactory();
        ExecutorService pool = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors(),
                r -> {
                    // always give threads descriptive names
                    Thread thread = factory.newThread(r);
                    thread.setName(thread.getName() + "#bruteforce");
                    return thread;
                }
        );
        int target = 0; // our target hash code
        for (byte i0 : alphabet) {
            pool.submit(() -> {
                int h0 = i0;
                for (byte i1 : alphabet) {
                    int h1 = h0 * 31 + i1;
                    for (byte i2 : alphabet) {
                        int h2 = h1 * 31 + i2;
                        for (byte i3 : alphabet) {
                            int h3 = h2 * 31 + i3;
                            for (byte i4 : alphabet) {
                                int h4 = h3 * 31 + i4;
                                for (byte i5 : alphabet) {
                                    int h5 = h4 * 31 + i5;
                                    for (byte i6 : alphabet) {
                                        int h6 = h5 * 31 + i6;
                                        if (h6 == target) {
                                            byte[] is = {i0, i1, i2, i3, i4, i5, i6};
                                            log.info(new String(is));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            });
        }
        pool.shutdown();
        // once all threads are done, we naturally shutdown
    }
}
