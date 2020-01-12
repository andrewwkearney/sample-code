/*
 * Copyright 2019. Androsaces. All rights reserved.
 */

package com.androsaces.functional.concurrency.lessonone.sleep;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Within the main method we start a new thread first, which would sleep for a
 * very long time (about 290,000 years) if it would not be interrupted. To get
 * the program finished before this time has passed by, {@code myThread} is
 * interrupted by calling {@code interrupt()} on its instance variable in the
 * main method. This causes an {@link InterruptedException} within the call of
 * {@code sleep()} and is printed on the console as “Interrupted by exception!”.
 * Having logged the exception the thread does some busy waiting until the
 * interrupted flag on the thread is set. This again is set from the main
 * thread by calling {@code interrupt()} on the thread’s instance variable.
 * Overall we see the following output on the console:
 * <ol>
 * <li>[main] Sleeping in main thread for 5s...</li>
 * <li>[main] Interrupting myThread</li>
 * <li>[main] Sleeping in main thread for 5s...</li>
 * <li>[myThread] Interrupted by exception!</li>
 * <li>[main] Interrupting myThread</li>
 * <li>[myThread] Interrupted for the second time.</li>
 * </ol>
 *
 * What is interesting in this output, are the lines 3 and 4. If we go through
 * the code we might have expected that the string “Interrupted by exception!”
 * is printed out before the main thread starts sleeping again with “Sleeping
 * in main thread for 5s…”. But as you can see from the output, the scheduler
 * has executed the main thread before it started myThread again. Hence
 * myThread prints out the reception of the exception after the main thread has
 * started sleeping.
 *
 * @author Andrew Kearney
 */
public class InterruptExample implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(InterruptExample.class);

    @Override
    public void run() {
        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            log.warn("[{}] interrupted by exception", Thread.currentThread().getName());
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
