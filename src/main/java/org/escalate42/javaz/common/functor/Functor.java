package org.escalate42.javaz.common.functor;

import org.escalate42.javaz.common.function.Applicable;
import org.escalate42.javaz.common.function.Function;

/**
 * Created by vdubs
 * on 8/21/14.
 */
public interface Functor<T, M extends Functor<?, M>> {
    <U> M map(Function<T, U> function);
    void foreach(Applicable<T> function);
}
