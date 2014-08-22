package org.escalate42.javaz.maybe;

import org.escalate42.javaz.common.function.F;

/**
 * Created by vdubs
 * on 8/20/14.
 */
public final class Just<T> extends Maybe<T> {

    public final T value;

    public Just(T value) {
        if (value == null) { throw new IllegalArgumentException("Value of Just<T> can not be null!"); }
        this.value = value;
    }

    public static <U> Just<U> just(U value) { return new Just<U>(value); }

    @Override
    public boolean isDefined() { return true; }

    @Override
    public T get() { return this.value; }

    public T orNull() { return this.value; }

    @Override
    public T orElse(T elseValue) { return this.value; }

    @Override
    public Maybe<T> or(Maybe<T> elseValue) { return this; }

    @Override
    public <U> Maybe<U> fmap(F<T, U> function) {
        return just(function.apply(this.value));
    }

    @Override
    public <U> U fold(U ifNone, F<T, U> function) {
        return function.apply(this.value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Just just = (Just) o;
        return value.equals(just.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() { return "Just(" + this.value + ')'; }
}
