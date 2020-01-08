/*
 * Copyright 2020. Androsaces. All rights reserved.
 */

package com.androsaces.functional.lessontwo;

import com.androsaces.functional.resources.Doc;
import com.androsaces.functional.resources.DocumentDb;
import com.androsaces.functional.resources.Resource;
import com.androsaces.functional.resources.WebService;

import java.util.List;

import static com.androsaces.functional.resources.Doc.Status.FAILED;
import static com.androsaces.functional.resources.Doc.Status.PROCESSED;

public class FeedHandler {
    WebService mWebService;
    DocumentDb mDocumentDb;

    void handle(List<Doc> changes) {
        changes.stream()
                .filter(doc -> isImportant(doc))
                .forEach(doc -> {
                    try {
                        Resource resource = createResource(doc);
                        updateToProcessed(doc, resource);
                    } catch (Exception e) {
                        updateToFailed(doc, e);
                    }
                    mDocumentDb.update(doc);
                });
    }

    private boolean isImportant(Doc doc) {
        return doc.getType() == Doc.Type.IMPORTANT;
    }

    private Resource createResource(Doc doc) {
        return mWebService.create(doc);
    }

    private void updateToProcessed(Doc doc, Resource resource) {
        doc.setApiId(resource.getId());
        doc.setStatus(PROCESSED);
        mDocumentDb.update(doc);
    }

    private void updateToFailed(Doc doc, Exception exception) {
        doc.setStatus(FAILED);
        doc.setError(exception.getMessage());
        mDocumentDb.update(doc);
    }

}
