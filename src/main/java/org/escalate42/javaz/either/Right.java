package org.escalate42.javaz.either;

import org.escalate42.javaz.common.function.F;

/**
 * Created by vdubs
 * on 8/21/14.
 */
public final class Right<L, R> extends Either<L, R> {

    public final R rightValue;

    public Right(R rightValue) {
        if (rightValue == null) { throw new IllegalArgumentException("Value of Right<L, R> can not be null!"); }
        this.rightValue = rightValue;
    }

    public static <L, R> Right<L, R> right(R value) { return new Right<L, R>(value); }

    @Override
    public <U> Either<L, U> fmap(F<R, U> function) {
        return right(function.apply(this.rightValue));
    }

    @Override
    public <U> Either<U, R> fmapLeft(F<L, U> function) { return right(this.rightValue); }

    @Override
    public R right() { return this.rightValue; }

    @Override
    public L left() { throw new IllegalAccessError(); }

    @Override
    public boolean isRight() { return true; }

    @Override
    public boolean isLeft() { return false; }

    @Override
    public <U> U foldRight(U ifLeft, F<R, U> function) { return function.apply(this.rightValue); }

    @Override
    public <U> U foldLeft(U ifRight, F<L, U> function) { return ifRight; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Right right = (Right) o;
        return rightValue.equals(right.rightValue);
    }

    @Override
    public int hashCode() { return this.rightValue.hashCode(); }

    @Override
    public String toString() { return "Right(" + this.rightValue + ')'; }
}
