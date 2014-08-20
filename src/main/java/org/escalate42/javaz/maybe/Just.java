package org.escalate42.javaz.maybe;

import org.escalate42.javaz.F;

/**
 * Created by vdubs
 * on 8/20/14.
 */
public final class Just<T> extends Maybe<T> {

    private final T value;

    private Just(final T value) {
        if (value == null) { throw new IllegalArgumentException("Value of Just<T> can not be null!"); }
        this.value = value;
    }

    public static <U> Just<U> just(final U value) { return new Just<U>(value); }

    @Override
    public boolean isDefined() { return true; }

    @Override
    public T get() { return this.value; }

    public T orNull() { return this.value; }

    @Override
    public T orElse(final T elseValue) { return this.value; }

    @Override
    public Maybe<T> or(final Maybe<T> elseValue) { return this; }

    @Override
    public <U> Maybe<U> fmap(F<T, U> function) {
        return just(function.apply(this.value));
    }

    @Override
    public <U> Maybe<U> mmap(F<T, Maybe<U>> function) {
        return function.apply(this.value);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Just just = (Just) o;
        return value.equals(just.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
