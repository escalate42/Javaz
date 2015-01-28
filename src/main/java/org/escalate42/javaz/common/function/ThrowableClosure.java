package org.escalate42.javaz.common.function;

/**
 * Created by vdubs
 * on 1/22/15.
 */
public interface ThrowableClosure<T> {
    public T apply() throws Throwable;
    public default Closure<T> ommitThrow() {
        return () -> {
            try {
                return apply();
            } catch (Throwable t) {
                throw new RuntimeException(t);
            }
        };
    }
}
