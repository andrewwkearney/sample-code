/*
 * Copyright 2020. Androsaces. All rights reserved.
 */

package com.androsaces.functional.lessonone;

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
        for (int i = 0; i < changes.size(); i++) {
            Doc doc = changes.get(i);
            // if (Doc.Type.IMPORTANT == doc.getType()) {
            changes.stream()
                    .filter(d -> d.getType() == Doc.Type.IMPORTANT)
                    .forEach(d -> {
                        try {
                            Resource resource = mWebService.create(doc);
                            doc.setApiId(resource.getId());
                            doc.setStatus(PROCESSED);
                        } catch (Exception e) {
                            doc.setStatus(FAILED);
                            doc.setError(e.getMessage());
                        }
                        mDocumentDb.update(doc);
                    });
        }
    }

    private boolean isImportant(Doc doc) {
        return doc.getType() == Doc.Type.IMPORTANT;
    }
}
