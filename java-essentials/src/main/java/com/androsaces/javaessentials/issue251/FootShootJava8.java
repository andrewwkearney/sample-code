/*
 * Copyright 2020. Androsaces. All rights reserved.
 */

package com.androsaces.javaessentials.issue251;

import java.util.*;

import static java.util.stream.Collectors.*;

@SuppressWarnings({"rawtypes", "UnnecessaryLocalVariable", "unchecked", "SimplifyStreamApiCallChains"})
public class FootShootJava8 {
    public static void main(String... args) {
        List<String> names = new ArrayList<>();
        Collections.addAll(names, "John", "Anton", "Heinz");
        List huh = names;
        List<Integer> numbers = huh;
        numbers.add(42);
        System.out.println(names.stream().collect(joining("+")));
    }
}
