package org.escalate42.javaz.maybe;

import org.escalate42.javaz.F;

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
    public T orElse(final T elseValue) { return elseValue; }

    @Override
    public Maybe<T> or(final Maybe<T> elseValue) { return elseValue; }

    @Override
    public <U> Maybe<U> fmap(F<T, U> function) { return none(); }

    @Override
    public <U> Maybe<U> mmap(F<T, Maybe<U>> function) { return none(); }

    @Override
    public boolean equals(final Object o) {
        return !(o == null || getClass() != o.getClass());
    }
}
