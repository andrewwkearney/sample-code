/**
 * <h1>ShuffleCollector</h1>
 * <h2>Sourced from the Java&trade; Specialist's Newsletter</h2>
 * <h3>Issue 258</h3>
 * Abstract:
 * Sorting a stream is easy. But what if we want the opposite: shuffling? We can
 * shuffle a List with {@link java.util.Collections#shuffle(java.util.List)}. But
 * how can we apply that to a Stream? In this newsletter we show how with
 * {@link java.util.stream.Collectors#collectingAndThen(java.util.stream.Collector, java.util.function.Function)}.
 */
package com.androsaces.javaessentials.issue258;