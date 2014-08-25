package org.escalate42.javaz.common.monad;

import org.escalate42.javaz.common.applicative.ApplicativeOps;
import org.escalate42.javaz.common.function.F;

/**
 * Created by vdubs
 * on 8/21/14.
 */
public interface MonadOps<M extends Monad<?, M>> extends ApplicativeOps<M> {
    public <T, U, MM extends Monad<U, M>> M mmap(M monad, F<T, MM> function);
}
