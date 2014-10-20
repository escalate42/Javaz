package org.escalate42.javaz.maybe;

import org.escalate42.javaz.common.function.F;
import org.escalate42.javaz.common.applicative.Applicative;
import org.escalate42.javaz.common.function.extra.F2;
import org.escalate42.javaz.common.function.extra.F3;
import org.escalate42.javaz.common.function.extra.F4;
import org.escalate42.javaz.common.functor.Functor;
import org.escalate42.javaz.common.monad.Monad;
import org.escalate42.javaz.common.monad.MonadOps;

/**
 * Created by vdubs
 * on 8/21/14.
 */
public final class MaybeOps implements MonadOps<Maybe<?>> {

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

    public <U, A1, A2> Maybe<U> yieldFor(Maybe<A1> a1Maybe, Maybe<A2> a2Maybe, F2<A1, A2, U> function2) {
        final Maybe<U> result;
        if (a1Maybe.isDefined() && a2Maybe.isDefined()) {
            result = Maybe.just(function2.apply2(a1Maybe.get(), a2Maybe.get()));
        } else {
            result = Maybe.none();
        }
        return result;
    }

    public <U, A1, A2, A3> Maybe<U> yieldFor(Maybe<A1> a1Maybe, Maybe<A2> a2Maybe, Maybe<A3> a3Maybe, F3<A1, A2, A3, U> function3) {
        final Maybe<U> result;
        if (a1Maybe.isDefined()) {
            result = yieldFor(a2Maybe, a3Maybe, function3.carry(a1Maybe.get()));
        } else {
            result = Maybe.none();
        }
        return result;
    }

    public <U, A1, A2, A3, A4> Maybe<U> yieldFor(Maybe<A1> a1Maybe, Maybe<A2> a2Maybe, Maybe<A3> a3Maybe, Maybe<A4> a4Maybe, F4<A1, A2, A3, A4, U> function4) {
        final Maybe<U> result;
        if (a1Maybe.isDefined()) {
            result = yieldFor(a2Maybe, a3Maybe, a4Maybe, function4.carry(a1Maybe.get()));
        } else {
            result = Maybe.none();
        }
        return result;
    }
}
