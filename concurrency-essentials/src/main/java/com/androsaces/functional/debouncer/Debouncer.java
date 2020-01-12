/*
 * Copyright 2019. Androsaces. All rights reserved.
 */

package com.androsaces.functional.debouncer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Andrew Kearney
 */
public class Debouncer {
    private static final Logger log = LoggerFactory.getLogger(Debouncer.class);

    private final ScheduledExecutorService mExecutor = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture<?> mFuture;
    private long mInterval;

    public Debouncer() {

    }

    public void debounce(long delay, Runnable runnable) {
        if (mFuture != null && !mFuture.isDone()) {
            log.info("future is being cancelled");
            mFuture.cancel(false);
        }
        mFuture = mExecutor.schedule(runnable, delay, TimeUnit.MILLISECONDS);
    }

    public void shutdown() {
        mExecutor.shutdown();
    }

    public static void main(String[] args) {
        AtomicInteger value = new AtomicInteger(10);
        Debouncer debouncer = new Debouncer();
        debouncer.debounce(250L, () -> System.out.println(value.incrementAndGet()));
        debouncer.debounce(250L, () -> System.out.println(value.incrementAndGet()));
        debouncer.debounce(250L, () -> System.out.println(value.incrementAndGet()));

        debouncer.shutdown();
    }
}
