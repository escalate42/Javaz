package org.escalate42.javaz.maybe;

import org.escalate42.javaz.common.F;
import org.escalate42.javaz.common.applicative.Applicative;
import org.escalate42.javaz.common.applicative.ApplicativeOps;
import org.escalate42.javaz.common.functor.Functor;
import org.escalate42.javaz.common.functor.FunctorOps;
import org.escalate42.javaz.common.monad.Monad;
import org.escalate42.javaz.common.monad.MonadOps;

/**
 * Created by vdubs
 * on 8/21/14.
 */
public class MaybeOps implements FunctorOps<Maybe<?>>, ApplicativeOps<Maybe<?>>, MonadOps<Maybe<?>> {

    private MaybeOps() {}

    public static final MaybeOps id = new MaybeOps();

    @Override
    public <U> Maybe<U> pure(final U value) { return Maybe.just(value); }

    @Override
    public <T, U, MM extends Applicative<T, Maybe<?>>, MF extends Applicative<F<T, U>, Maybe<?>>> Maybe<U> amap(MM app, MF appF) {
        //not safe, but the easiest way to make pretty API
        //noinspection unchecked
        final Maybe<T> maybe = (Maybe<T>) app;
        return maybe.amap(appF);
    }

    @Override
    public <T, U, MM extends Functor<T, Maybe<?>>> Maybe<U> fmap(MM functor, F<T, U> function) {
        //not safe, but the easiest way to make pretty API
        //noinspection unchecked
        final Maybe<T> maybe = (Maybe<T>) functor;
        return maybe.fmap(function);
    }

    @Override
    public <T, U, MM extends Monad<U, Maybe<?>>> Maybe<U> mmap(Maybe<?> monad, F<T, MM> function) {
        //noinspection unchecked
        final Maybe<T> maybe = (Maybe<T>) monad;
        return maybe.mmap(function);
    }
}
