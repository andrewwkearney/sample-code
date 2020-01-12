/*
 * Copyright 2019. Androsaces. All rights reserved.
 */

package com.androsaces.functional.concurrency.lessontwo.threadlocal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * We have seen so far that threads share the same memory. In terms of
 * performance this is a good way to share data between the threads. If we would
 * have used separate processes in order to execute code in parallel, we would
 * have more heavy data exchange methods, like remote procedure calls or
 * synchronization on file system or network level. But sharing memory between
 * different threads is also difficult to handle if not synchronized properly.
 *
 * The type of data that should be stored within the ThreadLocal is given by the
 * generic template parameter T. In the example above we used just Integer, but
 * we could have used any other data type here as well. The following code
 * demonstrates the usage of ThreadLocal
 *
 * @author Andrew Kearney
 */
public class ThreadLocalExample implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(ThreadLocalExample.class);

    private static final ThreadLocal<Integer> mThreadLocal = new ThreadLocal<>();
    private final int mValue;

    public ThreadLocalExample(int value) {
        mValue = value;
    }

    @Override
    public void run() {
        mThreadLocal.set(mValue);
        Integer integer = mThreadLocal.get();
        log.info("{}", integer);
    }

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[5];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new ThreadLocalExample(i), "thread-" + 1);
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }
    }
}
