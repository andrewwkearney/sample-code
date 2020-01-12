/*
 * Copyright 2019. Androsaces. All rights reserved.
 */

/**
 * When developing an application that uses concurrency to accomplish its aims,
 * you may come across situations where different threads may block each other.
 * As the whole application then performs slower than expected, we would say
 * that the application does not finish in matter of time as expected. In this
 * section we will take a closer look at the problems that may endanger the
 * live-ness of a multi-threaded application.
 * <p>
 * In general the following requirements for a deadlock can be identified:
 * <ul>
 * <li><b>Mutual exclusion:</b> There is a resource which can be accessed only
 *     by one thread at any point in time.</li>
 * <li><b>Resource holding:</b> While having locked one resource, the thread
 *     tries to acquire another lock on some other exclusive resource.</li>
 * <li><b>No preemption:</b> There is no mechanism, which frees the resource if
 *     one threads holds the lock for a specific period of time.</li>
 * <li><b>Circular wait:</b> During runtime a constellation occurs in which two
 *     (or more) threads are each waiting on the other thread to free a
 *     resource that it has locked.</li>
 * </ul>
 * Although the list of requirements looks long, it is not uncommon that more
 * advanced multi-threaded applications have deadlock problems. But you can try
 * to avoid deadlocks if you are able to relax one of the requirements listed
 * above:
 *
 * <ul>
 * <li>Mutual exclusion: This is a requirement that often cannot be relaxed, as
 *     the resource has to be used exclusively. But this must no always be the
 *     case. When using DBMS systems, a possible solution instead of using a
 *     pessimistic lock on some table row that has to be updated, one can use a
 *     technique called Optimistic Locking.</li>
 * <li>A possible solution to circumvent resource holding while waiting for
 *     another exclusive resource is to lock all necessary resources at the
 *     beginning of the algorithm and free all resources if it is not possible
 *     to obtain all locks. This is of course not always possible, maybe the
 *     resources to lock are not known ahead or it is just as waste of
 *     resources.</li>
 * <li>If the lock cannot be obtained immediately, a possible solution to
 *     circumvent a possible deadlock is the introduction of a timeout. The SDK
 *     class ReentrantLock for example provides the possibility to specify a
 *     timeout for locking.</li>
 * <li>As we have seen from the example code above, a deadlock does not appear
 *     if the sequence of lock requests does not differ between the different
 *     threads. This can be easily controlled if you are able to put all the
 *     locking code into one method where all threads have to pass through.</li>
 * </ul>
 *
 * In more advanced applications you may even consider the implementation of a
 * deadlock detection system. Here you would have to implement some kind of
 * thread monitoring, where each thread reports the successful acquisition of a
 * lock and his attempt to obtain a lock. If threads and locks are modelled as
 * a directed graph, you are able to detect when two different threads are
 * holding resources while simultaneously requesting another blocked resource.
 * If you would then able to force the blocking threads to release an obtained
 * resource you are able to resolve deadlock situations automatically.
 *
 * <h2>1.2. Starvation</h2>
 * The scheduler decides which of the threads in state RUNNABLE it should
 * execute next. The decision is based on the thread’s priority; hence threads
 * with lower priority gain less CPU time than threads with a higher priority.
 * What sounds like a reasonable feature can also cause problems when abused.
 * If most of the time threads with a high priority are executed, the threads
 * with lower priority seem to “starve” as they get not enough time to execute
 * their work properly. Therefore it is recommended to set the priority of a
 * thread only if there are strong reasons for it.
 *
 * A sophisticated example for thread starvation is for example the finalize()
 * method. This feature of the Java language can be used to execute code before
 * an object gets garbage collected. But when you take a look at the priority
 * of the finalizer thread, you may see that it runs not with highest priority.
 * Hence there is the possibility for thread starvation if the finalize()
 * methods of your object spend too much time in comparison to the rest of the
 * code.
 *
 * Another problem with execution time is the problem, that it is not defined
 * in which order threads pass a synchronized block. When many parallel threads
 * have to pass some code that is encapsulated with a synchronized block, it
 * may happen that certain threads have to wait longer than other threads until
 * they can enter the block. In theory they may never enter the block.
 *
 * A solution to the latter problem is the so called “fair” lock. Fair locks
 * take the waiting time of the threads into account, when choosing the next
 * thread to pass. An example implementation of a fair lock is provided by the
 * Java SDK: {@link java.util.concurrent.locks.ReentrantLock}. If the
 * constructor with the boolean flag set to true is used, the ReentrantLock
 * grants access to the longest-waiting thread. This guarantees a lack of
 * starvation but introduces at the same time the problem that the thread
 * priority is not taken into account and therefore threads with lower priority
 * that often wait at this barrier may be executed more often. Last but not
 * least the ReentrantLock class can of course only consider threads that are
 * waiting for the lock, i.e. threads that have been executed often enough to
 * reach the lock. If thread priority is too low this may not often happen and
 * therefore threads with higher priority still pass the lock more often.
 *
 * @author Andrew Kearney
 */
package com.androsaces.functional.concurrency.lessontwo.liveness;