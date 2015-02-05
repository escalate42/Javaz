package org.escalate42.javaz.option;

import org.escalate42.javaz.common.monad.MonadOps;

/**
 * Created by vdubs
 * on 8/21/14.
 */
public final class OptionOps implements MonadOps<Option<?>> {

    private OptionOps() {}

    public static final OptionOps id = new OptionOps();

    @Override
    public <U> Option<U> pure(final U value) { return OptionImpl.some(value); }
}
