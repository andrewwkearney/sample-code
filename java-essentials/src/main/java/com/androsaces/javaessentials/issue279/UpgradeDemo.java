/*
 * Copyright 2024. Androsaces. All rights reserved.
 */

package com.androsaces.javaessentials.issue279;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class UpgradeDemo {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpgradeDemo.class);

    public static void main(String[] args) {
        var rwlock = new ReentrantReadWriteLock();
        LOGGER.info("{}", rwlock);
        rwlock.readLock().lock();
        LOGGER.info("{}", rwlock);
        rwlock.writeLock().lock(); // deadlock
        LOGGER.info("{}", rwlock);
        rwlock.readLock().unlock();
        LOGGER.info("{}", rwlock);
        rwlock.writeLock().unlock();
        LOGGER.info("{}", rwlock);
    }


}
