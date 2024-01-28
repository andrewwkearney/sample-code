/**
 * <h1>Checking HashMaps with MapClashInspector</h1>
 * <h2>Sourced from the Java&trade; Specialist's Newsletter</h2>
 * <h3>Issue 235</h3>
 * Abstract:
 * Java 8 HashMap has been optimized to avoid denial of service attacks with
 * many distinct keys containing identical hash codes. Unfortunately
 * performance might degrade if you use your own keys. In this newsletter we
 * show a tool that you can use to inspect your HashMap and view the key
 * distribution within the buckets.
 */
package com.androsaces.javaessentials.issue235;