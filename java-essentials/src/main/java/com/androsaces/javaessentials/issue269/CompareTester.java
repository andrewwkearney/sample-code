/*
 * Copyright 2022. Androsaces. All rights reserved.
 */

package com.androsaces.javaessentials.issue269;

import com.androsaces.javaessentials.issue268.Person;
import com.androsaces.javaessentials.issue268.Programmer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Stream;

public class CompareTester {
    private static final Logger log = LoggerFactory.getLogger(CompareTester.class);

    public static <E extends Comparable<E>> void test(E[] comparables) {
        test(comparables, Comparator.naturalOrder());
    }

    public static <E> void test(E[] comparables, Comparator<E> comparator) {
        rule1(comparator, comparables);
        rule2(comparator, comparables);
        rule3(comparator, comparables);
        suggestion1(comparator, comparables);
    }

    // Quadratic
    private static <E> void rule1(Comparator<E> comparator, E[] comparables) {
        for (E x : comparables) {
            for (E y : comparables) {
                int xy = comparator.compare(x, y);
                int yx = comparator.compare(y, x);
                if (sgn(xy) != -sgn(yx)) {
                    log.info("rule 1 violated with x={}, y={}", x, y);
                }
            }
        }
    }

    // Cubic
    private static <E> void rule2(Comparator<E> comparator, E[] comparables) {
        for (E x : comparables) {
            for (E y : comparables) {
                for (E z : comparables) {
                    int xy = comparator.compare(x, y);
                    int yz = comparator.compare(x, z);
                    if (xy < 0 && yz < 0) {
                        int xz = comparator.compare(x, z);
                        if (!(xz < 0)) {
                            log.info("rule 2 violated with x={}, y={}, z={}", x, y, z);
                        }
                    }
                }
            }
        }
    }

    private static <E> void rule3(Comparator<E> comparator, E[] comparables) {
        for (E x : comparables) {
            for (E y : comparables) {
                for (E z : comparables) {
                    if (comparator.compare(x, y) == 0) {
                        int xz = comparator.compare(x, z);
                        int yz = comparator.compare(y, z);
                        if (sgn(xz) != sgn(yz)) {
                            log.info("rule 3 violated with x={}, y={}, z={}", x, y, z);
                        }
                    }
                }
            }
        }
    }

    private static <E> void suggestion1(Comparator<E> comparator, E[] comparables) {
        for (E x : comparables) {
            for (E y : comparables) {
                int xy = comparator.compare(x, y);
                if ((xy == 0) != x.equals(y)) {
                    log.info("Suggestion 1 violated with x={}, y={}", x, y);
                }
            }
        }
    }

    private static int sgn(int compareResult) {
        return Integer.compare(compareResult, 0);
    }

    public static void main(String[] args) {
        Person[] people = {
                new Programmer("Aaron", (short) 130),
                new Person("Adolf"),
                new Programmer("Brian", (short) 180),
                new Person("Brian"),
                new Programmer("Cedric", (short) 120),
                new Programmer("Cedric", (short) 120),
                new Programmer("Zoran", (short) 200),
        };
        Comparator<Person> comparator = (p1, p2) -> {
            if (p1 instanceof Programmer) {
                if (p2 instanceof Programmer) {
                    return p1.compareTo(p2);
                } else {
                    return -1;
                }
            }
            if (p2 instanceof Programmer) {
                return 1;
            }
            return p1.compareTo(p2);
        };
        CompareTester.test(people, comparator);
        Arrays.sort(people, comparator);
        Stream.of(people).forEach(p -> log.info("{}", p));
    }
}
