/*
 * Copyright 2019. Androsaces. All rights reserved.
 */

package com.androsaces.functional.concurrency.lessonthree.executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Andrew Kearney
 */
public class MapComparison implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(MapComparison.class);
    private static Map<Integer, String> mMap;
    private Random mRandom = new Random(System.currentTimeMillis());

    public static void main(String[] args) throws InterruptedException {
        runPerformanceTest(new Hashtable<>());
        runPerformanceTest(Collections.synchronizedMap(new Hashtable<>()));
        runPerformanceTest(new ConcurrentHashMap<>());
        runPerformanceTest(new ConcurrentSkipListMap<>());
    }

    private static void runPerformanceTest(Map<Integer, String> map) throws InterruptedException {
        MapComparison.mMap = map;
        fillMap(mMap);
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        long startMillis = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            executorService.execute(new MapComparison());
        }
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);
        long endMillis = System.currentTimeMillis();
        log.info("{} took {}ms", mMap.getClass().getSimpleName(), endMillis - startMillis);
    }

    private static void fillMap(Map<Integer, String> map) {
        for (int i = 0; i < 1000; i++) {
            map.put(i, String.valueOf(i));
        }
    }

    @Override
    public void run() {
        for (int i = 0; i < 100000; i++) {
            int randomInt = mRandom.nextInt(100);
            mMap.get(randomInt);
            randomInt = mRandom.nextInt(100);
            mMap.put(randomInt, String.valueOf(randomInt));
        }
    }
}
