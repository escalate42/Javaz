package org.escalate42.javaz.common.function;

/**
 * Created by vdubs
 * on 1/27/15.
 */
public interface ThrowableApplicable<T> {
    public void apply(T t) throws Throwable;
}
