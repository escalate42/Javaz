package org.escalate42.javaz.trym;

import org.escalate42.javaz.common.function.ThrowableClosure;
import org.escalate42.javaz.common.function.ThrowableFunction;
import org.escalate42.javaz.common.monad.MonadOps;

/**
 * Created by vdubs
 * on 8/25/14.
 */
@SuppressWarnings("unchecked")
public final class TryMOps implements MonadOps<TryM<?>> {

    public static <U> Success<U> success(U value) { return Success.success(value); }
    public static <U> Failure<U> fail(Throwable throwable) { return Failure.fail(throwable); }
    public static <A, B> TryM<B> tryM(ThrowableFunction<A, B> throwing, A a) {
        TryM<B> result;
        try { result = success(throwing.apply(a)); }
        catch (Throwable t) { result = fail(t); }
        return result;
    }
    public static <T> TryM<T> tryM(ThrowableClosure<T> throwing) {
        TryM<T> result;
        try { result = success(throwing.apply()); }
        catch (Throwable t) { result = fail(t); }
        return result;
    }
    public static <A, B> TryF<A, B> tryF(ThrowableFunction<A, B> function) { return TryF.tryF(function); }
    public static <T> TryC<T> tryC(ThrowableClosure<T> closure) { return TryC.tryC(closure); }

    private TryMOps() {}

    public static final TryMOps id = new TryMOps();

    @Override
    public <U> TryM<U> pure(U value) { return success(value); }
}
