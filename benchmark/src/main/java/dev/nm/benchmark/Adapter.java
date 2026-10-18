/* Copyright (c) NM LTD. See the repository LICENSE. */
package dev.nm.benchmark;

/** Native objects stay native throughout timing; conversion is used only for validation. */
interface Adapter {
    /** Native input objects, used only by checks outside timing. */
    Object[] inputs();
    Object run(String operation);
    double[][] array(Object matrix);
}
