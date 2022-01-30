/*
 * Copyright 2022. Androsaces. All rights reserved.
 */

package com.androsaces.javaessentials.issue270;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.management.*;
import java.lang.reflect.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class DeduplicationExplorer {
    private static final Logger log = LoggerFactory.getLogger(DeduplicationExplorer.class);

    // Java8:
    //   -XX:+UseG1GC
    //   -XX:+UseStringDeduplication
    //   -XX:+PrintStringDeduplicationStatistics
    //   -verbose:gc
    // Java11:
    //   -XX:+UseStringDeduplication
    //   -Xlog:stringdedup*=debug
    //   -verbose:gc
    public static void main(String[] args) throws IOException {
        List<String> lines = new ArrayList<>();
        Socket socket = new ServerSocket(8080).accept();
        PrintStream out = new PrintStream(socket.getOutputStream(), true);
        out.println("Commands: clear, print, ygc, fgc, close");
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) {
            log.info("{}", line);
            switch (line) {
                case "clear":
                    lines.clear();
                    break;
                case "print":
                    print(lines);
                    break;
                case "ygc":
                    youngGC();
                    break;
                case "fgc":
                    System.gc();
                    break;
                case "close":
                    return;
                default:
                    lines.add(line);
            }
        }
    }

    private static void youngGC() {
        long collectionCount = YOUNG_GC.getCollectionCount();
        do {
            // array is too big to be eliminated with escape analysis
            byte[] bytes = new byte[1024];
        } while (YOUNG_GC.getCollectionCount() == collectionCount);
    }

    private static void print(List<String> lines) {
        log.info("lines:");
        lines.forEach(DeduplicationExplorer::print);
    }

    private static void print(String line) {
        try {
            System.out.printf("\t\"%s\" - %s%n", line, VALUE.get(line));
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    private final static Field VALUE;

    static {
        try {
            VALUE = String.class.getDeclaredField("value");
            VALUE.setAccessible(true);
        } catch (NoSuchFieldException e) {
            throw new Error(e);
        }
    }

    private final static GarbageCollectorMXBean YOUNG_GC;

    static {
        YOUNG_GC = ManagementFactory.getGarbageCollectorMXBeans()
                .stream()
                .filter(pool -> pool.getName().equals("G1 Young Generation"))
                .findFirst()
                .orElseThrow(() -> new Error("Could not find G1 Young Generation Garbage Collector MXBean"));
    }
}
