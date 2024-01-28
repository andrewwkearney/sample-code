/*
 * Copyright 2022. Androsaces. All rights reserved.
 */

package com.androsaces.javaessentials.issue272;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Human {
    private static final Logger log = LoggerFactory.getLogger(Human.class);

    public void sing(HumanState state) {
        switch (state) {
            case HAPPY -> singHappySong();
            case SAD -> singDirge();
            default -> throw new IllegalStateException("Invalid state" + state);
        }
    }

    private void singHappySong() {
        log.info("When you're happy and you know it...");
    }

    private void singDirge() {
        log.info("Don't cry for me Argentina...");
    }

    public enum HumanState {
        HAPPY, SAD
    }
}
