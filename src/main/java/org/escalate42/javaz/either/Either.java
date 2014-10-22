package org.escalate42.javaz.either;

import org.escalate42.javaz.common.applicative.Applicative;
import org.escalate42.javaz.common.function.Function;
import org.escalate42.javaz.common.monad.Monad;

/**
 * Created by vdubs
 * on 10/22/14.
 */
public interface Either<L, R> extends Monad<R, Either<?, ?>> {
    public <U> Either<L, U> fmap(Function<R, U> function);
    public <U> Either<L, U> pure(U value);
    public <U, MM extends Applicative<Function<R, U>, Either<?, ?>>> Either<L, U> amap(MM applicativeFunction);
    public <U, MM extends Monad<U, Either<?, ?>>> Either<L, U> mmap(Function<R, MM> function);
    public <U> Either<U, R> fmapLeft(Function<L, U> function);
    public <U> Either<U, R> amapLeft(Either<?, Function<L, U>> applicativeFunction);
    public <U> Either<U, R> mmapLeft(Function<L, Either<U, R>> function);
    public R right();
    public L left();
    public boolean isRight();
    public boolean isLeft();
    public <U> U foldRight(U ifLeft, Function<R, U> function);
    public <U> U foldLeft(U ifRight, Function<L, U> function);
}
