/*
 * Copyright 2020. Androsaces. All rights reserved.
 */

package com.androsaces.functional.resources;

import java.util.concurrent.CompletableFuture;

/**
 * @author Andrew Kearney
 */
public interface ResourceCreator {
    CompletableFuture<Doc> create(Doc doc);
}
