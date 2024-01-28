/**
 * <h1>EnhancedStream with DynamicProxy</h1>
 * <h2>Sourced from the Java&trade; Specialist's Newsletter</h2>
 * <h3>Issue 275</h3>
 * Abstract:
 * In our previous newsletter we enhanced Java 8 Streams by decorating
 * them with an EnhancedStream class. The code had a lot of repetition,
 * which often leads to bugs if written by hand. In this newsletter we
 * use a dynamic proxy to create an EnhancedStream. The resulting code is
 * shorter and more consistent.
 */
package com.androsaces.javaessentials.issue275;