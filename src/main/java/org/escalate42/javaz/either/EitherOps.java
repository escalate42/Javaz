package org.escalate42.javaz.either;

import org.escalate42.javaz.common.monad.MonadOps;

/**
 * Created by vdubs
 * on 8/21/14.
 */
public final class EitherOps implements MonadOps<Either<?, ?>> {

    public static <L, R> Either<L, R> left(L leftValue) { return Left.left(leftValue); }
    public static <L, R> Either<L, R> right(R rightValue) { return Right.right(rightValue); }

    private EitherOps() {}

    public static final EitherOps id = new EitherOps();

    @Override
    public <U> Either<?, U> pure(final U value) { return right(value); }
}

