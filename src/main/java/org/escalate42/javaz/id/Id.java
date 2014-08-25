package org.escalate42.javaz.id;

import org.escalate42.javaz.common.applicative.Applicative;
import org.escalate42.javaz.common.function.F;
import org.escalate42.javaz.common.monad.Monad;
import org.escalate42.javaz.common.monad.MonadOps;

/**
 * Created by vdubs
 * on 8/25/14.
 */
@SuppressWarnings("unchecked")
public final class Id<T> implements Monad<T, Id<?>> {

    public final T value;

    public Id(T value) {
        if (value == null) { throw new IllegalArgumentException("Value of Id<T> can not be null!"); }
        this.value = value;
    }

    public static <U> Id<U> id(U value) { return new Id<U>(value); }

    @Override
    public <U> Id<U> fmap(F<T, U> function) {
        return id(function.apply(this.value));
    }

    @Override
    public <U> Id<U> pure(U value) {
        return id(value);
    }

    @Override
    public <U, MM extends Applicative<F<T, U>, Id<?>>> Id<U> amap(MM applicativeFunction) {
        return (Id<U>)applicativeFunction.fmap(new F<F<T, U>, Id<U>>() {
            @Override  public Id<U> apply(F<T, U> tuf) { return fmap(tuf); }
        }).value;
    }

    @Override
    public <U, MM extends Monad<U, Id<?>>> Id<U> mmap(F<T, MM> function) {
        return (Id<U>)fmap(function).value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Id id = (Id) o;
        return this.value.equals(id.value);
    }

    @Override
    public int hashCode() { return value.hashCode(); }

    @Override
    public String toString() { return "Id(" + this.value + ')'; }

    @Override
    public MonadOps<Id<?>> ops() { return IdOps.id; }
}
