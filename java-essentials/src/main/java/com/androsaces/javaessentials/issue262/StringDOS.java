/*
 * Copyright 2021. Androsaces. All rights reserved.
 */

package com.androsaces.javaessentials.issue262;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class StringDOS {
    private static final Logger log = LoggerFactory.getLogger(StringDOS.class);
    private static final String[] zeroHashCodes = {
            "ARbyguv", "ARbygvW", "ARbyhVv", "ARbyhWW",
            "ARbzHuv", "ARbzHvW", "ARbzIVv", "ARbzIWW",
            "ARcZguv", "ARcZgvW", "ARcZhVv", "ARcZhWW",
            "ASCyguv", "ASCygvW", "ASCyhVv", "ASCyhWW",
            "ASCzHuv", "ASCzHvW", "ASCzIVv", "ASCzIWW",
            "ASDZguv", "ASDZgvW", "ASDZhVv", "ASDZhWW",
    };

    public static void main(String... args) {
        for (int i = 5; i < 24; i++) {
            log.info("Testing with size {}", (1 << i));
            test(1 << i);
        }
    }

    private static void test(int size) {
        Map<String, Integer> map = new HashMap<>();
        long time = System.nanoTime();
        try {
            for (int i = 0; i < size; i++) {
                StringBuilder sb = new StringBuilder();
                for (int j = 1, index = 0; j <= i; j <<= 1, index++) {
                    if ((i & j) != 0)
                        sb.append(zeroHashCodes[index % zeroHashCodes.length]);
                }
                map.put(sb.toString(), i);
            }
        } finally {
            time = System.nanoTime() - time;
            log.info("creating time = {}ms", (time / 1000000));
        }
        log.info("map.size() = {}", map.size());
        for (String s : map.keySet()) {
            if (s.hashCode() != 0)
                throw new AssertionError("hashCode() of " + s + " is not 0");
        }
        log.info("All hashCode() were 0");
        String notInMap = zeroHashCodes[1] + zeroHashCodes[0];
        for (int repeats = 0; repeats < 10; repeats++) {
            testLookup(map, notInMap);
        }
    }

    private static void testLookup(Map<String, Integer> map, String notInMap) {
        long time = System.nanoTime();
        try {
            for (int i = 0; i < 1000 * 1000; i++) {
                map.get(notInMap);
            }
        } finally {
            time = System.nanoTime() - time;
            log.info("time = {}ms", (time / 1000000));
        }
    }
}
