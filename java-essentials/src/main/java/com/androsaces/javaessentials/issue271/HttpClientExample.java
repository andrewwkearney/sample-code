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
import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Andrew Kearney
 */
public class HttpClientExample {
    private static final Logger log = LoggerFactory.getLogger(HttpClientExample.class);

    private final ExecutorService mExecutor = Executors.newCachedThreadPool();
    private final HttpClient mClient;

    public HttpClientExample() {
        mClient = HttpClient.newBuilder()
                // Redirect except https to http
                .executor(mExecutor)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
    }

    public void doTheThing() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://horstmann.com"))
                .GET()
                .build();

        mClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response -> log.info("{}", response));
    }

    public <T> CompletableFuture<T> getAsync(String url,
                                             HttpResponse.BodyHandler<T> responseBodyHandler) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .build();
        return mClient.sendAsync(request, responseBodyHandler)
                .thenApply(HttpResponse::body);
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
                .thenAccept(this::process);
    }

    public void process(ImageInfo info) {
    }

    public static void main(String[] args) throws Exception {
        HttpClientExample client = new HttpClientExample();
        client.doTheThing();
    }
}
