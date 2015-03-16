package org.escalate42.javaz.either;

import org.escalate42.javaz.common.applicative.Applicative;
import org.escalate42.javaz.common.function.Function;
import org.escalate42.javaz.common.monad.Monad;
import org.escalate42.javaz.option.Option;

/**
 * Created by vdubs
 * on 10/22/14.
 */
public interface Either<L, R> extends Monad<R, Either<?, ?>> {
    <U> Either<L, U> map(Function<R, U> function);
    <U, MM extends Applicative<Function<R, U>, Either<?, ?>>> Either<L, U> amap(MM applicativeFunction);
    <U, MM extends Monad<U, Either<?, ?>>> Either<L, U> flatMap(Function<R, MM> function);
    <U> Either<U, R> mapLeft(Function<L, U> function);
    <U> Either<U, R> amapLeft(Either<?, Function<L, U>> applicativeFunction);
    <U> Either<U, R> flatMapLeft(Function<L, Either<U, R>> function);
    R right();
    L left();
    boolean isRight();
    boolean isLeft();
    <U> U foldRight(U ifLeft, Function<R, U> function);
    <U> U foldLeft(U ifRight, Function<L, U> function);
    Option<R> asOption();
}
