package org.escalate42.javaz.option;

import org.escalate42.javaz.common.function.Function;

/**
 * Created by vdubs
 * on 8/20/14.
 */
public final class None<T> extends Option<T> {

    public None() {}

    public static <U> None<U> none() { return new None<U>(); }

    @Override
    public boolean isDefined() { return false; }

    @Override
    public T get() { throw new IllegalAccessError(); }

    public T orNull() { return null; }

    @Override
    public T orElse(T elseValue) { return elseValue; }

    @Override
    public Option<T> or(Option<T> elseValue) { return elseValue; }

    @Override
    public <U> Option<U> fmap(Function<T, U> function) { return none(); }

    @Override
    public <U> U fold(U ifNone, Function<T, U> function) {
        return ifNone;
    }

    @Override
    public boolean equals(Object o) {
        return !(o == null || getClass() != o.getClass());
    }

    @Override
    public String toString() { return "None"; }
}
