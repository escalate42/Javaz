package org.escalate42.javaz.trym;

import org.escalate42.javaz.common.function.F;
import org.escalate42.javaz.common.function.TF;
import org.escalate42.javaz.common.applicative.Applicative;
import org.escalate42.javaz.common.monad.Monad;

import java.io.Serializable;

/**
 * Created by vdubs
 * on 8/22/14.
 */
public abstract class TryM<T> implements Serializable, Monad<T, TryM<?>> {

    public static <U> Success<U> success(U value) { return Success.success(value); }
    public static <U> Failure<U> fail(Throwable throwable) { return Failure.fail(throwable); }
    public static <A, B> TryM<B> tryM(TF<A, B> throwing, A a) {
        TryM<B> result;
        try { result = success(throwing.apply(a)); }
        catch (Throwable t) { result = fail(t); }
        return result;
    }
    public static <AA, BB> Try<AA, BB> tryF(TF<AA, BB> function) { return Try.tryF(function); }

    @Override
    public <U> TryM<U> pure(U value) { return success(value); }

    @Override
    public abstract <U> TryM<U> fmap(F<T, U> function);

    @Override
    public <U, MM extends Applicative<F<T, U>, TryM<?>>> TryM<U> amap(MM applicativeFunction) {
        //noinspection unchecked
        final TryM<TryM<U>> mapped = (TryM<TryM<U>>)applicativeFunction.fmap(new F<F<T, U>, TryM<U>>() {
            @Override public TryM<U> apply(F<T, U> tuf) { return fmap(tuf); }
        });
        final TryM<U> result;
        if (mapped.isFailure()) { result = fail(mapped.throwable()); }
        else { result = mapped.value(); }
        return result;
    }

    @Override
    public <U, MM extends Monad<U, TryM<?>>> TryM<U> mmap(F<T, MM> function) {
        //noinspection unchecked
        final TryM<TryM<U>> mapped = (TryM<TryM<U>>)fmap(function);
        final TryM<U> result;
        if (mapped.isFailure()) { result = fail(mapped.throwable()); }
        else { result = mapped.value(); }
        return result;
    }

    public abstract <U> TryM<U> fmap(TF<T, U> function);

    public <U> TryM<U> amap(TryM<TF<T, U>> applicativeFunction) {
        final TryM<TryM<U>> mapped = applicativeFunction.fmap(new TF<TF<T, U>, TryM<U>>() {
            @Override public TryM<U> apply(TF<T, U> tuf) { return fmap(tuf); }
        });
        final TryM<U> result;
        if (mapped.isFailure()) { result = fail(mapped.throwable()); }
        else { result = mapped.value(); }
        return result;
    }

    public <U> TryM<U> mmap(TF<T, TryM<U>> function) {
        final TryM<TryM<U>> mapped = fmap(function);
        final TryM<U> result;
        if (mapped.isFailure()) { result = fail(mapped.throwable()); }
        else { result = mapped.value(); }
        return result;
    }

    public abstract boolean isSuccess();
    public abstract boolean isFailure();

    public abstract T value();
    public abstract Throwable throwable();

    public abstract <U> U fold(F<Throwable, U> ifFailure, F<T, U> ifSuccess);
    public <U> U fold(final U ifFailure, F<T, U> ifSuccess) {
        return fold(
                new F<Throwable, U>() { @Override public U apply(Throwable throwable) { return ifFailure; }},
                ifSuccess
        );
    }
}
