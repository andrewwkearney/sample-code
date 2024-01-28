/*
 * Copyright 2022. Androsaces. All rights reserved.
 */

package com.androsaces.javaessentials.issue272;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * @author Andrew Kearney
 */
public class Spielverderber {
    private static final Logger log = LoggerFactory.getLogger(Spielverderber.class);

    public static void main(String[] args) {
        Field[] declaredFields = Field.class.getDeclaredFields();
        for (int i = 0; i < declaredFields.length; i++) {
            log.info("{}", declaredFields[i]);
        }
    }
}
