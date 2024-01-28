/*
 * Copyright 2023. Androsaces. All rights reserved.
 */

package com.androsaces.javaessentials.issue273.inner;

import com.androsaces.javaessentials.issue273.A;
import com.androsaces.javaessentials.issue273.B;

public class Hidden {
    public static A getPrivateInnerClass() {
        return new C();
    }

    private static class C implements A, B {
        public String foo() {
            return "Hello World";
        }

        public String bar() {
            return "Should not be visible";
        }
    }

    public static A getMethodClass() {
        class D implements A {
            public CharSequence foo() {
                return "inside method";
            }
        }
        return new D();
    }

    public static A getLambda() {
        return () -> "Hello Lambert";
    }
}