/*
 * Copyright 2019. Androsaces. All rights reserved.
 */

package com.androsaces.functional.concurrency.lessontwo.multithreaddesign;

import java.util.Collections;
import java.util.List;

/**
 * The static factory method creates a new instance of Job using the private
 * constructor and already calls start() on the instance. The returned reference
 * of Job is already in the correct state to work with, hence the getResults()
 * method only needs to be synchronized but does not have to check for the state
 * of the object.
 *
 * @author Andrew Kearney
 */
public class StaticFactoryAPIDesign {
    private final String mFilename;

    private StaticFactoryAPIDesign(String filename) {
        mFilename = filename;
    }

    public static StaticFactoryAPIDesign creqteAndStart(String filename) {
        StaticFactoryAPIDesign job = new StaticFactoryAPIDesign(filename);
        job.start();
        return job;
    }

    private void start() {

    }

    public synchronized List getResults() {
        return Collections.emptyList();
    }
}
