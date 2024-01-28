/*
 * Copyright 2023. Androsaces. All rights reserved.
 */

package com.androsaces.javaessentials.issue307;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AccessFlagDemo {
    private static final Logger log = LoggerFactory.getLogger(AccessFlagDemo.class);

    public static void main(String... args) {
        Arrays.stream(MethodsDemo.class.getDeclaredMethods())
                .sorted(Comparator.comparing(Method::getName))
                .forEach(method -> log.info("{}: \n\tMethod: {}\n\tModifiers: {}\n\tModifiers Hex: {}",
                        method.getName(),
                        method,
                        Modifier.toString(method.getModifiers()),
                        hexValues(method.getModifiers())));
    }

    private static String hexValues(int modifiers) {
        int bit = 1;
        List<Integer> values = new ArrayList<>();
        while (modifiers != 0) {
            if ((modifiers & bit) != 0) values.add(bit);
            modifiers = modifiers & ~bit;
            bit <<= 1;
        }
        return values.stream()
                .map(val -> String.format("0x%04x", val))
                .collect(Collectors.joining(" ", "[", "]"));
    }
}
