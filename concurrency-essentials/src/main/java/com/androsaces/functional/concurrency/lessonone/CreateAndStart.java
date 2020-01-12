package com.androsaces.functional.concurrency.lessonone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateAndStart extends Thread {
    private static final Logger log = LoggerFactory.getLogger(CreateAndStart.class);

    private CreateAndStart(String name) {
        super(name);
    }

    @Override
    public void run() {
        log.info("Executing thread: {}", Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        CreateAndStart thread = new CreateAndStart("myThread");
        thread.start();
    }
}
