/*
 * Copyright 2023. Androsaces. All rights reserved.
 */

package com.androsaces.javaessentials.issue307;

public class MethodsDemo implements Comparable<MethodsDemo> {
    private static final synchronized strictfp void foo() {
    }

    void varArgsMethod(Object... args) {
    }

    // bridge (volatile) & synthetic
    public int compareTo(MethodsDemo o1) {
        return 0;
    }
}
