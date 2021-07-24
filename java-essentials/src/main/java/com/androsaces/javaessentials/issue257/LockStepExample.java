/*
 * Copyright 2020. Androsaces. All rights reserved.
 */

package com.androsaces.javaessentials.issue257;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadLocalRandom;

public class LockStepExample {
    private static final Logger log = LoggerFactory.getLogger(LockStepExample.class);
    protected final static int TASKS_PER_BATCH = 3;
    protected final static int BATCHES = 5;

    protected final void doTask(int batch) {
        log.info("Task start {}", batch);
        int ms = ThreadLocalRandom.current().nextInt(500, 3000);
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        log.info("Task in batch {} took {}ms", batch, ms);
    }
}
