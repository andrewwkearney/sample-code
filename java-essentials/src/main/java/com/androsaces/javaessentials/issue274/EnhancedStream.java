/*
 * Copyright 2023. Androsaces. All rights reserved.
 */

package com.androsaces.javaessentials.issue274;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class EnhancedStream<T> implements Stream<T> {
    private static final class Key<T> {
        private final T t;
        private final ToIntFunction<T> hashCode;
        private final BiPredicate<T, T> equals;

        public Key(T t, ToIntFunction<T> hashCode,
                   BiPredicate<T, T> equals) {
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
            Key<T> that = (Key) obj;
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
                t -> new Key<>(t, hashCode, equals),
                t -> t,
                merger,
                LinkedHashMap::new
        ))
                .values()
                .stream();
        return this;
    }

    @Override
    public EnhancedStream<T> filter(Predicate<? super T> predicate) {
        this.delegate = delegate.filter(predicate);
        return this;
    }

    @Override
    public <R> EnhancedStream<R> map(Function<? super T, ? extends R> mapper) {
        return new EnhancedStream<>(delegate.map(mapper));
    }

    @Override
    public IntStream mapToInt(ToIntFunction<? super T> mapper) {
        return delegate.mapToInt(mapper);
    }

    @Override
    public LongStream mapToLong(ToLongFunction<? super T> mapper) {
        return delegate.mapToLong(mapper);
    }

    @Override
    public DoubleStream mapToDouble(ToDoubleFunction<? super T> mapper) {
        return delegate.mapToDouble(mapper);
    }

    @Override
    public <R> EnhancedStream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper) {
        return new EnhancedStream<>(delegate.flatMap(mapper));
    }

    @Override
    public IntStream flatMapToInt(Function<? super T, ? extends IntStream> mapper) {
        return delegate.flatMapToInt(mapper);
    }

    @Override
    public LongStream flatMapToLong(Function<? super T, ? extends LongStream> mapper) {
        return delegate.flatMapToLong(mapper);
    }

    @Override
    public DoubleStream flatMapToDouble(Function<? super T, ? extends DoubleStream> mapper) {
        return delegate.flatMapToDouble(mapper);
    }

    @Override
    public EnhancedStream<T> distinct() {
        delegate = delegate.distinct();
        return this;
    }

    @Override
    public EnhancedStream<T> sorted() {
        delegate = delegate.sorted();
        return this;
    }

    @Override
    public EnhancedStream<T> sorted(Comparator<? super T> comparator) {
        delegate = delegate.sorted(comparator);
        return this;
    }

    @Override
    public EnhancedStream<T> peek(Consumer<? super T> action) {
        delegate = delegate.peek(action);
        return this;
    }

    @Override
    public EnhancedStream<T> limit(long maxSize) {
        delegate = delegate.limit(maxSize);
        return this;
    }

    @Override
    public EnhancedStream<T> skip(long n) {
        delegate = delegate.skip(n);
        return this;
    }

    @Override
    public EnhancedStream<T> takeWhile(Predicate<? super T> predicate) {
        delegate = delegate.takeWhile(predicate);
        return this;
    }

    @Override
    public EnhancedStream<T> dropWhile(
            Predicate<? super T> predicate) {
        delegate = delegate.dropWhile(predicate);
        return this;
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        delegate.forEach(action);
    }

    @Override
    public void forEachOrdered(Consumer<? super T> action) {
        delegate.forEachOrdered(action);
    }

    @Override
    public Object[] toArray() {
        return delegate.toArray();
    }

    @Override
    public <A> A[] toArray(IntFunction<A[]> generator) {
        return delegate.toArray(generator);
    }

    @Override
    public T reduce(T identity, BinaryOperator<T> accumulator) {
        return delegate.reduce(identity, accumulator);
    }

    @Override
    public Optional<T> reduce(BinaryOperator<T> accumulator) {
        return delegate.reduce(accumulator);
    }

    @Override
    public <V> V reduce(V identity, BiFunction<V, ? super T, V> accumulator, BinaryOperator<V> combiner) {
        return delegate.reduce(identity, accumulator, combiner);
    }

    @Override
    public <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator, BiConsumer<R, R> combiner) {
        return delegate.collect(supplier, accumulator, combiner);
    }

    @Override
    public <R, A> R collect(Collector<? super T, A, R> collector) {
        return delegate.collect(collector);
    }

    @Override
    public Optional<T> min(Comparator<? super T> comparator) {
        return delegate.min(comparator);
    }

    @Override
    public Optional<T> max(Comparator<? super T> comparator) {
        return delegate.max(comparator);
    }

    @Override
    public long count() {
        return delegate.count();
    }

    @Override
    public boolean anyMatch(Predicate<? super T> predicate) {
        return delegate.anyMatch(predicate);
    }

    @Override
    public boolean allMatch(Predicate<? super T> predicate) {
        return delegate.allMatch(predicate);
    }

    @Override
    public boolean noneMatch(Predicate<? super T> predicate) {
        return delegate.noneMatch(predicate);
    }

    @Override
    public Optional<T> findFirst() {
        return delegate.findFirst();
    }

    @Override
    public Optional<T> findAny() {
        return delegate.findAny();
    }

    @Override
    public Iterator<T> iterator() {
        return delegate.iterator();
    }

    @Override
    public Spliterator<T> spliterator() {
        return delegate.spliterator();
    }

    @Override
    public boolean isParallel() {
        return delegate.isParallel();
    }

    @Override
    public Stream<T> sequential() {
        return delegate.sequential();
    }

    @Override
    public Stream<T> parallel() {
        return delegate.parallel();
    }

    @Override
    public Stream<T> unordered() {
        return delegate.unordered();
    }

    @Override
    public Stream<T> onClose(Runnable closeHandler) {
        return delegate.onClose(closeHandler);
    }

    @Override
    public void close() {
        delegate.close();
    }

    public static <T> EnhancedStream<T> of(T t) {
        return new EnhancedStream<>(Stream.of(t));
    }

    @SafeVarargs
    @SuppressWarnings("varargs")
    // Creating a stream from an array is safe
    public static <T> EnhancedStream<T> of(T... values) {
        return new EnhancedStream<>(Arrays.stream(values));
    }
}
