/*
 * Copyright 2021. Androsaces. All rights reserved.
 */

package com.androsaces.javaessentials.issue254;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public class MethodLookupCost {
    private static final Logger log = LoggerFactory.getLogger(MethodLookupCost.class);

    public static void main(String[] args) throws Exception {
        log.info("warmup");
        for (int i = 0; i < 2; i++) {
            test();
        }

        log.info("");
        log.info("proper run");
        for (int i = 0; i < 3; i++) {
            test();
        }
        log.info("{}", blackhole);
    }

    private static void test() throws Exception {
        for (int methods = 4; methods < 65536; methods *= 5) {
            test(Class.forName("Methods" + methods));
        }
    }

    private volatile static Method blackhole;

    private static void test(Class clazz) throws Exception {
        log.info("{}", clazz);
        log.info("{}", clazz.toString().replaceAll(".", "-"));
        Method[] methods = clazz.getMethods();
        int firstIndex = fooIndex(methods, 0, 1);
        Method first = methods[firstIndex];
        int lastIndex = fooIndex(methods, methods.length - 1, -1);
        Method last = methods[lastIndex];
        int middleStart = (lastIndex - firstIndex) / 2;
        int middleIndex = fooIndex(methods, middleStart, 1);
        Method middle = methods[middleIndex];
        System.out.println("indexes = (" + firstIndex + ", " +
                middleIndex + ", " + lastIndex + ")");
        System.out.println("foos = (" + first.getName() + ", " +
                middle.getName() + ", " + last.getName() + ")");
        System.out.println("  first  = " + methodFinds(first));
        System.out.println("  middle = " + methodFinds(middle));
        System.out.println("  last   = " + methodFinds(last));
        System.out.println();
    }

    private static int fooIndex(Method[] methods,
                                int start, int inc) {
        int idx = start;
        while (!methods[idx].getName().startsWith("foo")) idx += inc;
        return idx;
    }

    private static long methodFinds(Method method)
            throws Exception {
        return methodFinds(method.getDeclaringClass(),
                method.getName());
    }

    /**
     * Counts how many times we can get the method in a second.
     */
    private static long methodFinds(Class clazz, String method)
            throws Exception {
        long time = System.currentTimeMillis();
        long methodFinds = 0;
        while (System.currentTimeMillis() - time < 1000) {
            blackhole = clazz.getMethod(method, (Class[]) null);
            methodFinds++;
        }
        return methodFinds;
    }
}
