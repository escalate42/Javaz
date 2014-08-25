package org.escalate42.javaz.trym;

import org.escalate42.javaz.common.function.F;
import org.escalate42.javaz.common.function.TF;

/**
 * Created by vdubs
 * on 8/22/14.
 */
public final class Failure<T> extends TryM<T> {

    public final Throwable throwable;

    public Failure(Throwable throwable) {
        if (throwable == null) { throw new IllegalArgumentException("Value of Success<T> can not be null!"); }
        this.throwable = throwable;
    }

    public static <U> Failure<U> fail(Throwable throwable) { return new Failure<U>(throwable); }

    @Override
    public <U> TryM<U> fmap(F<T, U> function) { return fail(this.throwable); }

    @Override
    public <U> TryM<U> fmap(TF<T, U> function) { return fail(this.throwable); }

    @Override
    public boolean isSuccess() { return false; }
    @Override
    public boolean isFailure() { return true; }

    @Override
    public T value() { throw new IllegalAccessError(); }
    @Override
    public Throwable throwable() { return this.throwable; }

    @Override
    public <U> U fold(F<Throwable, U> ifFailure, F<T, U> ifSuccess) { return ifFailure.apply(this.throwable); }

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
