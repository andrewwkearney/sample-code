/**
 * <h1>Strings with Zero HashCode</h1>
 * <h2>Sourced from the Java&trade; Specialist's Newsletter</h2>
 * <h3>Issue 277</h3>
 * Abstract:
 * In Java 2, the computational time complexity of String#hashCode() became
 * linear, as it used every character to calculate the hash. Java 3 cached the
 * hash code, so that it would not need to be recalculated every time we called
 * hashCode(), except in the case that the hash code was zero. Java 13 finally
 * fixes that inefficiency with an additional boolean field.
 */
package com.androsaces.javaessentials.issue277;