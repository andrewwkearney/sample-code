/*
 * Copyright 2023. Androsaces. All rights reserved.
 */

package com.androsaces.javaessentials.issue274;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.stream.*;

public class MethodDistinctify {
    private static final Logger log = LoggerFactory.getLogger(MethodDistinctify.class);

    public static void main(String... args) {
        log.info("Normal ArrayDeque clone() Methods:");
        EnhancedStream.of(ArrayDeque.class.getMethods())
                .filter(method -> method.getName().equals("clone"))
                .forEach(MethodDistinctify::print);

        log.info("Distinct ArrayDeque:");
        EnhancedStream.of(ArrayDeque.class.getMethods())
                .filter(method -> method.getName().equals("clone"))
                .distinct(HASH_CODE, EQUALS, MERGE)
                .forEach(MethodDistinctify::print);

        log.info("Normal ConcurrentSkipListSet:");
        EnhancedStream.of(ConcurrentSkipListSet.class.getMethods())
                .filter(method -> method.getName().contains("Set"))
                .sorted(METHOD_COMPARATOR)
                .forEach(MethodDistinctify::print);

        log.info("Distinct ConcurrentSkipListSet:");
        EnhancedStream.of(ConcurrentSkipListSet.class.getMethods())
                .filter(method -> method.getName().contains("Set"))
                .distinct(HASH_CODE, EQUALS, MERGE)
                .sorted(METHOD_COMPARATOR)
                .forEach(MethodDistinctify::print);
    }

    private static void print(Method m) {
        log.info(
                Stream.of(m.getParameterTypes())
                        .map(Class::getSimpleName)
                        .collect(Collectors.joining(
                                ", ",
                                "  " + m.getReturnType().getSimpleName()
                                        + " " + m.getName() + "(",
                                ")"))
        );
    }

    public static final ToIntFunction<Method> HASH_CODE = method -> method.getName().hashCode() + method.getParameterCount();

    public static final BiPredicate<Method, Method> EQUALS =
            (method1, method2) ->
                    method1.getName().equals(method2.getName()) &&
                            method1.getParameterCount() ==
                                    method2.getParameterCount() &&
                            Arrays.equals(method1.getParameterTypes(),
                                    method2.getParameterTypes());
    public static final BinaryOperator<Method> MERGE =
            (method1, method2) -> {
                if (method1.getReturnType()
                        .isAssignableFrom(method2.getReturnType()))
                    return method2;
                if (method2.getReturnType()
                        .isAssignableFrom(method1.getReturnType()))
                    return method1;
                throw new IllegalArgumentException(
                        "Conflicting return types " +
                                method1.getReturnType().getCanonicalName() +
                                " and " +
                                method2.getReturnType().getCanonicalName());
            };
    public static final Comparator<Method> METHOD_COMPARATOR =
            Comparator.comparing(Method::getName)
                    .thenComparing(method ->
                            Arrays.toString(method.getParameterTypes()));
}
