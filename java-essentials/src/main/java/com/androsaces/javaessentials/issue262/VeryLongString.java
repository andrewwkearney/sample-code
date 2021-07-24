/*
 * Copyright 2021. Androsaces. All rights reserved.
 */

package com.androsaces.javaessentials.issue262;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VeryLongString {
    private static final Logger log = LoggerFactory.getLogger(VeryLongString.class);

    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder(1_999_999_995);
        for (int i = 0; i < 285_714_285; i++) {
            sb.append("ASDZguv");
        }

        String s = sb.toString();
        for (int i = 0; i < 10; i++) {
            long time = System.nanoTime();
            try {
                log.info("hashCode: {}", s.hashCode());
            } finally {
                time = System.nanoTime() - time;
                log.info("time = {}ms", (time / 1_000_000));
            }
        }
    }
}
