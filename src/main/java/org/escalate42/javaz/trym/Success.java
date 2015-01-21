package org.escalate42.javaz.trym;

import org.escalate42.javaz.common.function.Function;
import org.escalate42.javaz.common.function.FunctionOps;
import org.escalate42.javaz.common.function.TryFunction;

/**
 * Created by vdubs
 * on 8/22/14.
 */
public class Success<T> extends TryMImpl<T> {

    public final T value;

    public Success(T value) {
        if (value == null) { throw new IllegalArgumentException("Value of Success<T> can not be null!"); }
        this.value = value;
    }

    public static <U> Success<U> success(U value) { return new Success<>(value); }

    @Override
    public <U> TryM<U> map(Function<T, U> function) { return mapT(FunctionOps.asTf(function)); }

    @Override
    public void foreach(Function<T, Void> function) {
        function.apply(this.value);
    }

    @Override
    public <U> TryM<U> mapT(TryFunction<T, U> function)  { return TryMImpl.tryM(function, this.value); }

    @Override
    public boolean isSuccess() { return true; }
    @Override
    public boolean isFailure() { return false; }

    @Override
    public T value() { return this.value; }
    @Override
    public Throwable throwable() { throw new IllegalAccessError(); }

    @Override
    public <U> U fold(Function<Throwable, U> ifFailure, Function<T, U> ifSuccess) { return ifSuccess.apply(this.value); }

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
