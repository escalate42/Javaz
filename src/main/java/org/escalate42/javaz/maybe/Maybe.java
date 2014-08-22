package org.escalate42.javaz.maybe;

import org.escalate42.javaz.common.F;
import org.escalate42.javaz.common.applicative.Applicative;
import org.escalate42.javaz.common.monad.Monad;

import java.io.Serializable;

/**
 * Created by vdubs
 * on 8/20/14.
 */
public abstract class Maybe<T> implements Serializable, Monad<T, Maybe<?>> {

    private static final long serialVersionUID = 0;

    public static <U> Maybe<U> maybe(final U value) {
        final Maybe<U> none = none();
        return value == null ? none : just(value);
    }
    public static <U> None<U> none() { return None.none(); }
    public static <U> Just<U> just(final U value) { return Just.just(value); }

    @Override
    public abstract <U> Maybe<U> fmap(F<T, U> function);

    @Override
    public <U> Maybe<U> pure(U value) { return maybe(value); }

    @Override
    public <U, MM extends Applicative<F<T, U>, Maybe<?>>> Maybe<U> amap(MM applicativeFunction) {
        //noinspection unchecked
        return (Maybe<U>)(applicativeFunction.fmap(new F<F<T, U>, Maybe<U>>() {
            @Override  public Maybe<U> apply(F<T, U> tuf) { return fmap(tuf); }
        }).get());
    }

    @Override
    public <U, MM extends Monad<U, Maybe<?>>> Maybe<U> mmap(F<T, MM> function) {
        //noinspection unchecked
        return (Maybe<U>)fmap(function).get();
    }

    public abstract boolean isDefined();

    public abstract T get();

    public abstract T orNull();

    public abstract T orElse(T elseValue);

    public abstract Maybe<T> or(Maybe<T> elseValue);

    public abstract <U> U fold(U ifNone, F<T, U> function);
}
