package org.escalate42.javaz.id;

import org.escalate42.javaz.common.applicative.Applicative;
import org.escalate42.javaz.common.function.Applicable;
import org.escalate42.javaz.common.function.Function;
import org.escalate42.javaz.common.functor.Functor;
import org.escalate42.javaz.common.monad.Monad;
import org.escalate42.javaz.common.monad.MonadOps;

/**
 * Created by vdubs
 * on 8/25/14.
 */
@SuppressWarnings("unchecked")
public final class IdOps implements MonadOps<Id<?>> {

    private IdOps() {}

    public static final IdOps id = new IdOps();

    @Override
    public <T, U, MM extends Functor<T, Id<?>>> Id<U> map(MM functor, Function<T, U> function) {
        return (Id<U>)functor.map(function);
    }

    @Override
    public <T, MM extends Functor<T, Id<?>>> void foreach(MM functor, Applicable<T> function) {
        functor.foreach(function);
    }

    @Override
    public <U> Id<U> pure(U value) { return Id.id(value); }

    @Override
    public <T, U, MM extends Applicative<T, Id<?>>, MF extends Applicative<Function<T, U>, Id<?>>> Id<U> amap(MM app, MF appF) {
        return (Id<U>)app.amap(appF);
    }

    @Override
    public <T, U, MM extends Monad<U, Id<?>>> Id<U> flatMap(Id<?> monad, Function<T, MM> function) {
        return ((Id<T>)monad).flatMap(function);
    }
}
