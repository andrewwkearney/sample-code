/*
 * Copyright 2021. Androsaces. All rights reserved.
 */

package com.androsaces.javaessentials.issue262;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BruteForceBase {
    private static final Logger log = LoggerFactory.getLogger(BruteForceBase.class);
    protected static final byte[] alphabet = new byte[26 * 2];

    static {
        for (int i = 0; i < 26; i++) {
            alphabet[i] = (byte) ('A' + i);
            alphabet[i + 26] = (byte) ('a' + i);
        }
        log.info("alphabet: {}", new String(alphabet));
    }
}
