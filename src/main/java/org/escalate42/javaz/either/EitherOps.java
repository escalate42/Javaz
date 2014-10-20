package org.escalate42.javaz.either;

import org.escalate42.javaz.common.function.Function;
import org.escalate42.javaz.common.applicative.Applicative;
import org.escalate42.javaz.common.functor.Functor;
import org.escalate42.javaz.common.monad.Monad;
import org.escalate42.javaz.common.monad.MonadOps;

/**
 * Created by vdubs
 * on 8/21/14.
 */
public final class EitherOps implements MonadOps<Either<?, ?>> {

    private EitherOps() {}

    public static final EitherOps id = new EitherOps();

    @Override
    public <U> Either<?, U> pure(final U value) { return Either.right(value); }

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
}

