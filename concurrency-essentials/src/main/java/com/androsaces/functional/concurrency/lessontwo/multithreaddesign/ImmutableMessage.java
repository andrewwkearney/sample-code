/*
 * Copyright 2019. Androsaces. All rights reserved.
 */

package com.androsaces.functional.concurrency.lessontwo.multithreaddesign;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * In the following you will find a set of rules to apply when you want to make a class immutable:
 *
 * <ul>
 * <li>All fields should be final and private.</li>
 * <li>There should be not setter methods.</li>
 * <li>The class itself should be declared final in order to prevent subclasses
 *     to violate the principle of immutability.</li>
 * <li>If fields are not of a primitive type but a reference to another object:
 *     </li>
 * <li>There should not be a getter method that exposes the reference directly
 *     to the caller.</li>
 * <li>Don’t change the referenced objects (or at least changing these
 *     references is not visible to clients of the object).</li>
 * </ul>
 *
 * The class is immutable as all of its fields are final and private. There are
 * no methods that would be able to modify the state of an instance after its
 * construction. Returning the references to subject and message is safe, as
 * String itself is an immutable class. The caller who obtains a reference for
 * example to the message cannot modify it directly. With the Map of headers we
 * have to pay more attention. Just returning the reference to the Map would
 * allow the caller to change its content. Hence we have to return an
 * unmodifiable Map acquired through a call of Collections.unmodifiableMap().
 * This returns a view on the Map that allows callers to read the values (which
 * are strings again), but which does not allow modifications. An
 * UnsupportedOperationException will be thrown when trying to modify the Map
 * instance. In this example it is also safe to return the value for a specific
 * key, like it is done within getHeader(String key), as the returned String is
 * immutable again. If the Map would contain objects that are not immutable
 * themselves, this operation would not be thread-safe.
 *
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
