/*
 * Copyright 2019. Androsaces. All rights reserved.
 */

/**
 * In the previous section we saw how to synchronize access to some complex
 * resource when many concurrent threads have to execute a certain part of code
 * but only one thread should execute it at each point in time. We have also
 * seen that if we do not synchronize access to common resources, operations on
 * these resources interleave and may cause an illegal state.
 *
 * The Java language provides some basic operations that are atomic and that
 * therefore can be used to make sure that concurrent threads always see the
 * same value:
 * <ul>
 * <li>Read and write operations to reference variables and primitive variables
 *     (except long and double)</li>
 * <li>Read and write operations for all variables declared as volatile</li>
 * </ul>
 * To understand this in more detail, let’s assume we have a HashMap filled
 * with properties that are read from a file and a bunch of threads that work
 * with these properties. It is clear that we need some kind of synchronization
 * here, as the process of reading the file and update the Map costs time and
 * that during this time other threads are executed.
 *
 * We cannot easily share one instance of this Map between all threads and work
 * on this Map during the update process. This would lead to an inconsistent
 * state of the Map, which is read by the accessing threads. With the knowledge
 * from the last section we could of course use a synchronized block around
 * each access (read/write) of the map to ensure that the all threads only see
 * one state and not a partially updated Map. But this leads to performance
 * problems if the concurrent threads have to read very often from the Map.
 *
 * Cloning the Map for each thread within a synchronized block and letting each
 * thread work on a separate copy would be a solution, too. But each thread
 * would have to ask from time to time for an updated copy and the copy
 * occupies memory, which might not be feasible in each case. But there is a
 * more simple solution.
 *
 * Since we know that write operations to a reference are atomic, we can create
 * a new Map each time we read the file and update the reference that is shared
 * between the threads in one atomic operation. In this implementation the
 * worker threads will never read an inconsistent Map as the Map is updated with
 * one atomic operation.
 *
 * @author Andrew Kearney
 */
package com.androsaces.samplecode.concurrency.lessonone.atomicaccess;