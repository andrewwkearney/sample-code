/*
 * Copyright 2022. Androsaces. All rights reserved.
 */

package com.androsaces.javaessentials.issue272;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.fail;

class HumanTest {
    @Test
    void testSingingAddingEnum() throws Exception {
        EnumBuster<Human.HumanState> buster = new EnumBuster<>(Human.HumanState.class, Human.class);
        try {
            Human heinz = new Human();
            heinz.sing(Human.HumanState.HAPPY);
            heinz.sing(Human.HumanState.SAD);
            Human.HumanState MELLOW = buster.make("MELLOW");
            buster.addByValue(MELLOW);
            System.out.println(Arrays.toString(Human.HumanState.values()));
            try {
                heinz.sing(MELLOW);
                fail("Should have caused an IllegalStateException");
            } catch (IllegalStateException success) {
                // Do nothing
            }
        } finally {
            System.out.println("Restoring HumanState");
            buster.restore();
            System.out.println(Arrays.toString(Human.HumanState.values()));
        }
    }
}
