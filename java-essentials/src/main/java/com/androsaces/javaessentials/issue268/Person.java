/*
 * Copyright 2021. Androsaces. All rights reserved.
 */

package com.androsaces.javaessentials.issue268;

public class Person implements Comparable<Person> {
    private final String name;

    public Person(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(Person that) {
        return name.compareTo(that.name);
    }

    @Override
    public String toString() {
        return name;
    }
}
