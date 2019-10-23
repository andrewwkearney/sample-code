/*
 * Copyright 2019. Androsaces. All rights reserved.
 */

/**
 * As we have seen in the last examples, the exact sequence in which all
 * running threads are executed depends next to the thread configuration like
 * priority also on the available CPU resources and the way the scheduler
 * chooses the next thread to execute. Although the behavior of the scheduler
 * is completely deterministic, it is hard to predict which threads execute in
 * which moment at a given point in time. This makes access to shared resources
 * critical as it is hard to predict which thread will be the first thread that
 * tries to access it. And often access to shared resources is exclusive, which
 * means only one thread at a given point in time should access this resource
 * without any other thread interfering this access.
 *
 * @author Andrew Kearney
 */
package com.androsaces.samplecode.concurrency.lessonone.synchronization;