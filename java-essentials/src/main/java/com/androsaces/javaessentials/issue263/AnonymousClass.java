/*
 * Copyright 2021. Androsaces. All rights reserved.
 */

package com.androsaces.javaessentials.issue263;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnonymousClass {
    private static final Logger log = LoggerFactory.getLogger(AnonymousClass.class);
    public static void main(String[] args) {
        var obj = new Object() {
            private void test() {
                log.info("anonymous test");
            }
        };
        obj.test();
    }
}
