/**
 * <h1>Upgrading ReadWrite Lock</h1>
 * <h2>Sourced from the Java&trade; Specialist's Newsletter</h2>
 * <h3>Issue 279</h3>
 * Abstract:
 * The Java ReentrantReadWriteLock can never ever upgrade a read lock to a write
 * lock. Kotlin's extension function ReentrantReadWriteLock.write() cheats a bit
 * by letting go of the read lock before upgrading, thus opening the door for
 * race conditions. A better solution is StampedLock, which has a method to try
 * to convert the lock to a write lock.
 */
package com.androsaces.javaessentials.issue279;