package org.escalate42.javaz.trym;

import org.escalate42.javaz.common.applicative.Applicative;
import org.escalate42.javaz.common.applicative.ApplicativeOps;
import org.escalate42.javaz.common.function.F;
import org.escalate42.javaz.common.functor.Functor;
import org.escalate42.javaz.common.functor.FunctorOps;
import org.escalate42.javaz.common.monad.Monad;
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
    public <T, U, MM extends Functor<T, TryM<?>>> TryM<U> fmap(MM functor, F<T, U> function) {
        return (TryM<U>)functor.fmap(function);
    }

    @Override
    public <U> TryM<U> pure(U value) { return TryM.success(value); }

    @Override
    public <T, U, MM extends Applicative<T, TryM<?>>, MF extends Applicative<F<T, U>, TryM<?>>> TryM<U> amap(MM app, MF appF) {
        return (TryM<U>)app.amap(appF);
    }

    @Override
    public <T, U, MM extends Monad<U, TryM<?>>> TryM<?> mmap(TryM<?> monad, F<T, MM> function) {
        return ((TryM<T>)monad).mmap(function);
    }
}
