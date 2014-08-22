package org.escalate42.javaz.either;

import org.escalate42.javaz.common.F;

/**
 * Created by vdubs
 * on 8/21/14.
 */
public class Left<L, R> extends Either<L, R> {

    private final L leftValue;

    private Left(L leftValue) {
        if (leftValue == null) { throw new IllegalArgumentException("Value of Left<L, R> can not be null!"); }
        this.leftValue = leftValue;
    }

    public static <L, R> Left<L, R> left(L value) { return new Left<L, R>(value); }

    @Override
    public <U> Either<L, U> fmap(F<R, U> function) { return left(this.leftValue); }

    public <U> Either<U, R> fmapLeft(F<L, U> function) { return left(function.apply(this.leftValue)); }

    public R right() { throw new IllegalAccessError(); }

    public L left() { return this.leftValue; }

    public boolean isRight() { return false; }

    public boolean isLeft() { return true; }

    public <U> U foldRight(U ifLeft, F<R, U> function) { return ifLeft; }

    public <U> U foldLeft(U ifRight, F<L, U> function) { return function.apply(this.leftValue); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Left left = (Left) o;
        return leftValue.equals(left.leftValue);
    }

    @Override
    public int hashCode() { return leftValue.hashCode(); }

    @Override
    public String toString() { return "Left(" + this.leftValue + ')'; }
}
