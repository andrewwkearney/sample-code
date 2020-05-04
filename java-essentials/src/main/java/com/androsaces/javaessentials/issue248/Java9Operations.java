/*
 * Copyright 2020. Androsaces. All rights reserved.
 */

package com.androsaces.javaessentials.issue248;

import java.util.List;

/**
 * @author Andrew Kearney
 */
public class Java9Operations {
    public void ListOperations() {
        List<Integer> list0 = List.of(); // List0
        List<Integer> list1 = List.of(42); // List1
        List<Integer> list2 = List.of(42, 57); // List2
        List<Integer> list3 = List.of(42, 57, 1); // ListN
        List<Integer> list4 = List.of(42, 57, 1, 2); // ListN
    }
}
