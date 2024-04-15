/*
 * Copyright 2024. Androsaces. All rights reserved.
 */

package com.androsaces.javaessentials.issue283;

import java.util.*;

/**
 * @author Andrew Kearney
 */
public class ListSurprise {
    public static void main(String[] args) {
        // Make ListSurprise print 3.14159
        System.setSecurityManager(new SecurityManager());
        List<Integer> numbers = new ArrayList<>();
        Collections.addAll(numbers, 3, 1, 4, 1, 5, 5, 9);
        Iterator<Integer> it = numbers.iterator();
        System.out.print(it.next()); // 3
        System.out.print('.');
        System.out.print(it.next()); // 1
        System.out.print(it.next()); // 4
        System.out.print(it.next()); // 1
        System.out.print(it.next()); // 5
        doSomethingNumbers(numbers);
        System.out.print(it.next()); // 9
        if (!numbers.equals(List.of(3, 1, 4, 1, 5, 9))) {
            throw new AssertionError();
        }
    }

    /**
     * The {@code modCount} field in {@link AbstractList} helps to discover
     * concurrent updates to a list. Whenever the list is changed, the
     * {@code modCount} is also incremented. When the {@code Iterator} is created,
     * it makes a copy of the current {@code modCount}. If this changes during
     * iteration, then the backing list must have changed. However, if we remove
     * an item with the iterator itself, then the iterator's {@code expectedModCount}
     * is also changed to match the list's modCount. The trick is thus to remove
     * the element at index 5, and to then change the list another 4294967295 times,
     * thus looping the int back to the starting point.
     */
//    private static void doSomethingNumbers(List<Integer> list) {
//        // Remove element at index 5 and modify list 4 billion times
//        list.remove(5);
//        for (int i = Integer.MIN_VALUE; i < Integer.MAX_VALUE; i++) {
//            ((ArrayList<Integer>) list).trimToSize();
//        }
//    }

    /**
     * The {@link ArrayList#trimToSize()} method increments the modCount even if
     * it does not really change the structure of the list. It is thus a fairly
     * quick way of spinning the modCount back to its original value.
     */
//    private static void doSomethingNumbers(List<Integer> list) {
//        list.set(5, 9);
//        Phaser phaser = new Phaser(2);
//        Thread main = Thread.currentThread();
//        new Thread(() -> {
//            synchronized (System.out) {
//                phaser.arriveAndDeregister();
//                while(main.getState() != Thread.State.BLOCKED) {
//                    Thread.onSpinWait();
//                }
//                list.remove(6);
//            }
//        }).start();
//        phaser.arriveAndAwaitAdvance();
//    }

    /**
     * We take advantage of type erasure to insert a magical object into the list.
     * When {@link Object#toString} is called, which would be after the call to
     * it.next(), but before the call to System.out.println(), we remove this,
     * leaving behind the desired list of Integer objects.
     */
    private static void doSomethingNumbers(List<Integer> list) {
        // Replace 5 with object that removes itself and returns "9"
        ((List) list).set(5, new Object() {
            @Override
            public String toString() {
                list.remove(this);
                return "9";
            }
        });
    }
}
