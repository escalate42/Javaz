package org.escalate42.javaz.trym;

import org.escalate42.javaz.common.function.F;
import org.escalate42.javaz.common.function.FOps;
import org.escalate42.javaz.common.function.TF;

/**
 * Created by vdubs
 * on 8/22/14.
 */
public final class Success<T> extends TryM<T> {

    public final T value;

    public Success(T value) {
        if (value == null) { throw new IllegalArgumentException("Value of Success<T> can not be null!"); }
        this.value = value;
    }

    public static <U> Success<U> success(U value) { return new Success<U>(value); }

    @Override
    public <U> TryM<U> fmap(F<T, U> function) { return fmap(FOps.asTf(function)); }

    @Override
    public <U> TryM<U> fmap(TF<T, U> function)  { return TryM.tryM(function, this.value); }

    @Override
    public boolean isSuccess() { return true; }
    @Override
    public boolean isFailure() { return false; }

    @Override
    public T value() { return this.value; }
    @Override
    public Throwable throwable() { throw new IllegalAccessError(); }

    @Override
    public <U> U fold(F<Throwable, U> ifFailure, F<T, U> ifSuccess) { return ifSuccess.apply(this.value); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Success success = (Success) o;
        return this.value.equals(success.value);

    }

    @Override
    public int hashCode() { return this.value.hashCode(); }

    @Override
    public String toString() { return "Success(" + this.value + ')'; }
}
