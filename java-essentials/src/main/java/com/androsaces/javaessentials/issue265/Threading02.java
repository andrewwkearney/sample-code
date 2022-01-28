/*
 * Copyright 2021. Androsaces. All rights reserved.
 */

package com.androsaces.javaessentials.issue265;

public class Threading02 {
    private static final Object LOCK = new Object();
    private static Object myObj = null;

    public static Object retrieve() {
        if (myObj == null) {
            synchronized (LOCK) {
                myObj = create();
            }
        }
        return myObj;
    }

    private static Object create() {
        return new Object();
    }

    public static void main(String[] args) {
        Threading02.retrieve();
    }
}
