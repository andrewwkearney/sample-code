/*
 * Copyright 2020. Androsaces. All rights reserved.
 */

package com.androsaces.javaessentials.issue258;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class PrimitiveShuffleCollector {
    private static final Logger log = LoggerFactory.getLogger(PrimitiveShuffleCollector.class);

    private static void printRandom(int from,
                                    int upto,
                                    Supplier<Random> randomSupplier) {
        int[] shuffled = IntStream.range(from, upto)
            .boxed()
            .collect(ShuffleCollector.shuffle(randomSupplier))
            .limit(5)
            .mapToInt(Integer::intValue)
            .toArray();
        log.info("{}", shuffled);
    }

    public static void main(String[] args) {
        printRandom(0, 10, ThreadLocalRandom::current);
        printRandom(0, 10, () -> new Random(0));
    }
}
