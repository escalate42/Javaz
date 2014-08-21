package org.escalate42.javaz.either;

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
public class EitherOps implements FunctorOps<Either<?, ?>>, ApplicativeOps<Either<?, ?>>, MonadOps<Either<?, ?>> {

    private EitherOps() {}

    public static final EitherOps id = new EitherOps();

    @Override
    public <U> Either<?, U> pure(final U value) { return Either.right(value); }

    @Override
    public <T, U, MM extends Applicative<T, Either<?, ?>>, MF extends Applicative<F<T, U>, Either<?, ?>>> Either<?, U> amap(MM app, MF appF) {
        //not safe, but the easiest way to make pretty API
        //noinspection unchecked
        final Either<?, T> maybe = (Either<?, T>) app;
        return maybe.amap(appF);
    }

    @Override
    public <T, U, MM extends Functor<T, Either<?, ?>>> Either<?, U> fmap(MM functor, F<T, U> function) {
        //not safe, but the easiest way to make pretty API
        //noinspection unchecked
        final Either<?, T> maybe = (Either<?, T>) functor;
        return maybe.fmap(function);
    }

    @Override
    public <T, U, MM extends Monad<U, Either<?, ?>>> Either<?, U> mmap(Either<?, ?> monad, F<T, MM> function) {
        //noinspection unchecked
        final Either<?, T> maybe = (Either<?, T>) monad;
        return maybe.mmap(function);
    }
}

