/*
 * Copyright 2019. Androsaces. All rights reserved.
 */

package com.androsaces.functional.concurrency.lessonthree.executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author Andrew Kearney
 */
public class ExecutorsExample implements Callable<Integer> {
    private static final Logger log = LoggerFactory.getLogger(ExecutorsExample.class);

    private static Random mRandom = new Random(System.currentTimeMillis());

    @Override
    public Integer call() throws Exception {
        Thread.sleep(1000L);
        return mRandom.nextInt(100);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        List<Future<Integer>> futures = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            futures.add(executorService.submit(new ExecutorsExample()));
        }
        for (Future<Integer> future : futures) {
            Integer retVal = future.get();
            log.info("{}", retVal);
        }
        executorService.shutdown();
    }
}
