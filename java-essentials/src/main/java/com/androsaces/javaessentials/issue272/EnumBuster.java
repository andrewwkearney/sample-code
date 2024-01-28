/*
 * Copyright 2022. Androsaces. All rights reserved.
 */

package com.androsaces.javaessentials.issue272;

import java.lang.reflect.Field;
import java.util.*;

import static com.androsaces.javaessentials.issue272.ReflectionHelper.*;

public class EnumBuster<E extends Enum<E>> {
    private final Class<E> mClazz;
    private final Collection<Field> mSwitchFields;
    private final Deque<Memento> mUndoStack = new ArrayDeque<>();

    /**
     * Construct and EnumBuster for the given enum class and keep
     * the switch statements of the classes specified in
     * switchUsers in sync with the enum values.
     */
    public EnumBuster(Class<E> clazz, Class<?>... switchUsers) {
        mClazz = clazz;
        mSwitchFields = findRelatedSwitchFields(switchUsers);
    }

    /**
     * Make a new enum instance, without adding it to the values
     * array and using the default ordinal of 0.
     */
    public E make(String value) throws ReflectiveOperationException {
        return makeEnum(mClazz, value);
    }

    /**
     * This method adds the given enum into the array
     * inside the enum class.  If the enum already
     * contains that particular value, then the value
     * is overwritten with our enum.  Otherwise it is
     * added at the end of the array.
     * <p>
     * In addition, if there is a constant field in the
     * enum class pointing to an enum with our value,
     * then we replace that with our enum instance.
     * <p>
     * The ordinal is either set to the existing position
     * or to the last value.
     * <p>
     * Warning: This should probably never be called,
     * since it can cause permanent changes to the enum
     * values.  Use only in extreme conditions.
     *
     * @param e the enum to add
     */
    public void addByValue(E e) throws ReflectiveOperationException {
        mUndoStack.push(new Memento());
        Field valuesField = findValuesField();
        // we get the current Enum[]
        E[] values = values();
        for (int i = 0; i < values.length; i++) {
            E value = values[i];
            if (value.name().equals(e.name())) {
                setOrdinal(e, value.ordinal());
                values[i] = e;
                replaceConstant(e);
                return;
            }
        }
        // we did not find it in the existing array, thus
        // append it to the array
        E[] newValues = Arrays.copyOf(values, values.length + 1);
        newValues[newValues.length - 1] = e;
        setStaticFinalField(valuesField, newValues);
        int ordinal = newValues.length - 1;
        setOrdinal(e, ordinal);
        addSwitchCase();
    }

    public boolean deleteByValue(E e) throws ReflectiveOperationException {
        if (e == null) throw new NullPointerException();
        mUndoStack.push(new Memento());
        // we get the current E[]
        mUndoStack.push(new Memento());
        // we get the current E[]
        E[] values = values();
        for (int i = 0; i < values.length; i++) {
            E value = values[i];
            if (value.name().equals(e.name())) {
                E[] newValues = Arrays.copyOf(values, values.length - 1);
                System.arraycopy(values, i + 1, newValues, i, values.length - i - 1);
                for (int j = i; j < newValues.length; j++) {
                    setOrdinal(newValues[j], j);
                }
                Field valuesField = findValuesField();
                setStaticFinalField(valuesField, newValues);
                removeSwitchCase(i);
                blankOutConstant(e);
                return true;
            }
        }
        return false;
    }

    /**
     * Undo the state right back to the beginning when the
     * EnumBuster was created.
     */
    public void restore() throws ReflectiveOperationException {
        while (undo()) {
            //
        }
    }

    /**
     * Undo the previous operation.
     */
    public boolean undo() throws ReflectiveOperationException {
        Memento memento = mUndoStack.poll();
        if (memento == null) return false;
        memento.undo();
        return true;
    }

    /**
     * The only time we ever add a new enum is at the end.
     * Thus all we need to do is expand the switch map arrays
     * by one empty slot.
     */
    private void addSwitchCase() throws ReflectiveOperationException {
        for (Field switchField : mSwitchFields) {
            int[] switches = (int[]) switchField.get(null);
            switches = Arrays.copyOf(switches, switches.length + 1);
            setStaticFinalField(switchField, switches);
        }
    }

    private void replaceConstant(E e) {
        Field[] fields = mClazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equals(e.name())) {
                setStaticFinalField(field, null);
            }
        }
    }

    private void blankOutConstant(E e) throws ReflectiveOperationException {
        Field[] fields = mClazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equals(e.name())) {
                setStaticFinalField(field, null);
            }
        }
    }

    private void setOrdinal(E e, int ordinal) throws NoSuchFieldException, IllegalAccessException {
        Field ordinalField = Enum.class.getDeclaredField("ordinal");
        ordinalField.setAccessible(true);
        ordinalField.set(e, ordinal);
    }

    /**
     * Method to find the values field, set it to accessible,
     * and return it.
     *
     * @return the values array field for the enum.
     * @throws NoSuchFieldException if the field could not be found
     */
    private Field findValuesField() throws NoSuchFieldException {
        // first we find the static final array that holds
        // the values in the enum class
        Field valuesField = mClazz.getDeclaredField("$VALUES");
        // we mark it to be public
        valuesField.setAccessible(true);
        return valuesField;
    }

    private Collection<Field> findRelatedSwitchFields(
            Class<?>[] switchUsers) {
        Collection<Field> result = new ArrayList<>();
        for (Class<?> switchUser : switchUsers) {
            Class<?>[] clazzes = getAnonymousClasses(switchUser);
            for (Class<?> suspect : clazzes) {
                Field[] fields = suspect.getDeclaredFields();
                for (Field field : fields) {
                    if (field.getName().startsWith("$SwitchMap$" +
                            mClazz.getName().replace(".", "$"))) {
                        field.setAccessible(true);
                        result.add(field);
                    }
                }
            }
        }
        return result;
    }

    private void removeSwitchCase(int ordinal)
            throws ReflectiveOperationException {
        for (Field switchField : mSwitchFields) {
            int[] switches = (int[]) switchField.get(null);
            int[] newSwitches = Arrays.copyOf(
                    switches, switches.length - 1);
            System.arraycopy(switches, ordinal + 1, newSwitches,
                    ordinal, switches.length - ordinal - 1);
            setStaticFinalField(switchField, newSwitches);
        }
    }

    private E[] values() throws ReflectiveOperationException {
        return (E[]) findValuesField().get(null);
    }

    private class Memento {
        private final E[] mValues;
        private final Map<Field, int[]> mSavedSwitchFieldValues = new HashMap<>();

        private Memento() throws ReflectiveOperationException {
            mValues = values().clone();
            for (Field switchField : mSwitchFields) {
                int[] switchArray = (int[]) switchField.get(null);
                mSavedSwitchFieldValues.put(switchField, switchArray.clone());
            }
        }

        private void undo() throws ReflectiveOperationException {
            Field valuesField = findValuesField();
            setStaticFinalField(valuesField, mValues);
            for (int i = 0; i < mValues.length; i++) {
                setOrdinal(mValues[i], i);
            }
            // reset all of the constants defined inside the enum
            Map<String, E> valuesMap = new HashMap<>();
            for (E e : mValues) {
                valuesMap.put(e.name(), e);
            }
            Field[] constantEnumFields = mClazz.getDeclaredFields();
            for (Field constantEnumField : constantEnumFields) {
                E en = valuesMap.get(constantEnumField.getName());
                if (en != null) {
                    setStaticFinalField(constantEnumField, en);
                }
            }
            for (Map.Entry<Field, int[]> entry : mSavedSwitchFieldValues.entrySet()) {
                Field field = entry.getKey();
                int[] mappings = entry.getValue();
                setStaticFinalField(field, mappings);
            }
        }
    }
}
