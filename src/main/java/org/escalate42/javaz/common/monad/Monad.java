package org.escalate42.javaz.common.monad;

import org.escalate42.javaz.common.F;
import org.escalate42.javaz.common.applicative.Applicative;

/**
 * Created by vdubs
 * on 8/21/14.
 */
public interface Monad<T, M extends Monad<?, M>> extends Applicative<T, M> {
    public <U, MM extends Monad<U, M>> M mmap(F<T, MM> function);
    // TODO: implement (>>)
}
