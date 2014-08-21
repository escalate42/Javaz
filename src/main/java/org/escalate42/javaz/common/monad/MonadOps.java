package org.escalate42.javaz.common.monad;

import org.escalate42.javaz.common.F;

/**
 * Created by vdubs
 * on 8/21/14.
 */
public interface MonadOps<M extends Monad<?, M>> {
    public <T, U, MM extends Monad<U, M>> M mmap(M monad, F<T, MM> function);
}
