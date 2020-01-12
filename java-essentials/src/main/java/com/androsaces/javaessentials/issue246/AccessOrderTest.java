/*
 * Copyright 2020. Androsaces. All rights reserved.
 */

package com.androsaces.javaessentials.issue246;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class AccessOrderTest {
    private static final Logger log = LoggerFactory.getLogger(AccessOrderTest.class);
    public static void main(String[] args) {
        test(new LinkedHashMap<>()); // insertion order
        test(new TreeMap<>()); // sorted order
        test(new HashMap<>()); // undefined order
    }

    private static void test(Map<Integer, String> map) {
        log.info(map.getClass().getSimpleName());
        map.put(42, "Meaning");
        map.put(7, "Days Per Week");
        map.put(86400, "Seconds");
        map.put(1, "Highlander");
        log.info("map = {}", map);
        log.info("map.get(7) = {}", map.get(7));
        log.info("map = {}", map);
    }
}
