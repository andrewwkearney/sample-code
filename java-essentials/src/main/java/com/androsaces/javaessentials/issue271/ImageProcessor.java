/*
 * Copyright 2022. Androsaces. All rights reserved.
 */

package com.androsaces.javaessentials.issue271;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDate;
import java.util.concurrent.*;

public class ImageProcessor {
    public static final int NUMBER_TO_SHOW = 1000;
    public static final int DELAY = 0;
    private static final Logger log = LoggerFactory.getLogger(ImageProcessor.class);

    private final CountDownLatch mLatch = new CountDownLatch(NUMBER_TO_SHOW);
    private final Executor mExecutor = Executors.newCachedThreadPool(new NamedThreadFactory("executor"));
    private final ExecutorService mExecutorService = Executors.newFixedThreadPool(100, new NamedThreadFactory("executorService"));
    public boolean isPrintMessage = true;
    public boolean isSaveFile = true;
    public boolean isShowInFrame = true;
    public Path mImageDir = Paths.get("/tmp/images");

    private final HttpClient client = HttpClient.newBuilder()
            .executor(mExecutor)
            .followRedirects(HttpClient.Redirect.NEVER)
            .build();

    public <T> CompletableFuture<T> getAsync(String url,
                                             HttpResponse.BodyHandler<T> responseBodyHandler) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(30))
                .build();
        return client.sendAsync(request, responseBodyHandler)
                .thenApplyAsync(HttpResponse::body, mExecutorService);
    }

    private CompletableFuture<ImageInfo> findImageInfo(LocalDate date,
                                                       ImageInfo info) {
        return getAsync(info.getUrlForDate(date), HttpResponse.BodyHandlers.ofString())
                .thenApply(info::findImage);
    }

    private CompletableFuture<ImageInfo> findImageData(ImageInfo info) {
        return getAsync(info.getImagePath(), HttpResponse.BodyHandlers.ofByteArray())
                .thenApply(info::setImageData);
    }

    public void load(LocalDate date, ImageInfo info) {
        findImageInfo(date, info)
                .thenCompose(this::findImageData)
                .thenAccept(this::process)
                .exceptionally(t -> {
                    t.printStackTrace();
                    return null;
                })
                .whenComplete((x, t) -> mLatch.countDown());
    }

    public void loadAll() {
        long time = System.nanoTime();
        try {
            LocalDate date = LocalDate.now();
            for (int i = 0; i < NUMBER_TO_SHOW; i++) {
                ImageInfo info = new DilbertImageInfo();
//                ImageInfo info = new WikimediaImageInfo();
                info.setDate(date.toString());
                log.info("Loading: {}", date);
                load(date, info);
                if (DELAY > 0) Thread.sleep(DELAY);
                date = date.minusDays(1L);
            }
            mLatch.await();
            mExecutorService.shutdown();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.warn("Interrupted");
        } finally {
            time = System.nanoTime() - time;
            log.info("time = {}", (time / 1_000_000));
        }
    }

    public void process(ImageInfo info) {
        mLatch.countDown();
        if (isPrintMessage) {
            log.info("process called by {}, date: {}", Thread.currentThread(), info.getDate());
        }
        if (isShowInFrame) {
            EventQueue.invokeLater(() -> {
                JFrame frame = new JFrame(info.getDate());
                frame.add(new JLabel(new ImageIcon(info.getImageData())));
                frame.pack();
                frame.setLocationByPlatform(true);
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                frame.setVisible(true);
            });
        }
        if (isSaveFile) {
            try {
                Files.createDirectories(mImageDir);
                Files.write(mImageDir.resolve(info.getDate() + ".jpg"), info.getImageData());
            } catch (IOException e) {
                log.warn("error writing image info");
            }
        }
    }

    public static void main(String[] args) {
        ImageProcessor processor = new ImageProcessor();
        processor.loadAll();
    }
}

class NamedThreadFactory implements ThreadFactory {
    private final String mName;
    private int mCount;

    public NamedThreadFactory(String name) {
        mName = name;
    }

    @Override
    public Thread newThread(Runnable r) {
        mCount++;
        ThreadFactory factory = Executors.defaultThreadFactory();
        Thread t = factory.newThread(r);
        t.setName(mName + '-' + mCount);
        return t;
    }
}