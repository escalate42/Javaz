package org.escalate42.javaz.id;

import org.escalate42.javaz.common.monad.MonadOps;

/**
 * Created by vdubs
 * on 8/25/14.
 */
@SuppressWarnings("unchecked")
public final class IdOps implements MonadOps<Id<?>> {

    public static <U> Id<U> id(U value) { return new Id<>(value); }

    private IdOps() {}

    public static final IdOps id = new IdOps();

    @Override
    public <U> Id<U> pure(U value) { return id(value); }
}
