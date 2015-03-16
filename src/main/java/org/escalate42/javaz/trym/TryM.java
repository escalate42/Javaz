package org.escalate42.javaz.trym;

import org.escalate42.javaz.common.applicative.Applicative;
import org.escalate42.javaz.common.function.Applicable;
import org.escalate42.javaz.common.function.Function;
import org.escalate42.javaz.common.function.ThrowableFunction;
import org.escalate42.javaz.common.monad.Monad;

/**
 * Created by vdubs
 * on 10/22/14.
 */
public interface TryM<T> extends Monad<T, TryM<?>> {
    <U> TryM<U> map(Function<T, U> function);
    <U, MM extends Applicative<Function<T, U>, TryM<?>>> TryM<U> amap(MM applicativeFunction);
    <U, MM extends Monad<U, TryM<?>>> TryM<U> flatMap(Function<T, MM> function);
    <U> TryM<U> mapT(ThrowableFunction<T, U> function);
    <U> TryM<U> amapT(TryM<ThrowableFunction<T, U>> applicativeFunction);
    <U> TryM<U> flatMapT(ThrowableFunction<T, TryM<U>> function);
    boolean isSuccess();
    boolean isFailure();
    T value();
    Throwable throwable();
    <U> U fold(Function<Throwable, U> ifFailure, Function<T, U> ifSuccess);
    <U> U fold(final U ifFailure, Function<T, U> ifSuccess);
    void foreachFailure(Applicable<Throwable> applicable);
}
