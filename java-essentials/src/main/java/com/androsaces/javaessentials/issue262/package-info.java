/**
 * <h1>How Java Maps Protect Themselves From DOS Attacks</h1>
 * <h2>Sourced from the Java&trade; Specialist's Newsletter</h2>
 * <h3>Issue 262</h3>
 * Abstract:
 * A well known exploit with maps is to create a lot of Strings as keys that
 * all have the same hash code. In this newsletter we see how easily that can
 * be done and what Java 8+ HashMap does to protect itself.
 */
package com.androsaces.javaessentials.issue262;