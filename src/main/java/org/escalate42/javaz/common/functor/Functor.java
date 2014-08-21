package org.escalate42.javaz.common.functor;

import org.escalate42.javaz.common.F;

/**
 * Created by vdubs
 * on 8/21/14.
 */
public interface Functor<T, M extends Functor<?, M>> {
    public <U> M fmap(F<T, U> function);
}
