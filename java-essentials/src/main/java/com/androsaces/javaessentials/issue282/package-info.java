/**
 * <h1>Biased locking a goner, but better things Loom ahead</h1>
 * <h2>Sourced from the Java&trade; Specialist's Newsletter</h2>
 * <h3>Issue 282</h3>
 * Abstract:
 * Biased locking has made unnecessary mutexes cheap for over a decade. However,
 * it is disabled by default in Java 15, slated for removal. From Java 15
 * onwards we should be more diligent to avoid synchronized in places where we
 * do not need it.
 */
package com.androsaces.javaessentials.issue282;