package org.escalate42.javaz.common.monad;

import org.escalate42.javaz.common.function.Function;
import org.escalate42.javaz.common.applicative.Applicative;

/**
 * Created by vdubs
 * on 8/21/14.
 */
public interface Monad<T, M extends Monad<?, M>> extends Applicative<T, M> {
    <U, MM extends Monad<U, M>> M flatMap(Function<T, MM> function);
    // TODO: implement (>>)
}
