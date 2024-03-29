/*
 * Copyright 2021. Androsaces. All rights reserved.
 */

package com.androsaces.javaessentials.issue268;

public class Programmer extends Person {
    private final short iq;

    public Programmer(String name, short iq) {
        super(name);
        if (iq < 120) {
            throw new IllegalArgumentException("Programmer IQ cannot be less than 120");
        }
        this.iq = iq;
    }

    @Override
    public int compareTo(Person that) {
        if (that instanceof Programmer) {
            Programmer p = (Programmer) that;
            int result = iq - p.iq;  // works because it's a short
            if (result != 0) return -result; // biggest IQ first
        }
        return super.compareTo(that);
    }

    @Override
    public String toString() {
        return super.toString() + " (" + iq + ")";
    }
}
