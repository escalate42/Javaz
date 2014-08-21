package org.escalate42.javaz.either;

import org.escalate42.javaz.common.F;

/**
 * Created by vdubs
 * on 8/21/14.
 */
public class Left<L, R> extends Either<L, R> {

    private final L leftValue;

    private Left(L leftValue) { this.leftValue = leftValue; }

    public static <L, R> Left<L, R> left(L value) { return new Left<L, R>(value); }

    @Override
    public <U> Either<L, U> fmap(F<R, U> function) { return left(this.leftValue); }

    public <U> Either<U, R> fmapLeft(F<L, U> function) { return left(function.apply(this.leftValue)); }

    public R right() { throw new IllegalAccessError(); }

    public L left() { return this.leftValue; }

    public boolean isRight() { return false; }

    public boolean isLeft() { return true; }
}
