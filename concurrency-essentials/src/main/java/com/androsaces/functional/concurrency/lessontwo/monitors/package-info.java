/*
 * Copyright 2019. Androsaces. All rights reserved.
 */

/**
 * A common task in multi-threaded computing is to have some worker threads that
 * are waiting for their producer to create some work for them. But as we have
 * learned, busy waiting within a loop and checking some value is not a good
 * option in terms of CPU time. In this use case the Thread.sleep() method is
 * also not of much value, as we want to start our work immediately after it has
 * been submitted.
 *
 * The Java Programming Language therefore has another construct, that can be
 * used in this scenario: wait() and notify(). The wait() method that every
 * object inherits from the java.lang.Object class can be used to pause the
 * current thread execution and wait until another threads wakes us up using the
 * notify() method. In order to work correctly, the thread that calls the wait()
 * method has to hold a lock that it has acquired before using the synchronized
 * keyword. When calling wait() the lock is released and the threads waits until
 * another thread that now owns the lock calls notify() on the same object
 * instance.
 *
 * In a multi-threaded application there may of course be more than one thread
 * waiting on some object to be notified. Hence there are two different methods
 * to wake up threads: notify() and notifyAll(). Whereas the first method only
 * wakes up one of the waiting threads, the notifyAll() methods wakes them all
 * up. But be aware that similar to the synchronized keyword there is no rule
 * that specifies which thread to wake up next when calling notify(). In a
 * simple producer and consumer example this does not matter, as we are not
 * interested in the fact which thread exactly wakes up.
 *
 * @author Andrew Kearney
 */
package com.androsaces.functional.concurrency.lessontwo.monitors;