package org.escalate42.javaz.trym;

import org.escalate42.javaz.common.function.Function;
import org.escalate42.javaz.common.function.ThrowableFunction;
import org.escalate42.javaz.common.applicative.Applicative;
import org.escalate42.javaz.common.monad.Monad;

import java.io.Serializable;

import static org.escalate42.javaz.trym.TryMOps.*;

/**
 * Created by vdubs
 * on 8/22/14.
 */
public abstract class TryMImpl<T> implements Serializable, TryM<T> {

    @Override
    public abstract <U> TryM<U> map(Function<T, U> function);

    @Override
    public <U, MM extends Applicative<Function<T, U>, TryM<?>>> TryM<U> amap(MM applicativeFunction) {
        //noinspection unchecked
        final TryM<TryM<U>> mapped = (TryM<TryM<U>>)applicativeFunction.map(new Function<Function<T, U>, TryM<U>>() {
            @Override
            public TryM<U> apply(Function<T, U> tuf) {
                return map(tuf);
            }
        });
        final TryM<U> result;
        if (mapped.isFailure()) { result = fail(mapped.throwable()); }
        else { result = mapped.value(); }
        return result;
    }

    @Override
    public <U, MM extends Monad<U, TryM<?>>> TryM<U> flatMap(Function<T, MM> function) {
        //noinspection unchecked
        final TryM<TryM<U>> mapped = (TryM<TryM<U>>) map(function);
        final TryM<U> result;
        if (mapped.isFailure()) { result = fail(mapped.throwable()); }
        else { result = mapped.value(); }
        return result;
    }

    public abstract <U> TryM<U> mapT(ThrowableFunction<T, U> function);

    public <U> TryM<U> amapT(TryM<ThrowableFunction<T, U>> applicativeFunction) {
        final TryM<TryM<U>> mapped = applicativeFunction.mapT(new ThrowableFunction<ThrowableFunction<T, U>, TryM<U>>() {
            @Override
            public TryM<U> apply(ThrowableFunction<T, U> tuf) {
                return mapT(tuf);
            }
        });
        final TryM<U> result;
        if (mapped.isFailure()) { result = fail(mapped.throwable()); }
        else { result = mapped.value(); }
        return result;
    }

    public <U> TryM<U> flatMapT(ThrowableFunction<T, TryM<U>> function) {
        final TryM<TryM<U>> mapped = mapT(function);
        final TryM<U> result;
        if (mapped.isFailure()) { result = fail(mapped.throwable()); }
        else { result = mapped.value(); }
        return result;
    }

    public abstract boolean isSuccess();
    public abstract boolean isFailure();

    public abstract T value();
    public abstract Throwable throwable();

    public abstract <U> U fold(Function<Throwable, U> ifFailure, Function<T, U> ifSuccess);
    public <U> U fold(final U ifFailure, Function<T, U> ifSuccess) {
        return fold(
                (Function<Throwable, U>) throwable -> ifFailure,
                ifSuccess
        );
    }
}
