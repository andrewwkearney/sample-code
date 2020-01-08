/*
 * Copyright 2020. Androsaces. All rights reserved.
 */

package com.androsaces.functional.lessonthree;

import com.androsaces.functional.resources.Doc;
import com.androsaces.functional.resources.DocumentDb;
import com.androsaces.functional.resources.Resource;
import com.androsaces.functional.resources.WebService;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import static com.androsaces.functional.resources.Doc.Status.FAILED;
import static com.androsaces.functional.resources.Doc.Status.PROCESSED;

public class FeedHandler {
    WebService mWebService;
    DocumentDb mDocumentDb;

    void handle(List<Doc> changes) {
        for (Doc doc : changes) {
            if (isImportant(doc)) {
                createResource(doc)
                        .thenAccept(resource -> updateToProcessed(doc, resource))
                        .exceptionally(throwable -> {
                            updateToFailed(doc, throwable);
                            return null;
                        });
            }
        }
    }

    private boolean isImportant(Doc doc) {
        return doc.getType() == Doc.Type.IMPORTANT;
    }

    private CompletableFuture<Resource> createResource(Doc doc) {
        return CompletableFuture.completedFuture(mWebService.create(doc));
    }

    private void updateToProcessed(Doc doc, Resource resource) {
        doc.setApiId(resource.getId());
        doc.setStatus(PROCESSED);
        mDocumentDb.update(doc);
    }

    private void updateToFailed(Doc doc, Throwable exception) {
        doc.setStatus(FAILED);
        doc.setError(exception.getMessage());
        mDocumentDb.update(doc);
    }

}
