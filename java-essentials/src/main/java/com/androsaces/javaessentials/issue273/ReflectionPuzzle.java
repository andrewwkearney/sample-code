/*
 * Copyright 2023. Androsaces. All rights reserved.
 */

package com.androsaces.javaessentials.issue273;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.*;

public class ReflectionPuzzle {
    private static final Logger log = LoggerFactory.getLogger(ReflectionPuzzle.class);

    public static void main(String... args) {
        Collection<String> names = new ArrayList<>();
        Collections.addAll(names, "Goetz", "Marks", "Rose");
        printSize(names);
        printSize(Arrays.asList("Goetz", "Marks", "Rose"));
        printSize(List.of("Goetz", "Marks", "Rose"));
        printSize(Collections.unmodifiableCollection(names));
    }

    private static void printSize(Collection<?> col) {
        log.info("Size of {}", col.getClass().getName());
        try {
            Method sizeMethod = col.getClass().getMethod("size");
            log.info("{}", sizeMethod.invoke(col));
        } catch (ReflectiveOperationException e) {
            log.warn("{}", e.getMessage(), e);
        }
    }
}