/*
 * Copyright 2022. Androsaces. All rights reserved.
 */

package com.androsaces.javaessentials.issue272;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class HiddenFieldsRevealed {
    private static final Logger log = LoggerFactory.getLogger(HiddenFieldsRevealed.class);
    private static final Object mGreeting = "Hello World";

    public static void main(String[] args) throws ReflectiveOperationException {
        VarHandle vh = MethodHandles.privateLookupIn(Field.class, MethodHandles.lookup())
                .findVarHandle(Field.class, "modifiers", int.class);
        Field greetingField = HiddenFieldsRevealed.class.getDeclaredField("mGreeting");
        log.info("isFinal={}", Modifier.isFinal(greetingField.getModifiers()));
        vh.set(greetingField, 0);
        log.info("isFinal={}", Modifier.isFinal(greetingField.getModifiers()));
    }
}
