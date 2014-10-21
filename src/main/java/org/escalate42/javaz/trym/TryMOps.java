package org.escalate42.javaz.trym;

import org.escalate42.javaz.common.applicative.Applicative;
import org.escalate42.javaz.common.function.Function;
import org.escalate42.javaz.common.function.extra.Function2;
import org.escalate42.javaz.common.function.extra.Function3;
import org.escalate42.javaz.common.function.extra.Function4;
import org.escalate42.javaz.common.functor.Functor;
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
    public <T, U, MM extends Functor<T, TryM<?>>> TryM<U> fmap(MM functor, Function<T, U> function) {
        return (TryM<U>)functor.fmap(function);
    }

    @Override
    public <T, MM extends Functor<T, TryM<?>>> void foreach(MM functor, Function<T, Void> function) {
        functor.foreach(function);
    }

    @Override
    public <U> TryM<U> pure(U value) { return TryM.success(value); }

    @Override
    public <T, U, MM extends Applicative<T, TryM<?>>, MF extends Applicative<Function<T, U>, TryM<?>>> TryM<U> amap(MM app, MF appF) {
        return (TryM<U>)app.amap(appF);
    }

    @Override
    public <T, U, MM extends Monad<U, TryM<?>>> TryM<?> mmap(TryM<?> monad, Function<T, MM> function) {
        return ((TryM<T>)monad).mmap(function);
    }

    public <U, A1, A2> TryM<U> yieldFor(TryM<A1> a1TryM, TryM<A2> a2TryM, Function2<A1, A2, U> function2) {
        final TryM<U> result;
        if (a1TryM.isSuccess() && a2TryM.isSuccess()) {
            result = TryM.success(function2.apply2(a1TryM.value(), a2TryM.value()));
        } else {
            result = TryM.fail(a1TryM.isFailure() ? a1TryM.throwable() : a2TryM.throwable());
        }
        return result;
    }

    public <U, A1, A2, A3> TryM<U> yieldFor(TryM<A1> a1TryM, TryM<A2> a2TryM, TryM<A3> a3TryM, Function3<A1, A2, A3, U> function3) {
        final TryM<U> result;
        if (a1TryM.isSuccess()) {
            result = yieldFor(a2TryM, a3TryM, function3.carry(a1TryM.value()));
        } else {
            result = TryM.fail(a1TryM.throwable());
        }
        return result;
    }

    public <U, A1, A2, A3, A4> TryM<U> yieldFor(TryM<A1> a1TryM, TryM<A2> a2TryM, TryM<A3> a3TryM, TryM<A4> a4TryM, Function4<A1, A2, A3, A4, U> function4) {
        final TryM<U> result;
        if (a1TryM.isSuccess()) {
            result = yieldFor(a2TryM, a3TryM, a4TryM, function4.carry(a1TryM.value()));
        } else {
            result = TryM.fail(a1TryM.throwable());
        }
        return result;
    }
}
