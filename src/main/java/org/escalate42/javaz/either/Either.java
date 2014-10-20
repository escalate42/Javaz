package org.escalate42.javaz.either;

import org.escalate42.javaz.common.function.Function;
import org.escalate42.javaz.common.applicative.Applicative;
import org.escalate42.javaz.common.monad.Monad;
import org.escalate42.javaz.common.monad.MonadOps;

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
    public abstract <U> Either<L, U> fmap(Function<R, U> function);

    @Override
    public <U> Either<L, U> pure(U value) { return right(value); }

    @Override
    public <U, MM extends Applicative<Function<R, U>, Either<?, ?>>> Either<L, U> amap(MM applicativeFunction) {
        //noinspection unchecked
        final Either<L, Either<L, U>> mapped = (Either<L, Either<L, U>>)applicativeFunction.fmap(new Function<Function<R, U>, Either<L, U>>() {
            @Override  public Either<L, U> apply(Function<R, U> ruf) { return fmap(ruf); }
        });
        final Either<L, U> result;
        if (mapped.isLeft()) { result = left(mapped.left()); }
        else { result = mapped.right(); }
        return result;
    }

    @Override
    public <U, MM extends Monad<U, Either<?, ?>>> Either<L, U> mmap(Function<R, MM> function) {
        //noinspection unchecked
        final Either<L, Either<L, U>> mapped = (Either<L, Either<L, U>>)fmap(function);
        final Either<L, U> result;
        if (mapped.isLeft()) { result = left(mapped.left()); }
        else { result = mapped.right(); }
        return result;
    }

    public abstract <U> Either<U, R> fmapLeft(Function<L, U> function);

    public <U> Either<U, R> amapLeft(Either<?, Function<L, U>> applicativeFunction) {
        //noinspection unchecked
        return applicativeFunction.fmap(new Function<Function<L, U>, Either<U, R>>() {
            @Override public Either<U, R> apply(Function<L, U> luf) { return fmapLeft(luf); }
        }).right();
    }

    public <U> Either<U, R> mmapLeft(Function<L, Either<U, R>> function) {
        //noinspection unchecked
        return fmapLeft(function).left();
    }

    public abstract R right();
    public abstract L left();

    public abstract boolean isRight();
    public abstract boolean isLeft();

    public abstract <U> U foldRight(U ifLeft, Function<R, U> function);
    public abstract <U> U foldLeft(U ifRight, Function<L, U> function);

    @Override
    public MonadOps<Either<?, ?>> ops() { return EitherOps.id; }
}
