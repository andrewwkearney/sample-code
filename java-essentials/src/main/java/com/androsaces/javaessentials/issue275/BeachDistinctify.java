/*
 * Copyright 2023. Androsaces. All rights reserved.
 */

package com.androsaces.javaessentials.issue275;

import java.util.function.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeachDistinctify {
    private static final Logger log = LoggerFactory.getLogger(BeachDistinctify.class);

    public static void main(String... args) {
        EnhancedStream.of("Kalathas", "Stavros", "STAVROS",
                        "marathi", "kalathas", "baLos", "Balos")
                .distinct(HASH_CODE, EQUALS, MERGE)
                .forEach(s -> log.info("{}", s));
    }

    // case insensitive hashCode() and equals()
    public static final ToIntFunction<String> HASH_CODE = s -> s.toUpperCase().hashCode();
    public static final BiPredicate<String, String> EQUALS = String::equalsIgnoreCase;
    // keep the string with the highest total ascii value
    public static final BinaryOperator<String> MERGE = (s1, s2) -> s1.chars().sum() < s2.chars().sum() ? s2 : s1;
}
