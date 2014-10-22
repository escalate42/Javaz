package org.escalate42.javaz.trym;

import org.escalate42.javaz.common.applicative.Applicative;
import org.escalate42.javaz.common.function.Function;
import org.escalate42.javaz.common.function.TryFunction;
import org.escalate42.javaz.common.monad.Monad;

/**
 * Created by vdubs
 * on 10/22/14.
 */
public interface TryM<T> extends Monad<T, TryM<?>> {
    public <U> TryM<U> pure(U value);
    public <U> TryM<U> fmap(Function<T, U> function);
    public <U, MM extends Applicative<Function<T, U>, TryM<?>>> TryM<U> amap(MM applicativeFunction);
    public <U, MM extends Monad<U, TryM<?>>> TryM<U> mmap(Function<T, MM> function);
    public <U> TryM<U> fmap(TryFunction<T, U> function);
    public <U> TryM<U> amap(TryM<TryFunction<T, U>> applicativeFunction);
    public <U> TryM<U> mmap(TryFunction<T, TryM<U>> function);
    public boolean isSuccess();
    public boolean isFailure();
    public T value();
    public Throwable throwable();
    public <U> U fold(Function<Throwable, U> ifFailure, Function<T, U> ifSuccess);
    public <U> U fold(final U ifFailure, Function<T, U> ifSuccess);
}
