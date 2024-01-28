/*
 * Copyright 2023. Androsaces. All rights reserved.
 */

package com.androsaces.javaessentials.issue235;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class MapBucketFillTest {
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;
    private static final Logger log = LoggerFactory.getLogger(MapBucketFillTest.class);

    public static void main(String[] args) throws Exception {
        for (Pixel.HASH_ALGO algo : Pixel.HASH_ALGO.values()) {
            testWith(algo);
        }
    }

    private static void testWith(Pixel.HASH_ALGO algo) throws NoSuchFieldException, IllegalAccessException {
        log.info("Testing with {}", algo);
        Pixel.setAlgo(algo);
        Map<Pixel, Integer> pixels = new HashMap<>();
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                pixels.put(new Pixel(x, y), 33);
            }
        }

        log.info("Hash clash print");
        Map<Integer, Integer> bucketClashes = MapClashInspector.getHashClashDistribution(pixels);
        printClashes(bucketClashes);
    }

    private static void printClashes(Map<Integer, Integer> clashes) {
        if (isPerfect(clashes)) log.info("Perfect!!!");
        for (Map.Entry<Integer, Integer> e : clashes.entrySet()) {
            log.info("{}: {}", e.getKey(), e.getValue());
        }
    }

    private static boolean isPerfect(Map<Integer, Integer> clashes) {
        Integer n = clashes.get(1);
        return n != null && n == WIDTH * HEIGHT;
    }
}
