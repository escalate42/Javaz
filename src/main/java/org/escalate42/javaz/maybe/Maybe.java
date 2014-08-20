package org.escalate42.javaz.maybe;

import org.escalate42.javaz.F;

import java.io.Serializable;

/**
 * Created by vdubs
 * on 8/20/14.
 */
public abstract class Maybe<T> implements Serializable {

    private static final long serialVersionUID = 0;

    public static <U> Maybe<U> maybe(final U value) {
        final Maybe<U> none = none();
        return value == null ? none : just(value);
    }
    public static <U> None<U> none() { return None.none(); }
    public static <U> Just<U> just(final U value) { return Just.just(value); }

    public abstract boolean isDefined();

    public abstract T get();

    public abstract T orNull();

    public abstract T orElse(final T elseValue);

    public abstract Maybe<T> or(final Maybe<T> elseValue);

    public abstract <U> Maybe<U> fmap(final F<T, U> function);

    public abstract <U> Maybe<U> mmap(final F<T, Maybe<U>> function);
}
