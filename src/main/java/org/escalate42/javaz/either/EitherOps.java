package org.escalate42.javaz.either;

import org.escalate42.javaz.common.monad.MonadOps;

/**
 * Created by vdubs
 * on 8/21/14.
 */
public final class EitherOps implements MonadOps<Either<?, ?>> {

    private EitherOps() {}

    public static final EitherOps id = new EitherOps();

    @Override
    public <U> Either<?, U> pure(final U value) { return EitherImpl.right(value); }
}

