/*
 * Copyright 2019. Androsaces. All rights reserved.
 */

package com.androsaces.functional.specialists;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * @author Andrew Kearney
 */
public class EnhancedStream<T> implements Stream<T> {
    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public Spliterator<T> spliterator() {
        return null;
    }

    @Override
    public boolean isParallel() {
        return false;
    }

    @Override
    public Stream<T> sequential() {
        return null;
    }

    @Override
    public Stream<T> parallel() {
        return null;
    }

    @Override
    public Stream<T> unordered() {
        return null;
    }

    @Override
    public Stream<T> onClose(Runnable closeHandler) {
        return null;
    }

    @Override
    public void close() {

    }

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

    @Override
    public IntStream flatMapToInt(Function<? super T, ? extends IntStream> mapper) {
        return null;
    }

    @Override
    public LongStream flatMapToLong(Function<? super T, ? extends LongStream> mapper) {
        return null;
    }

    @Override
    public DoubleStream flatMapToDouble(Function<? super T, ? extends DoubleStream> mapper) {
        return null;
    }

    @Override
    public Stream<T> distinct() {
        return null;
    }

    @Override
    public Stream<T> sorted() {
        return null;
    }

    @Override
    public Stream<T> sorted(Comparator<? super T> comparator) {
        return null;
    }

    @Override
    public Stream<T> peek(Consumer<? super T> action) {
        return null;
    }

    @Override
    public Stream<T> limit(long maxSize) {
        return null;
    }

    @Override
    public Stream<T> skip(long n) {
        return null;
    }

    @Override
    public void forEach(Consumer<? super T> action) {

    }

    @Override
    public void forEachOrdered(Consumer<? super T> action) {

    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <A> A[] toArray(IntFunction<A[]> generator) {
        return null;
    }

    @Override
    public T reduce(T identity, BinaryOperator<T> accumulator) {
        return null;
    }

    @Override
    public Optional<T> reduce(BinaryOperator<T> accumulator) {
        return Optional.empty();
    }

    @Override
    public <U> U reduce(U identity, BiFunction<U, ? super T, U> accumulator, BinaryOperator<U> combiner) {
        return null;
    }

    @Override
    public <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator, BiConsumer<R, R> combiner) {
        return null;
    }

    @Override
    public <R, A> R collect(Collector<? super T, A, R> collector) {
        return null;
    }

    @Override
    public Optional<T> min(Comparator<? super T> comparator) {
        return Optional.empty();
    }

    @Override
    public Optional<T> max(Comparator<? super T> comparator) {
        return Optional.empty();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public boolean anyMatch(Predicate<? super T> predicate) {
        return false;
    }

    @Override
    public boolean allMatch(Predicate<? super T> predicate) {
        return false;
    }

    @Override
    public boolean noneMatch(Predicate<? super T> predicate) {
        return false;
    }

    @Override
    public Optional<T> findFirst() {
        return Optional.empty();
    }

    @Override
    public Optional<T> findAny() {
        return Optional.empty();
    }
}
