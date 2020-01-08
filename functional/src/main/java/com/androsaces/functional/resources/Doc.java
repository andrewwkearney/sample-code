/*
 * Copyright 2020. Androsaces. All rights reserved.
 */

package com.androsaces.functional.resources;

/**
 * @author Andrew Kearney
 */
public class Doc {
    private String mApiId;
    private String mError;
    private String mMessage;
    private Status mStatus;
    private Type mType;

    public String getApiId() {
        return mApiId;
    }

    public void setApiId(String apiId) {
        mApiId = apiId;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public Status getStatus() {
        return mStatus;
    }

    public void setStatus(Status status) {
        mStatus = status;
    }

    public String getError() {
        return mError;
    }

    public void setError(String error) {
        mError = error;
    }

    public Type getType() {
        return mType;
    }

    public void setType(Type type) {
        mType = type;
    }

    public enum Status {
        PROCESSED,
        FAILED
    }

    public enum Type {
        IMPORTANT
    }
}
