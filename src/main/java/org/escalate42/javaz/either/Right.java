package org.escalate42.javaz.either;

import org.escalate42.javaz.common.F;

/**
 * Created by vdubs
 * on 8/21/14.
 */
public class Right<L, R> extends Either<L, R> {

    private final R rightValue;

    private Right(R rightValue) {
        if (rightValue == null) { throw new IllegalArgumentException("Value of Right<L, R> can not be null!"); }
        this.rightValue = rightValue;
    }

    public static <L, R> Right<L, R> right(R value) { return new Right<L, R>(value); }

    @Override
    public <U> Either<L, U> fmap(F<R, U> function) {
        return right(function.apply(this.rightValue));
    }

    public <U> Either<U, R> fmapLeft(F<L, U> function) { return right(this.rightValue); }

    public R right() { return this.rightValue; }

    public L left() { throw new IllegalAccessError(); }

    public boolean isRight() { return true; }

    public boolean isLeft() { return false; }
}
