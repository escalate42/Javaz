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
    <U> Option<U> map(Function<T, U> function);
    <U, MM extends Applicative<Function<T, U>, Option<?>>> Option<U> amap(MM applicativeFunction);
    <U, MM extends Monad<U, Option<?>>> Option<U> flatMap(Function<T, MM> function);
    Option<T> filter(Function<T, Boolean> function);
    boolean isDefined();
    T get();
    T orNull();
    T orElse(T elseValue);
    Option<T> or(Option<T> elseValue);
    <U> U fold(U ifNone, Function<T, U> function);
    default Optional<T> toOptional() {
        return isDefined() ? Optional.of(get()) : Optional.<T>empty();
    }
}
