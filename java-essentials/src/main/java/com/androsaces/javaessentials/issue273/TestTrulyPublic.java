/*
 * Copyright 2023. Androsaces. All rights reserved.
 */

package com.androsaces.javaessentials.issue273;

import com.androsaces.javaessentials.issue273.inner.Hidden;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

public class TestTrulyPublic {
    private static final Logger log = LoggerFactory.getLogger(TestTrulyPublic.class);

    public static void main(String... args) throws Exception {
        log.info("Testing private inner class");
        test(Hidden.getPrivateInnerClass());

        log.info("Testing method inner class");
        test(Hidden.getMethodClass());

        log.info("Testing lambda");
        test(Hidden.getLambda());
    }

    private static void test(A a) {
        Reflections.getTrulyPublicMethods(a.getClass()).forEach(
                m -> log.info("{}", m));
        printMethodResult(a, "foo");
        printMethodResult(a, "bar");
    }

    private static void printMethodResult(Object o, String methodName) {
        Optional<Method> method = Reflections.getTrulyPublicMethod(o.getClass(), methodName);
        method.map(m -> {
            try {
                log.info("m = {}", m);
                return m.invoke(o);
            } catch (IllegalAccessException e) {
                throw new IllegalStateException(e);
            } catch (InvocationTargetException e) {
                throw new IllegalStateException(e.getCause());
            }
        }).ifPresentOrElse(System.out::println,
                () -> log.info("Method {}() not found", methodName));
    }
}