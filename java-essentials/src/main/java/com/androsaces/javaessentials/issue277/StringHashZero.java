/*
 * Copyright 2024. Androsaces. All rights reserved.
 */

package com.androsaces.javaessentials.issue277;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringHashZero {
    private static final Logger LOGGER = LoggerFactory.getLogger(StringHashZero.class);

    public static void main(String[] args) {
        String s = "ARcZguv";
        LOGGER.info("{}", s.hashCode());
        LOGGER.info("{}", (s + s).hashCode());
        LOGGER.info("{}", (s + s + s).hashCode());
        LOGGER.info("{}", (s + s + s + s).hashCode());
    }
}
