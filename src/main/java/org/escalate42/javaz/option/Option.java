package org.escalate42.javaz.option;

import org.escalate42.javaz.common.applicative.Applicative;
import org.escalate42.javaz.common.filterable.Filterable;
import org.escalate42.javaz.common.function.Function;
import org.escalate42.javaz.common.monad.Monad;

import java.util.Optional;

/**
 * Created by vdubs
 * on 10/22/14.
 */
public interface Option<T> extends Monad<T, Option<?>>, Filterable<T, Option<?>> {
    public <U> Option<U> map(Function<T, U> function);
    public <U> Option<U> pure(U value);
    public <U, MM extends Applicative<Function<T, U>, Option<?>>> Option<U> amap(MM applicativeFunction);
    public <U, MM extends Monad<U, Option<?>>> Option<U> flatMap(Function<T, MM> function);
    public Option<T> filter(Function<T, Boolean> function);
    public boolean isDefined();
    public T get();
    public T orNull();
    public T orElse(T elseValue);
    public Option<T> or(Option<T> elseValue);
    public <U> U fold(U ifNone, Function<T, U> function);
    public default Optional<T> toOptional() {
        return isDefined() ? Optional.of(get()) : Optional.<T>empty();
    }
}
