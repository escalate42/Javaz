package org.escalate42.javaz.future;

import org.escalate42.javaz.common.monad.MonadOps;

/**
 * Created by vdubs
 * on 2/5/15.
 */
public class FutureOps implements MonadOps<Future<?>> {

    private FutureOps() {}

    public static final FutureOps id = new FutureOps();

    @Override
    public <U> Future<?> pure(U value) {
        return FutureImpl.completed(value);
    }
}
