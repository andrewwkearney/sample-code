/*
 * Copyright 2019. Androsaces. All rights reserved.
 */

package com.androsaces.samplecode.concurrency.lessonthree.executors;

import java.util.concurrent.Executor;

/**
 * The {@link java.util.concurrent} package defines a set of interfaces whose
 * implementations execute tasks. The simplest one of these is the Executor
 * interface.
 *
 * Hence an Executor implementation takes the given Runnable instance and
 * executes it. The interface makes no assumptions about the way of the
 * execution, the javadoc only states “Executes the given command at some time
 * in the future.”.
 *
 * @author Andrew Kearney
 */
public class MyExecutor implements Executor {
    @Override
    public void execute(Runnable command) {
        new Thread(command).start();
    }
}
