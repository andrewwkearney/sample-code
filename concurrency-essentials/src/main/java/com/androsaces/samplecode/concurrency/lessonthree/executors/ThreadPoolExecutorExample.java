/*
 * Copyright 2019. Androsaces. All rights reserved.
 */

package com.androsaces.samplecode.concurrency.lessonthree.executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Under the hood the {@link ThreadPoolExecutor} maintains a pool of threads and
 * dispatches the instances of Runnable given the {@code execute()} method to
 * the pool. The arguments passed to the constructor control the behavior of the
 * thread pool. The constructor with the most arguments is the following one:
 *
 * {@link ThreadPoolExecutor#ThreadPoolExecutor(int, int, long, TimeUnit, BlockingQueue, ThreadFactory, RejectedExecutionHandler)}
 *
 * Let’s go through the different arguments step by step:
 * <ul>
 * <li><b>corePoolSize:</b> The {@code ThreadPoolExecutor} has an attribute
 *     {@code corePoolSize} that determines how many threads it will start until
 *     new threads are only started when the queue is full.</li>
 * <li><b>maximumPoolSize:</b> This attribute determines how many threads are
 *     started at the maximum. You can set this to {@link Integer#MAX_VALUE} in
 *     order to have no upper boundary.</li>
 * <li><b>keepAliveTime:</b> When the {@code ThreadPoolExecutor} has created
 *     more than {@code corePoolSize} threads, a thread will be removed from the
 *     pool when it idles for the given amount of time.</li>
 * <li><b>unit:</b> This is just the {@code TimeUnit} for the keepAliveTime.</li>
 * <li><b>workQueue:</b> This queue holds the instances of Runnable given
 *     through the {@code execute()} method until they are actually started.</li>
 * <li><b>threadFactory:</b> An implementation of this interface gives you
 *     control over the creation of the threads used by the ThreadPoolExecutor.</li>
 * <li><b>handler:</b> When you specify a fixed size for the workQueue and
 *     provide a maximumPoolSize then it may happen, that the ThreadPoolExecutor
 *     is not able to execute your Runnable instance due to saturation. In this
 *     case the provided handler is called and gives you control over what
 *     should happen in this case.</li>
 * </ul>
 *
 * @author Andrew Kearney
 */
public class ThreadPoolExecutorExample implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(ThreadPoolExecutorExample.class);

    private static AtomicInteger mCounter = new AtomicInteger();
    private final int mTaskId;

    private ThreadPoolExecutorExample(int taskId) {
        mTaskId = taskId;
    }

    private int getTaskId() {
        return mTaskId;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            log.info("interrupted thread, due to: {}", e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(10);
        ThreadFactory threadFactory = r -> {
            int currentCount = mCounter.getAndIncrement();
            log.info("creating new thread: {}", currentCount);
            return new Thread(r, "myThread-" + currentCount);
        };
        RejectedExecutionHandler rejectedHandler = (r, executor) -> {
            if (r instanceof ThreadPoolExecutorExample) {
                ThreadPoolExecutorExample example = (ThreadPoolExecutorExample) r;
                log.info("rejecting task with id {}", example.getTaskId());
            }
        };
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 1, TimeUnit.SECONDS, queue, threadFactory, rejectedHandler);
        for (int i = 0; i < 1000; i++) {
            executor.execute(new ThreadPoolExecutorExample(i));
        }
        executor.shutdown();
    }
}
