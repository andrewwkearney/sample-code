/*
 * Copyright 2024. Androsaces. All rights reserved.
 */

package com.androsaces.javaessentials.issue281;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Vector;
import java.util.stream.IntStream;

/**
 * @author Andrew Kearney
 */
public class VectorBench {
    private static final Logger LOGGER = LoggerFactory.getLogger(VectorBench.class);

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            test(false);
            test(true);
        }
    }

    private static void test(boolean parallel) {
        IntStream range = IntStream.range(1, 1_000_000_000);
        if (parallel) range = range.parallel();
        long time = System.nanoTime();
        try {
            ThreadLocal<List<Integer>> lists = ThreadLocal.withInitial(() -> {
                List<Integer> result = new Vector<>();
                for (int i = 0; i < 1024; i++) result.add(i);
                return result;
            });
            range.map(i -> lists.get().get(i & 1023)).sum();
        } finally {
            time = System.nanoTime() - time;
            LOGGER.info("{}, {}ms", parallel ? "parallel":"sequential", (time / 1_000_000));
        }
    }
}
