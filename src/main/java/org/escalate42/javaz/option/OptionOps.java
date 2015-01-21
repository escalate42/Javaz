package org.escalate42.javaz.option;

import org.escalate42.javaz.common.filterable.Filterable;
import org.escalate42.javaz.common.filterable.FilterableOps;
import org.escalate42.javaz.common.function.Function;
import org.escalate42.javaz.common.applicative.Applicative;
import org.escalate42.javaz.common.function.extra.*;
import org.escalate42.javaz.common.functor.Functor;
import org.escalate42.javaz.common.monad.Monad;
import org.escalate42.javaz.common.monad.MonadOps;

/**
 * Created by vdubs
 * on 8/21/14.
 */
public final class OptionOps implements MonadOps<Option<?>>, FilterableOps<Option<?>> {

    private OptionOps() {}

    public static final OptionOps id = new OptionOps();

    @Override
    public <U> Option<U> pure(final U value) { return OptionImpl.some(value); }

    @Override
    public <T, U, MM extends Applicative<T, Option<?>>, MF extends Applicative<Function<T, U>, Option<?>>> Option<U> amap(MM app, MF appF) {
        //not safe, but the easiest way to make pretty API
        //noinspection unchecked
        final Option<T> option = (Option<T>) app;
        return option.amap(appF);
    }

    @Override
    public <T, U, MM extends Functor<T, Option<?>>> Option<U> map(MM functor, Function<T, U> function) {
        //not safe, but the easiest way to make pretty API
        //noinspection unchecked
        final Option<T> option = (Option<T>) functor;
        return option.map(function);
    }

    @Override
    public <T, MM extends Filterable<T, Option<?>>> Option<?> filter(MM filterable, Function<T, Boolean> function) {
        return filterable.filter(function);
    }

    @Override
    public <T, MM extends Functor<T, Option<?>>> void foreach(MM functor, Function<T, Void> function) {
        //not safe, but the easiest way to make pretty API
        //noinspection unchecked
        functor.foreach(function);
    }

    @Override
    public <T, U, MM extends Monad<U, Option<?>>> Option<U> flatMap(Option<?> monad, Function<T, MM> function) {
        //noinspection unchecked
        final Option<T> option = (Option<T>) monad;
        return option.flatMap(function);
    }

    public <U, A1, A2> Option<U> yieldFor(Option<A1> a1Option, Option<A2> a2Option, Function2<A1, A2, U> function2) {
        final Option<U> result;
        if (a1Option.isDefined() && a2Option.isDefined()) {
            result = OptionImpl.some(function2.apply(a1Option.get(), a2Option.get()));
        } else {
            result = OptionImpl.none();
        }
        return result;
    }

    public <U, A1, A2, A3> Option<U> yieldFor(Option<A1> a1Option, Option<A2> a2Option, Option<A3> a3Option, Function3<A1, A2, A3, U> function3) {
        final Option<U> result;
        if (a1Option.isDefined()) {
            result = yieldFor(a2Option, a3Option, function3.carry(a1Option.get()));
        } else {
            result = OptionImpl.none();
        }
        return result;
    }

    public <U, A1, A2, A3, A4> Option<U> yieldFor(Option<A1> a1Option, Option<A2> a2Option, Option<A3> a3Option, Option<A4> a4Option, Function4<A1, A2, A3, A4, U> function4) {
        final Option<U> result;
        if (a1Option.isDefined()) {
            result = yieldFor(a2Option, a3Option, a4Option, function4.carry(a1Option.get()));
        } else {
            result = OptionImpl.none();
        }
        return result;
    }
}
