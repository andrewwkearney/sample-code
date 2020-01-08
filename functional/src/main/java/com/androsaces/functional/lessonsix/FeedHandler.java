/*
 * Copyright 2020. Androsaces. All rights reserved.
 */

package com.androsaces.functional.lessonsix;

import com.androsaces.functional.resources.Doc;
import com.androsaces.functional.resources.Resource;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.androsaces.functional.resources.Doc.Status.FAILED;
import static com.androsaces.functional.resources.Doc.Status.PROCESSED;

public class FeedHandler {
    List<Doc> handle(List<Doc> changes, Function<Doc, CompletableFuture<Resource>> creator) {
        return changes.stream()
                .filter(FeedHandler::isImportant)
                .map(doc -> {
                    try {
                        return creator.apply(doc)
                                .thenApply(resource -> setToProcessed(doc, resource))
                                .exceptionally(e -> setToFailed(doc, e))
                                .get(5, TimeUnit.SECONDS);
                    } catch (InterruptedException | ExecutionException | TimeoutException e) {
                        return handleFailure(doc, e);
                    }
                })
                .collect(Collectors.toList());
    }

    private static boolean isImportant(Doc doc) {
        return doc.getType() == Doc.Type.IMPORTANT;
    }

    private Doc setToProcessed(Doc doc, Resource resource) {
        Doc newDoc = new Doc();
        newDoc.setApiId(resource.getId());
        newDoc.setStatus(PROCESSED);
        return newDoc;
    }

    private Doc setToFailed(Doc doc, Throwable exception) {
        Doc newDoc = new Doc();
        newDoc.setStatus(FAILED);
        newDoc.setError(exception.getMessage());
        return newDoc;
    }

    private static Doc handleFailure(Doc doc, Throwable e) {
        Doc newDoc = new Doc();
        newDoc.setStatus(FAILED);
        newDoc.setError(e.getMessage());
        return newDoc;
    }

}
