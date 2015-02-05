package org.escalate42.javaz.trym;

import org.escalate42.javaz.common.monad.MonadOps;

/**
 * Created by vdubs
 * on 8/25/14.
 */
@SuppressWarnings("unchecked")
public final class TryMOps implements MonadOps<TryM<?>> {

    private TryMOps() {}

    public static final TryMOps id = new TryMOps();

    @Override
    public <U> TryM<U> pure(U value) { return TryMImpl.success(value); }
}
