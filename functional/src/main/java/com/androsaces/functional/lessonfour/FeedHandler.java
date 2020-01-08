/*
 * Copyright 2020. Androsaces. All rights reserved.
 */

package com.androsaces.functional.lessonfour;

import com.androsaces.functional.resources.Doc;
import com.androsaces.functional.resources.DocumentDb;
import com.androsaces.functional.resources.Resource;
import com.androsaces.functional.resources.WebService;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static com.androsaces.functional.resources.Doc.Status.FAILED;
import static com.androsaces.functional.resources.Doc.Status.PROCESSED;

public class FeedHandler {
    WebService mWebService;
    DocumentDb mDocumentDb;

    List<Doc> handle(List<Doc> changes) {
        return changes.stream()
                .filter(this::isImportant)
                .map(doc -> {
                    try {
                        return createResource(doc)
                                .thenApply(this::setToProcessed)
                                .exceptionally(this::setToFailed)
                                .get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .collect(Collectors.toList());
    }

    private boolean isImportant(Doc doc) {
        return doc.getType() == Doc.Type.IMPORTANT;
    }

    private CompletableFuture<Resource> createResource(Doc doc) {
        return CompletableFuture.completedFuture(mWebService.create(doc));
    }

    private Doc setToProcessed(Resource resource) {
        Doc newDoc = new Doc();
        newDoc.setApiId(resource.getId());
        newDoc.setStatus(PROCESSED);
        return newDoc;
    }

    private Doc setToFailed(Throwable exception) {
        Doc newDoc = new Doc();
        newDoc.setStatus(FAILED);
        newDoc.setError(exception.getMessage());
        return newDoc;
    }

}
