package org.escalate42.javaz.trym;

import org.escalate42.javaz.common.function.Function;
import org.escalate42.javaz.common.function.ThrowableFunction;

/**
 * Created by vdubs
 * on 8/22/14.
 */
public class Failure<T> extends TryMImpl<T> {

    public final Throwable throwable;

    public Failure(Throwable throwable) {
        if (throwable == null) { throw new IllegalArgumentException("Value of Success<T> can not be null!"); }
        this.throwable = throwable;
    }

    public static <U> Failure<U> fail(Throwable throwable) { return new Failure<>(throwable); }

    @Override
    public <U> TryM<U> map(Function<T, U> function) { return fail(this.throwable); }

    @Override
    public void foreach(Function<T, Void> function) {
    }

    @Override
    public <U> TryM<U> mapT(ThrowableFunction<T, U> function) { return fail(this.throwable); }

    @Override
    public boolean isSuccess() { return false; }
    @Override
    public boolean isFailure() { return true; }

    @Override
    public T value() { throw new IllegalAccessError(); }
    @Override
    public Throwable throwable() { return this.throwable; }

    @Override
    public <U> U fold(Function<Throwable, U> ifFailure, Function<T, U> ifSuccess) { return ifFailure.apply(this.throwable); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Failure failure = (Failure) o;
        return this.throwable.equals(failure.throwable);

    }

    @Override
    public int hashCode() { return this.throwable.hashCode(); }

    @Override
    public String toString() { return "Failure(" + this.throwable + ')'; }
}
