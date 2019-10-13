/*
 * Copyright 2019. Androsaces. All rights reserved.
 */

package com.androsaces.samplecode.concurrency.lessontwo;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Andrew Kearney
 */
public final class ImmutableMessage {
    private final String mSubject;
    private final String mMessage;
    private final Map<String, String> mHeader;

    public ImmutableMessage(String subject, String message, Map<String, String> header) {
        mSubject = subject;
        mMessage = message;
        mHeader = new HashMap<>(header);
    }

    public String getSubject() {
        return mSubject;
    }

    public String getMessage() {
        return mMessage;
    }

    public String getHeader(String key) {
        return mHeader.get(key);
    }

    public Map<String, String> getHeaders() {
        return Collections.unmodifiableMap(mHeader);
    }
}
