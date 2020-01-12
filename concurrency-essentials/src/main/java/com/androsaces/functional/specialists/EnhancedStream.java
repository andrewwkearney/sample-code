/*
 * Copyright 2019. Androsaces. All rights reserved.
 */

package com.androsaces.functional.specialists;

import java.util.LinkedHashMap;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * @author Andrew Kearney
 */
public class EnhancedStream<T> implements Stream<T> {
    private static final class Key<T> {
        private final T t;
        private final ToIntFunction<T> hashCode;
        private final BiPredicate<T, T> equals;

        public Key(T t, ToIntFunction<T> hashCode, BiPredicate<T, T> equals) {
            this.t = t;
            this.hashCode = hashCode;
            this.equals = equals;
        }

        @Override
        public int hashCode() {
            return hashCode.applyAsInt(t);
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Key)) return false;
            @SuppressWarnings("unchecked")
            Key<T> that = (Key<T>) obj;
            return equals.test(this.t, that.t);
        }
    }

    private Stream<T> delegate;

    public EnhancedStream(Stream<T> delegate) {
        this.delegate = delegate;
    }

    public EnhancedStream<T> distinct(ToIntFunction<T> hashCode,
                                      BiPredicate<T, T> equals,
                                      BinaryOperator<T> merger) {
        delegate = collect(Collectors.toMap(
                t -> new Key(t, hashCode, equals), t -> t, merger, LinkedHashMap::new))
                .values()
                .stream();
        return this;
    }

    public EnhancedStream<T> filter(Predicate<? super T> predicate) {
        this.delegate = delegate.filter(predicate);
        return this;
    }

    public <R> EnhancedStream<R> map(Function<? super T, ? extends R> mapper) {
        return new EnhancedStream<>(delegate.map(mapper));
    }

    public IntStream mapToInt(ToIntFunction<? super T> mapper) {
        return delegate.mapToInt(mapper);
    }

    public LongStream mapToLong(ToLongFunction<? super T> mapper) {
        return delegate.mapToLong(mapper);
    }

    public DoubleStream mapToDouble(ToDoubleFunction<? super T> mapper) {
        return delegate.mapToDouble(mapper);
    }

    public <R> EnhancedStream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper) {
        return new EnhancedStream<>(delegate.flatMap(mapper));
    }
}
