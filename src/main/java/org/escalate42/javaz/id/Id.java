package org.escalate42.javaz.id;

import org.escalate42.javaz.common.applicative.Applicative;
import org.escalate42.javaz.common.function.Applicable;
import org.escalate42.javaz.common.function.Function;
import org.escalate42.javaz.common.monad.Monad;

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

    public static <U> Id<U> id(U value) { return new Id<>(value); }

    @Override
    public <U> Id<U> map(Function<T, U> function) {
        return id(function.apply(this.value));
    }

    @Override
    public void foreach(Applicable<T> function) {
        function.apply(this.value);
    }

    @Override
    public <U> Id<U> pure(U value) {
        return id(value);
    }

    @Override
    public <U, MM extends Applicative<Function<T, U>, Id<?>>> Id<U> amap(MM applicativeFunction) {
        return (Id<U>)applicativeFunction.map(new Function<Function<T, U>, Id<U>>() {
            @Override
            public Id<U> apply(Function<T, U> tuf) {
                return map(tuf);
            }
        }).value;
    }

    @Override
    public <U, MM extends Monad<U, Id<?>>> Id<U> flatMap(Function<T, MM> function) {
        return (Id<U>) map(function).value;
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
}
