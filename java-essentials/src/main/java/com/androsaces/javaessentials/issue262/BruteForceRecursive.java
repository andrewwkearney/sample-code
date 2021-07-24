/*
 * Copyright 2021. Androsaces. All rights reserved.
 */

package com.androsaces.javaessentials.issue262;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class BruteForceRecursive extends BruteForceBase {
    private static final Logger log = LoggerFactory.getLogger(BruteForceRecursive.class);

    public static void main(String[] args) {
        byte[] is = new byte[7];
        Arrays.fill(is, (byte) '!');
        long start = System.nanoTime();
        check(0, 0, is, 0);
        long end = System.nanoTime() - start;
        log.info("completed in {}ms", end / 1_000_000);
    }

    static void check(int depth, int h, byte[] is, int target) {
        if (depth == 7) {
            if (h == target) {
                log.info(new String(is));
            }
            return;
        }
        for (byte i : alphabet) {
            is[depth] = i;
            check(depth + 1, h * 31 + i, is, target);
        }
    }
}
