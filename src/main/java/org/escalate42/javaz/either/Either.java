package org.escalate42.javaz.either;

import org.escalate42.javaz.common.F;
import org.escalate42.javaz.common.applicative.Applicative;
import org.escalate42.javaz.common.monad.Monad;

import java.io.Serializable;

/**
 * Created by vdubs
 * on 8/21/14.
 */
public abstract class Either<L, R> implements Serializable, Monad<R, Either<?, ?>> {

    private static final long serialVersionUID = 0;

    public static <L, R> Either<L, R> left(L leftValue) { return Left.left(leftValue); }
    public static <L, R> Either<L, R> right(R rightValue) { return Right.right(rightValue); }

    @Override
    public abstract <U> Either<L, U> fmap(F<R, U> function);

    @Override
    public <U> Either<L, U> pure(U value) { return right(value); }

    @Override
    public <U, MM extends Monad<U, Either<?, ?>>> Either<L, U> mmap(F<R, MM> function) {
        //noinspection unchecked
        return (Either<L, U>)fmap(function).right();
    }

    @Override
    public <U, MM extends Applicative<F<R, U>, Either<?, ?>>> Either<L, U> amap(MM applicativeFunction) {
        //noinspection unchecked
        return (Either<L, U>)(applicativeFunction.fmap(new F<F<R, U>, Either<L, U>>() {
            @Override  public Either<L, U> apply(F<R, U> ruf) { return fmap(ruf); }
        }).right());
    }

    public abstract <U> Either<U, R> fmapLeft(F<L, U> function);

    public <U> Either<U, R> mmapLeft(F<L, Either<U, R>> function) {
        //noinspection unchecked
        return fmapLeft(function).left();
    }

    public <U> Either<U, R> amapLeft(Either<?, F<L, U>> applicativeFunction) {
        //noinspection unchecked
        return applicativeFunction.fmap(new F<F<L, U>, Either<U, R>>() {
            @Override public Either<U, R> apply(F<L, U> luf) { return fmapLeft(luf); }
        }).right();
    }

    public abstract R right();
    public abstract L left();

    public abstract boolean isRight();
    public abstract boolean isLeft();
}
