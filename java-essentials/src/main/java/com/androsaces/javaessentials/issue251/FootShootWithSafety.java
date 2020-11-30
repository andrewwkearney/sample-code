/*
 * Copyright 2020. Androsaces. All rights reserved.
 */

package com.androsaces.javaessentials.issue251;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings({"rawtypes", "UnnecessaryLocalVariable", "unchecked", "SimplifyStreamApiCallChains"})
public class FootShootWithSafety {
    public static void main(String... args) {
        List<String> names = Collections.checkedList(new ArrayList<>(), String.class);
        Collections.addAll(names, "John", "Anton", "Heinz");
        List huh = names;
        List<Integer> numbers = huh;
        numbers.add(42);
        System.out.println(names.stream().collect(Collectors.joining("+")));
    }
}
