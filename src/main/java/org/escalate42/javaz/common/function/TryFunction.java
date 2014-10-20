package org.escalate42.javaz.common.function;

/**
 * Created by vdubs
 * on 8/22/14.
 */
public interface TryFunction<A, B> { public B apply(A a) throws Throwable; }
