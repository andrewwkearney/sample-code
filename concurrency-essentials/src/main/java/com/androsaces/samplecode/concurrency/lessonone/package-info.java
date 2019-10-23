/*
 * Copyright 2019. Androsaces. All rights reserved.
 */

/**
 * These pages were part of a course on Java Concurrency essentials and do not
 * represent work created by me. They are just for learning about concurrency.
 *
 * <h1>Basic know-how about threads</h1>
 * Concurrency is the ability of a program to execute several computations
 * simultaneously. This can be achieved by distributing the computations over
 * the available CPU cores of a machine or even over different machines within
 * the same network.
 *
 * To achieve a better understanding of parallel execution, we have to
 * distinguish between processes and threads. Processes are an execution
 * environment provided by the operating system that has its own set of private
 * resources (e.g. memory, open files, etc.). {@code Threads} in contrast are
 * processes that live within a process and share their resources (memory, open
 * files, etc.) with the other threads of the process.
 *
 * The ability to share resources between different threads makes threads more
 * suitable for tasks where performance is a significant requirement. Though it
 * is possible to establish an inter-process communication between different
 * processes running on the same machine or even on different machines within
 * the same network, for performance reasons, threads are often chosen to
 * parallelize the computation on a single machine.
 *
 * In Java, processes correspond to a running Java Virtual Machine (JVM),
 * whereas threads live within the same JVM and can be created and stopped by
 * the Java application dynamically during runtime. Each program has at least
 * one thread: the main thread. This main thread is created during the start
 * of each Java application and it is the one that calls the {@code main()}
 * method of your program. From this point on, the Java application can create
 * new Threads and work with them.
 *
 * The truth about threads is that not all threads are really executed
 * simultaneously, but the execution time on each CPU core is divided into
 * small slices and the next time slice is assigned to the next waiting thread
 * with the highest priority. The scheduler of the JVM decides based on the
 * thread’s priorities which thread to execute next.
 *
 * Next to the priority, a thread also has a state, which can be one of the
 * following:
 *
 * <ul>
 * <li>NEW: A thread that has not yet started is in this state.</li>
 * <li>RUNNABLE: A thread executing in the Java virtual machine is in this
 *     state.</li>
 * <li>BLOCKED: A thread that is blocked waiting for a monitor lock is in
 *     this state.</li>
 * <li>WAITING: A thread that is waiting indefinitely for another thread to
 *     perform a particular action is in this state.</li>
 * <li>TIMED_WAITING: A thread that is waiting for another thread to perform
 *     an action for up to a specified waiting time is in this state.</li>
 * <li>TERMINATED: A thread that has exited is in this state.</li>
 * </ul>
 *
 * Our main thread from the example above is of course in the state RUNNABLE.
 * State names like BLOCKED indicate here already that thread management is an
 * advanced topic. If not handled properly, threads can block each other which
 * in turn cause the application to hang. But we will come to this later on.
 *
 * Last but not least the attribute {@code threadGroup} of our thread indicates
 * that threads are managed in groups. Each thread belongs to a group of
 * threads. The JDK class {@link java.lang.ThreadGroup} provides some methods
 * to handle a whole group of {@code Threads}. With these methods we can for
 * example interrupt all threads of a group or set their maximum priority.
 *
 * @author Andrew Kearney
 */
package com.androsaces.samplecode.concurrency.lessonone;