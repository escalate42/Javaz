package org.escalate42.javaz.either;

import org.escalate42.javaz.common.function.Function;
import org.escalate42.javaz.common.applicative.Applicative;
import org.escalate42.javaz.common.function.extra.Function2;
import org.escalate42.javaz.common.function.extra.Function3;
import org.escalate42.javaz.common.function.extra.Function4;
import org.escalate42.javaz.common.functor.Functor;
import org.escalate42.javaz.common.monad.Monad;
import org.escalate42.javaz.common.monad.MonadOps;

import static org.escalate42.javaz.common.function.extra.carried.Carry.*;

/**
 * Created by vdubs
 * on 8/21/14.
 */
public final class EitherOps implements MonadOps<Either<?, ?>> {

    private EitherOps() {}

    public static final EitherOps id = new EitherOps();

    @Override
    public <U> Either<?, U> pure(final U value) { return EitherImpl.right(value); }

    @Override
    public <T, U, MM extends Applicative<T, Either<?, ?>>, MF extends Applicative<Function<T, U>, Either<?, ?>>> Either<?, U> amap(MM app, MF appF) {
        //not safe, but the easiest way to make pretty API
        //noinspection unchecked
        final Either<?, T> maybe = (Either<?, T>) app;
        return maybe.amap(appF);
    }

    @Override
    public <T, U, MM extends Functor<T, Either<?, ?>>> Either<?, U> fmap(MM functor, Function<T, U> function) {
        //not safe, but the easiest way to make pretty API
        //noinspection unchecked
        final Either<?, T> maybe = (Either<?, T>) functor;
        return maybe.fmap(function);
    }

    @Override
    public <T, MM extends Functor<T, Either<?, ?>>> void foreach(MM functor, Function<T, Void> function) {
        functor.foreach(function);
    }

    @Override
    public <T, U, MM extends Monad<U, Either<?, ?>>> Either<?, U> mmap(Either<?, ?> monad, Function<T, MM> function) {
        //noinspection unchecked
        final Either<?, T> maybe = (Either<?, T>) monad;
        return maybe.mmap(function);
    }

    public <L, R, U> Either<U, R> amapLeft(Either<L, R> app, Either<L, Function<L, U>> appF) {
        return app.amapLeft(appF);
    }

    public <L, R, U> Either<U, R> fmapLeft(Either<L, R> functor, Function<L, U> function) {
        return functor.fmapLeft(function);
    }

    public <L, R, U> Either<U, R> mmapLeft(Either<L, R> monad, Function<L, Either<U, R>> function) {
        return monad.mmapLeft(function);
    }

    public <L, U, A1, A2> Either<L, U> yieldFor(Either<L, A1> a1Either, Either<L, A2> a2Either, Function2<A1, A2, U> function2) {
        final Either<L, U> result;
        if (a1Either.isRight() && a2Either.isRight()) {
            result = EitherImpl.right(function2.apply(a1Either.right(), a2Either.right()));
        } else {
            result = EitherImpl.left(a1Either.isLeft() ? a1Either.left() : a2Either.left());
        }
        return result;
    }

    public <L, U, A1, A2, A3> Either<L, U> yieldFor(Either<L, A1> a1Either, Either<L, A2> a2Either, Either<L, A3> a3Either, Function3<A1, A2, A3, U> function3) {
        final Either<L, U> result;
        if (a1Either.isRight()) {
            result = yieldFor(a2Either, a3Either, carry(a1Either.right(), function3));
        } else {
            result = EitherImpl.left(a1Either.left());
        }
        return result;
    }

    public <L, U, A1, A2, A3, A4> Either<L, U> yieldFor(Either<L, A1> a1Either, Either<L, A2> a2Either, Either<L, A3> a3Either, Either<L, A4> a4Either, Function4<A1, A2, A3, A4, U> function4) {
        final Either<L, U> result;
        if (a1Either.isRight()) {
            result = yieldFor(a2Either, a3Either, a4Either, carry(a1Either.right(), function4));
        } else {
            result = EitherImpl.left(a1Either.left());
        }
        return result;
    }
}

