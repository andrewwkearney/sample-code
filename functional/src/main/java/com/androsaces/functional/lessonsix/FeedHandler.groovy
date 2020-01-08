import com.androsaces.functional.resources.Doc
import com.androsaces.functional.resources.Resource

import java.util.concurrent.CompletableFuture
import java.util.function.Function

/*
 * Copyright 2020. Androsaces. All rights reserved.
 */

class FeedHandler {
    List<Doc> handle(List<Doc> changes,
                     Function<Doc, CompletableFuture<Resource>> creator) {

        changes
                .findAll { doc -> isImportant(doc) }
                .collect { doc ->
                    creator.apply(doc)
                            .thenApply { resource ->
                                setToProcessed(doc, resource)
                            }
                            .exceptionally { e ->
                                setToFailed(doc, e)
                            }
                            .get()
                }
    }

    private static boolean isImportant(doc) {
        doc.type == 'important'
    }

    private static Doc setToProcessed(doc, resource) {
        doc.copyWith(
                status: 'processed',
                apiId: resource.id
        )
    }

    private static Doc setToFailed(doc, e) {
        doc.copyWith(
                status: 'failed',
                error: e.message
        )
    }
}