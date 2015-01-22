package org.escalate42.javaz.trym;

import org.escalate42.javaz.common.applicative.Applicative;
import org.escalate42.javaz.common.function.Function;
import org.escalate42.javaz.common.function.ThrowableFunction;
import org.escalate42.javaz.common.monad.Monad;

/**
 * Created by vdubs
 * on 10/22/14.
 */
public interface TryM<T> extends Monad<T, TryM<?>> {
    public <U> TryM<U> pure(U value);
    public <U> TryM<U> map(Function<T, U> function);
    public <U, MM extends Applicative<Function<T, U>, TryM<?>>> TryM<U> amap(MM applicativeFunction);
    public <U, MM extends Monad<U, TryM<?>>> TryM<U> flatMap(Function<T, MM> function);
    public <U> TryM<U> mapT(ThrowableFunction<T, U> function);
    public <U> TryM<U> amapT(TryM<ThrowableFunction<T, U>> applicativeFunction);
    public <U> TryM<U> flatMapT(ThrowableFunction<T, TryM<U>> function);
    public boolean isSuccess();
    public boolean isFailure();
    public T value();
    public Throwable throwable();
    public <U> U fold(Function<Throwable, U> ifFailure, Function<T, U> ifSuccess);
    public <U> U fold(final U ifFailure, Function<T, U> ifSuccess);
}
