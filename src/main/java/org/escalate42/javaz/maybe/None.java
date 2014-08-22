package org.escalate42.javaz.maybe;

import org.escalate42.javaz.common.F;

/**
 * Created by vdubs
 * on 8/20/14.
 */
public final class None<T> extends Maybe<T> {

    private None() {}

    public static <U> None<U> none() { return new None<U>(); }

    @Override
    public boolean isDefined() { return false; }

    @Override
    public T get() { throw new IllegalAccessError(); }

    public T orNull() { return null; }

    @Override
    public T orElse(T elseValue) { return elseValue; }

    @Override
    public Maybe<T> or(Maybe<T> elseValue) { return elseValue; }

    @Override
    public <U> Maybe<U> fmap(F<T, U> function) { return none(); }

    @Override
    public <U> U fold(U ifNone, F<T, U> function) {
        return ifNone;
    }

    @Override
    public boolean equals(Object o) {
        return !(o == null || getClass() != o.getClass());
    }

    @Override
    public String toString() { return "None"; }
}
