package com.androsaces.samplecode.concurrency.introduction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.Thread.State;

/**
 *
 */
public class Basic {
    private static final Logger log = LoggerFactory.getLogger(Basic.class);

    public static void main(String[] args) {
        long id = Thread.currentThread().getId();
        String name = Thread.currentThread().getName();
        int priority = Thread.currentThread().getPriority();
        State state = Thread.currentThread().getState();
        String threadGroupName = Thread.currentThread().getThreadGroup().getName();
        log.info("id={}; name={}; priority={}; state={}; threadGroupName={}", id, name, priority, state, threadGroupName);
    }
}
