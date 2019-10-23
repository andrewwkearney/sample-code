/*
 * Copyright 2019. Androsaces. All rights reserved.
 */

/**
 * <h1>3. Sleeping and interrupting</h1>
 * Once we have started a {@link java.lang.Thread}, it runs until the
 * {@code run()} method reaches its end. In the examples above the {@code run()}
 * method did nothing more than just printing out the name of the current
 * thread. Hence the thread finished very soon.
 *
 * In real world applications you will normally have to implement some kind of
 * background processing where you let the thread run until it has for example
 * processed all files within a directory structure, for example. Another
 * common use case is to have a background thread that looks every n seconds if
 * something has happened (e.g. a file has been created) and starts some kind
 * of action. In this case you will have to wait for n seconds or milliseconds.
 * You could implement this by using a while loop whose body gets the current
 * milliseconds and looks when the next second has passed. Although such an
 * implementation would work, it is a waste of CPU processing time as your
 * thread occupies the CPU and retrieves the current time again and again.
 *
 * @author Andrew Kearney
 */
package com.androsaces.samplecode.concurrency.lessonone.sleep;