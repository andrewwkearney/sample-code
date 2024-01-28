/*
 * Copyright 2023. Androsaces. All rights reserved.
 */

package com.androsaces.javaessentials.issue274;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.ToIntFunction;

public class BeachDistinctify {
    private static final Logger log = LoggerFactory.getLogger(BeachDistinctify.class);

    public static void main(String[] args) {
        EnhancedStream.of("Kalathas", "Stavros", "STAVROS", "marathi", "kalathas", "baLos", "Balos")
                .distinct(HASH_CODE, EQUALS, MERGE)
                .sorted(String.CASE_INSENSITIVE_ORDER)
                .forEach(l -> log.info("{}", l));
    }

    public static final ToIntFunction<String> HASH_CODE = s -> s.toUpperCase().hashCode();
    public static final BiPredicate<String, String> EQUALS = String::equalsIgnoreCase;
    public static final BinaryOperator<String> MERGE = (s1, s2) -> s1.chars().sum() < s2.chars().sum() ? s2 : s1;
}
