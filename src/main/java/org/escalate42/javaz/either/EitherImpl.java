package org.escalate42.javaz.either;

import org.escalate42.javaz.common.function.Function;
import org.escalate42.javaz.common.applicative.Applicative;
import org.escalate42.javaz.common.monad.Monad;
import org.escalate42.javaz.option.Option;

import java.io.Serializable;

import static org.escalate42.javaz.option.OptionOps.*;

/**
 * Created by vdubs
 * on 8/21/14.
 */
public abstract class EitherImpl<L, R> implements Serializable, Either<L, R> {

    private static final long serialVersionUID = 0;

    @Override
    public abstract <U> Either<L, U> map(Function<R, U> function);

    @Override
    public <U, MM extends Applicative<Function<R, U>, Either<?, ?>>> Either<L, U> amap(MM applicativeFunction) {
        //noinspection unchecked
        final Either<L, Either<L, U>> mapped = (Either<L, Either<L, U>>)applicativeFunction.map(new Function<Function<R, U>, Either<L, U>>() {
            @Override
            public Either<L, U> apply(Function<R, U> ruf) {
                return map(ruf);
            }
        });
        final Either<L, U> result;
        if (mapped.isLeft()) { result = EitherOps.left(mapped.left()); }
        else { result = mapped.right(); }
        return result;
    }

    @Override
    public <U, MM extends Monad<U, Either<?, ?>>> Either<L, U> flatMap(Function<R, MM> function) {
        //noinspection unchecked
        final Either<L, Either<L, U>> mapped = (Either<L, Either<L, U>>) map(function);
        final Either<L, U> result;
        if (mapped.isLeft()) { result = EitherOps.left(mapped.left()); }
        else { result = mapped.right(); }
        return result;
    }

    public abstract <U> Either<U, R> mapLeft(Function<L, U> function);

    public <U> Either<U, R> amapLeft(Either<?, Function<L, U>> applicativeFunction) {
        //noinspection unchecked
        final Either<Either<U, R>, R> mapped = (Either<Either<U, R>, R>)applicativeFunction.map(new Function<Function<L, U>, Either<U, R>>() {
            @Override public Either<U, R> apply(Function<L, U> luf) {
                return mapLeft(luf);
            }
        });
        final Either<U, R> result;
        if (mapped.isLeft()) { result = mapped.left(); }
        else { result = EitherOps.right(mapped.right()); }
        return result;
    }

    public <U> Either<U, R> flatMapLeft(Function<L, Either<U, R>> function) {
        //noinspection unchecked
        return mapLeft(function).left();
    }

    public abstract R right();
    public abstract L left();

    public abstract boolean isRight();
    public abstract boolean isLeft();

    public abstract <U> U foldRight(U ifLeft, Function<R, U> function);
    public abstract <U> U foldLeft(U ifRight, Function<L, U> function);

    public Option<R> asOption() {
        return isRight() ? some(right()) : none();
    }
}
