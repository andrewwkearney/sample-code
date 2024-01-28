/*
 * Copyright 2022. Androsaces. All rights reserved.
 */

package com.androsaces.javaessentials;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;

public class ForkJoinPuzzle {
    private static final Logger log = LoggerFactory.getLogger(ForkJoinPuzzle.class);

    public static void main(String[] args) {
        log.info("Forkjoin pool size: {}", ForkJoinPool.getCommonPoolParallelism());
        parallelStream().forEach(val -> process());
    }

    private static void process() {
        try {
            String processor = Thread.currentThread().getName();
            log.info("Processing: {}", processor);
            Runnable updateTask = () -> {
                parallelStream().forEach(value -> {
                    log.info("Updating: {} {}", Thread.currentThread().getName(), ForkJoinPool.commonPool());
                });
            };

            Thread thread = new Thread(updateTask, "Worker for " + processor);
            thread.start();
            log.info("Waiting: {}", processor);
            thread.join();
        } catch (InterruptedException e) {
            log.warn("Error", e);
            Thread.currentThread().interrupt();
        }
    }

    private static IntStream parallelStream() {
        return IntStream.range(0, Runtime.getRuntime().availableProcessors())
                .parallel();
    }
}
