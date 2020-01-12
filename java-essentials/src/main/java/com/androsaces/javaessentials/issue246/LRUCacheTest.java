/*
 * Copyright 2020. Androsaces. All rights reserved.
 */

package com.androsaces.javaessentials.issue246;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.stream.IntStream;

public class LRUCacheTest {
    private static final Logger log = LoggerFactory.getLogger(LRUCacheTest.class);

    public static void main(String[] args) {
        Map<Integer, String> cache = new LRUCache<>(5);
        IntStream.range(0, 10).forEach(i -> cache.put(i, "hi"));

        // entries 0-4 have already been removed
        // entries 5-9 are ordered
        log.info("cache: {}", cache);
        log.info("{}", cache.get(7));

        // entry 7 has moved to the end
        log.info("cache: {}", cache);
        IntStream.range(10, 14).forEach(i -> cache.put(i, "hi"));

        // entries 5,6,8,9 have been removed (eldest entries)
        // entry 7 is at the beginning now
        log.info("cache: {}", cache);
        cache.put(42, "meaning of life");

        // entry 7 is gone too
        log.info("cache: {}", cache);
    }
}
