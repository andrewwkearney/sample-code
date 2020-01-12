/*
 * Copyright 2020. Androsaces. All rights reserved.
 */

package com.androsaces.javaessentials.issue247;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author Andrew Kearney
 */
public class MemoryPuzzleWithLambdas {
    private static final Logger log = LoggerFactory.getLogger(MemoryPuzzleWithLambdas.class);

    static class A {
        public Runnable get() {
            return new Runnable() {
                @Override
                public void run() {
                    log.info("Hi from A");
                }
            };
        }

        @Override
        protected void finalize() throws Throwable {
            log.info("A finalized");
        }
    }

    static class B {
        public Runnable get() {
            return () -> log.info("Hi from B");
        }

        @Override
        protected void finalize() throws Throwable {
            log.info("B finalized");
        }
    }

    static class C {
        private int count = 0;

        public Runnable get() {
            return () -> log.info("Hi from C# {}", ++count);
        }

        @Override
        protected void finalize() throws Throwable {
            log.info("C finalized");
        }
    }

    static class D {
        private static int count = 0;

        public Runnable get() {
            return () -> log.info("Hi from D# {}", ++count);
        }

        @Override
        protected void finalize() throws Throwable {
            log.info("D finalized");
        }
    }

    static class E {
        private int count = ThreadLocalRandom.current().nextInt();

        public Runnable get() {
            int count = this.count;
            return () -> log.info("Hi from E {}", count);
        }

        @Override
        protected void finalize() throws Throwable {
            log.info("E finalized");
        }
    }

    static class F {
        public Runnable get() {
            return this::friendly;
        }

        public void friendly() {
            log.info("Hi from F");
        }

        @Override
        protected void finalize() throws Throwable {
            System.out.println("F finalized");
        }
    }

    public static void main(String[] args) {
        ScheduledExecutorService timer = Executors.newSingleThreadScheduledExecutor();
        timer.scheduleAtFixedRate(new A().get(), 1, 1, TimeUnit.SECONDS);
        timer.scheduleAtFixedRate(new B().get(), 1, 1, TimeUnit.SECONDS);
        timer.scheduleAtFixedRate(new C().get(), 1, 1, TimeUnit.SECONDS);
        timer.scheduleAtFixedRate(new D().get(), 1, 1, TimeUnit.SECONDS);
        timer.scheduleAtFixedRate(new E().get(), 1, 1, TimeUnit.SECONDS);
        timer.scheduleAtFixedRate(new F().get(), 1, 1, TimeUnit.SECONDS);
        timer.scheduleAtFixedRate(System::gc, 1, 1, TimeUnit.SECONDS);
    }
}
