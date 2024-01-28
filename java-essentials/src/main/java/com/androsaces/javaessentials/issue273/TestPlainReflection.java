/*
 * Copyright 2023. Androsaces. All rights reserved.
 */

package com.androsaces.javaessentials.issue273;

import com.androsaces.javaessentials.issue273.inner.Hidden;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.stream.Stream;

public class TestPlainReflection {
    private static final Logger log = LoggerFactory.getLogger(TestPlainReflection.class);

    public static void main(String... args) {
        log.info("Testing private inner class");
        test(Hidden.getPrivateInnerClass());

        log.info("Testing method inner class");
        test(Hidden.getMethodClass());

        log.info("Testing lambda");
        test(Hidden.getLambda());
    }

    private static void test(A a) {
        Stream.of(a.getClass().getMethods())
                .forEach(s -> log.info("{}", s));
        printMethodResult(a, "foo");
        printMethodResult(a, "bar");
    }

    private static void printMethodResult(Object o, String name) {
        try {
            Method method = o.getClass().getMethod(name);
            log.info("{}", method.invoke(o));
        } catch (NoSuchMethodException e) {
            log.warn("Method {}() not found", name);
        } catch (IllegalAccessException e) {
            log.warn("Illegal to call {}()", name);
        } catch (InvocationTargetException e) {
            throw new IllegalStateException(e.getCause());
        }
    }
}