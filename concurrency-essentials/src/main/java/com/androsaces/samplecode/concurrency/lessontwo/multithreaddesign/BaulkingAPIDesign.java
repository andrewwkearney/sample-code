/*
 * Copyright 2019. Androsaces. All rights reserved.
 */

package com.androsaces.samplecode.concurrency.lessontwo.multithreaddesign;

import java.util.Collections;
import java.util.List;

/**
 * When designing the public methods of a class, i.e. the API of this class, you
 * can also try to design it for multi-threaded usage. You might have methods
 * that should not be executed when the object is within a certain state. One
 * simple solution to overcome this situation would be to have a private flag,
 * which indicate in which state we are and throws for example an
 * IllegalStateException when a specific method should not be called:
 *
 * @author Andrew Kearney
 */
public class BaulkingAPIDesign {
    private boolean mIsRunning = false;
    private final String mFilename;

    public BaulkingAPIDesign(String filename) {
        mFilename = filename;
    }

    public synchronized void start() {
        if (!mIsRunning) {
            throw new IllegalStateException("must be running");
        }
        // ...
    }

    public synchronized List getResults() {
        if (!mIsRunning) {
            throw new IllegalStateException("must be running");
        }
        // ...
        return Collections.emptyList();
    }
}
