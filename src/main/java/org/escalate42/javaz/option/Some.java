package org.escalate42.javaz.option;

import org.escalate42.javaz.common.function.Function;

/**
 * Created by vdubs
 * on 8/20/14.
 */
public final class Some<T> extends Option<T> {

    public final T value;

    public Some(T value) {
        if (value == null) { throw new IllegalArgumentException("Value of Some<T> can not be null!"); }
        this.value = value;
    }

    public static <U> Some<U> some(U value) { return new Some<U>(value); }

    @Override
    public boolean isDefined() { return true; }

    @Override
    public T get() { return this.value; }

    public T orNull() { return this.value; }

    @Override
    public T orElse(T elseValue) { return this.value; }

    @Override
    public Option<T> or(Option<T> elseValue) { return this; }

    @Override
    public <U> Option<U> fmap(Function<T, U> function) {
        return some(function.apply(this.value));
    }

    @Override
    public void foreach(Function<T, Void> function) {
        function.apply(this.value);
    }

    @Override
    public <U> U fold(U ifNone, Function<T, U> function) {
        return function.apply(this.value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Some some = (Some) o;
        return value.equals(some.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() { return "Some(" + this.value + ')'; }
}
