/*
 * Copyright 2019. Androsaces. All rights reserved.
 */

package com.androsaces.samplecode.concurrency.lessonone.create;

/**
 * The main difference to the subclassing approach is the fact that we create
 * an instance of {@link java.lang.Thread} and provide an instance of the class
 * that implements the {@link Runnable} interface as an argument to the
 * {@link Thread} constructor. Next to this instance we also pass the name of
 * the Thread, so that we see the following output when we execute the program
 * from command line: “Executing thread myRunnable”.
 *
 * Whether you should use the subclassing or the interface approach, depends to
 * some extend on your taste. The interface is a more light-weight approach as
 * all you have to do is the implementation of an interface. The class can
 * still be a subclass of some other class. You can also pass your own
 * parameters to the constructor whereas subclassing {@code Thread} restricts
 * you to the available constructors that the class {@code Thread} brings along.
 *
 * @author Andrew Kearney
 */
public class CreateThreads {
    public static void main(String[] args) {
        // Extends thread
        MyThread thread = new MyThread("myThread");
        thread.start();

        // Implements runnable
        Thread myRunnable = new Thread(new MyRunnable(), "myRunnable");
        myRunnable.start();
    }
}
