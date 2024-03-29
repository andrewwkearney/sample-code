/*
 * Copyright 2022. Androsaces. All rights reserved.
 */

package com.androsaces.javaessentials.issue161;

import sun.reflect.ReflectionFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ReflectionHelper {
    private static final String MODIFIERS_FIELD = "modifiers";
    private static final ReflectionFactory reflection = ReflectionFactory.getReflectionFactory();

    public static void setStaticFinalField(Field field,
                                           Object value) throws NoSuchFieldException, IllegalAccessException {
        // we mark the field to be public
        field.setAccessible(true);
        // next we change the modifier in the Field instance
        // not to be final anymore, thus tricking reflection in
        // letting us modify the static final field
        Field modifiersField = Field.class.getDeclaredField(MODIFIERS_FIELD);
        modifiersField.setAccessible(true);
        int modifiers = modifiersField.getInt(field);
        // blank out the final bit in the modifiers int
        modifiers &= ~Modifier.FINAL;
        modifiersField.setInt(field, modifiers);
//        FieldAccessor fa = reflection.newFieldAccessor(field, false);
//        fa.set(null, value);
    }
}
