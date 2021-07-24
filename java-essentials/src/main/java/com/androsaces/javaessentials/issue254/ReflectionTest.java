
package com.androsaces.javaessentials.issue254;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public class ReflectionTest {
    private static final Logger log = LoggerFactory.getLogger(ReflectionTest.class);

    public static void main(String[] args) throws Exception {
        log.info("{}", A.class.getMethod("foo"));
        log.info("{}", B.class.getMethod("foo"));
        log.info("{}", C.class.getMethod("foo"));
        log.info("all methods in Class C");
        for (Method method : C.class.getMethods()) {
            log.info("{}", method);
        }
    }
}
