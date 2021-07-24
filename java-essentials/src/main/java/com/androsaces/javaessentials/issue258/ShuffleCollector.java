/*
 * Copyright 2020. Androsaces. All rights reserved.
 */

package com.androsaces.javaessentials.issue258;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import java.util.stream.*;

/**
 * By default we want to shuffle using ThreadLocalRandom, since it is by far the
 * fastest random number generator in the JDK. However, we might not know which
 * thread will end up calling the finishing lambda in the second shuffle() method,
 * so it is better to pass in a {@code Supplier&lt;Random&gt;}, rather than an
 * instance of the {@code ThreadLocalRandom}. Since Java 8, {@link ThreadLocalRandom}
 * is a Singleton and the seed is stored in Thread. It is seeded in the
 * {@code current()} method. We thus should never store an instance of
 * {@code ThreadLocalRandom} in a field or pass it to a method. It should never
 * escape from the methods in which we use it. We can store it in a local
 * variable, as long as no lambda captures this.
 */
public class ShuffleCollector {
    public static <T> Collector<T, ?, Stream<T>> shuffle() {
        return shuffle(ThreadLocalRandom::current);
    }

    public static <T> Collector<T, ?, Stream<T>> shuffle(Supplier<Random> random) {
        return Collectors.collectingAndThen(Collectors.toList(), ts -> {
            Collections.shuffle(ts, random.get());
            return ts.stream();
        });
    }
}
