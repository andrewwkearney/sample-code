/**
 * <h1>Of Hacking Enums and Modifying "static final" Fields</h1>
 * <h2>Sourced from the Java&trade; Specialist's Newsletter</h2>
 * <h3>Issue 161</h3>
 * Abstract:
 * The developers of the Java language tried their best to stop us from
 * constructing our own enum instances. However, for testing purposes, it can
 * be useful to temporarily add new enum instances to the system. In this
 * newsletter we show how we can do this using the classes in {@code sun.reflect}.
 * In addition, we use a similar technique to modify static final fields, which
 * we need to do if we want the switch statements to still work with our new
 * enums.
 */
package com.androsaces.javaessentials.issue161;