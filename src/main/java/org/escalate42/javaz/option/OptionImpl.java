package org.escalate42.javaz.option;

import org.escalate42.javaz.common.function.Function;
import org.escalate42.javaz.common.applicative.Applicative;
import org.escalate42.javaz.common.monad.Monad;
import org.escalate42.javaz.common.monad.MonadOps;

import java.io.Serializable;

/**
 * Created by vdubs
 * on 8/20/14.
 */
public abstract class OptionImpl<T> implements Serializable, Option<T> {

    private static final long serialVersionUID = 0;

    public static <U> Option<U> option(final U value) {
        final Option<U> none = none();
        return value == null ? none : some(value);
    }
    public static <U> None<U> none() { return None.none(); }
    public static <U> Some<U> some(final U value) { return Some.some(value); }

    @Override
    public abstract <U> Option<U> fmap(Function<T, U> function);

    @Override
    public abstract Option<T> filter(Function<T, Boolean> predicate);

    @Override
    public <U> Option<U> pure(U value) { return option(value); }

    @Override
    public <U, MM extends Applicative<Function<T, U>, Option<?>>> Option<U> amap(MM applicativeFunction) {
        //noinspection unchecked
        return (Option<U>)(applicativeFunction.fmap(new Function<Function<T, U>, Option<U>>() {
            @Override  public Option<U> apply(Function<T, U> tuf) { return fmap(tuf); }
        }).get());
    }

    @Override
    public <U, MM extends Monad<U, Option<?>>> Option<U> mmap(Function<T, MM> function) {
        //noinspection unchecked
        return (Option<U>)fmap(function).get();
    }

    public abstract boolean isDefined();

    public abstract T get();

    public abstract T orNull();

    public abstract T orElse(T elseValue);

    public abstract Option<T> or(Option<T> elseValue);

    public abstract <U> U fold(U ifNone, Function<T, U> function);
}
