/*
 * Copyright 2022. Androsaces. All rights reserved.
 */

package com.androsaces.javaessentials.issue272;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class ReflectionHelper {
    private static final Logger log = LoggerFactory.getLogger(ReflectionHelper.class);
    private final static Unsafe UNSAFE;

    public static Class<?>[] getAnonymousClasses(Class<?> clazz) {
        Collection<Class<?>> classes = new ArrayList<>();
        for (int i = 1; ; i++) {
            try {
                classes.add(Class.forName(clazz.getName() + "$" + i,
                        false, // do not initialize
                        Thread.currentThread().getContextClassLoader()));
            } catch (ClassNotFoundException e) {
                break;
            }
        }
        for (Class<?> inner : clazz.getDeclaredClasses()) {
            Collections.addAll(classes, getAnonymousClasses(inner));
        }
        for (Class<?> anon : new ArrayList<>(classes)) {
            Collections.addAll(classes, getAnonymousClasses(anon));
        }
        return classes.toArray(new Class<?>[0]);
    }

    static {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            UNSAFE = (Unsafe) theUnsafe.get(null);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Super dangerous and might have unintended side effects. Use
     * only for testing, and then with circumspection.
     * @param field
     * @param value
     * @throws ReflectiveOperationException
     */
    public static void setStaticFinalField(Field field, Object value) {
        if (!Modifier.isStatic(field.getModifiers()) || !Modifier.isFinal(field.getModifiers())) {
            throw new IllegalArgumentException("field should be final static");
        }
        Object fieldBase = UNSAFE.staticFieldBase(field);
        long fieldOffset = UNSAFE.staticFieldOffset(field);
        UNSAFE.putObject(fieldBase, fieldOffset, value);
    }

    public static <E extends Enum<E>> E makeEnum(Class<E> enumType, String name) throws ReflectiveOperationException {
        E e = (E) UNSAFE.allocateInstance(enumType);
        Field nameField = Enum.class.getDeclaredField("name");
        nameField.setAccessible(true);
        nameField.set(e, name);
        return e;
    }
}
