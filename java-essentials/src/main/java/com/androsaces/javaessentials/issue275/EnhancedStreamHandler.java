/*
 * Copyright 2023. Androsaces. All rights reserved.
 */

package com.androsaces.javaessentials.issue275;

import java.lang.reflect.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class EnhancedStreamHandler<T> implements InvocationHandler {
    private Stream<T> delegate;

    public EnhancedStreamHandler(Stream<T> delegate) {
        this.delegate = delegate;
    }

    private static final Method enhancedDistinct;

    static {
        try {
            enhancedDistinct = EnhancedStream.class.getMethod(
                    "distinct", ToIntFunction.class, BiPredicate.class,
                    BinaryOperator.class
            );
        } catch (NoSuchMethodException e) {
            throw new Error(e);
        }
    }

    private static final Map<Method, Method> methodMap = Stream.of(EnhancedStream.class.getMethods())
            .filter(m -> !m.equals(enhancedDistinct))
            .filter(m -> !Modifier.isStatic(m.getModifiers()))
            .collect(Collectors.toUnmodifiableMap(
                    Function.identity(),
                    m -> {
                        try {
                            return Stream.class.getMethod(
                                    m.getName(), m.getParameterTypes()
                            );
                        } catch (NoSuchMethodException e) {
                            throw new Error(e);
                        }
                    }
            ));

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.equals(enhancedDistinct)) {
            return distinct(
                    (EnhancedStream<T>) proxy,
                    (ToIntFunction<T>) args[0],
                    (BiPredicate<T, T>) args[1],
                    (BinaryOperator<T>) args[2]);
        } else if (method.getReturnType() == EnhancedStream.class) {
            Method match = methodMap.get(method);
            this.delegate = (Stream<T>) match.invoke(delegate, args);
            return proxy;
        } else {
            return method.invoke(this.delegate, args);
        }
    }

    private EnhancedStream<T> distinct(EnhancedStream<T> proxy,
                                       ToIntFunction<T> hashCode,
                                       BiPredicate<T, T> equals,
                                       BinaryOperator<T> merger) {
        delegate = delegate.collect(Collectors.toMap(
                        t -> new Key<>(t, hashCode, equals),
                        Function.identity(),
                        merger,
                        LinkedHashMap::new))
                .values()
                .stream();
        return proxy;
    }

    private static final class Key<E> {
        private final E e;
        private final ToIntFunction<E> hashCode;
        private final BiPredicate<E, E> equals;

        public Key(E e,
                   ToIntFunction<E> hashCode,
                   BiPredicate<E, E> equals) {
            this.e = e;
            this.hashCode = hashCode;
            this.equals = equals;
        }

        @Override
        public int hashCode() {
            return hashCode.applyAsInt(e);
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Key<?>)) return false;
            @SuppressWarnings("unchecked")
            Key<E> that = (Key<E>) obj;
            return equals.test(this.e, that.e);
        }
    }
}
