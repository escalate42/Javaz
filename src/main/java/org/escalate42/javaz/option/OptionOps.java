package org.escalate42.javaz.option;

import org.escalate42.javaz.common.monad.MonadOps;

import java.util.Optional;

/**
 * Created by vdubs
 * on 8/21/14.
 */
public final class OptionOps implements MonadOps<Option<?>> {

    public static <U> Option<U> fromOptional(final Optional<U> optional) {
        return option(optional.orElse(null));
    }
    public static <U> Option<U> option(final U value) {
        final Option<U> none = none();
        return value == null ? none : some(value);
    }
    public static <U> None<U> none() { return None.none(); }
    public static <U> Some<U> some(final U value) { return Some.some(value); }

    private OptionOps() {}

    public static final OptionOps id = new OptionOps();

    @Override
    public <U> Option<U> pure(final U value) { return some(value); }
}
