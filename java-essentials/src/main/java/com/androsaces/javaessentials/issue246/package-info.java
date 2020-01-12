/*
 * Copyright 2020. Androsaces. All rights reserved.
 */

/**
 * <h1>LRU Cache From LinkedHashMap</h1>
 * <h2>Sourced from the Java&trade; Specialist's Newsletter</h2>
 * <h3>Issue 246</h3>
 * Abstract:
 * The LinkedHashMap has an interesting feature, where we can order elements in
 * "access order", rather than the default "insertion order". This allows us to
 * build a light-weight LRU cache.
 *
 * <h3>LRU Cache From LinkedHashMap</h3>
 * Least-recently-used. LRU. Meaning that elements are removed when they have
 * been used the least recently. As opposed to most recently. It's very
 * confusing, I admit.
 * An oft overlooked feature of LinkedHashMap is the ability to also retrieve
 * elements in the order in which they were last accessed. A special constructor
 * is used to create this type of {@link java.util.LinkedHashMap#LinkedHashMap(int, float, boolean)}
 * - the {@code true} means <pre>accessOrder</pre> instead of the default, which
 * is <pre>insertionOrder</pre>.
 *
 * @author Dr Heinz Kabutz
 */
package com.androsaces.javaessentials.issue246;