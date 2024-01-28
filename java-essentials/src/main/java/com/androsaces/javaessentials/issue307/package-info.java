/**
 * <h1>AccessFlag Set for Modifiers</h1>
 * <h2>Sourced from the Java&trade; Specialist's Newsletter</h2>
 * <h3>Issue 307</h3>
 * Abstract:
 * Reflection returns the modifiers of class elements as an unqualified int
 * bitset. Unfortunately some of the bits have a different meaning depending on
 * their context. For example, a method can have their transient bit set, even
 * though that does not make sense for a method. In Java 20, we now have a more
 * accurate and robust enum based representation for modifiers.
 */
package com.androsaces.javaessentials.issue307;