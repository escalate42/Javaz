package org.escalate42.javaz.option;

import org.escalate42.javaz.common.function.Function;
import org.escalate42.javaz.common.monad.Monad;

/**
 * Created by vadimvd
 * on 05.11.2014 19:23.
 */
@SuppressWarnings("unchecked")
public class OptionT<T, M extends Monad<?, M>> {
    private final Monad<Option<T>, M> body;
    private OptionT(Monad<Option<T>, M> body) {
        this.body = body;
    }
    public static <T, M extends Monad<?, M>> OptionT<T, M> optionT(Monad<Option<T>, M> body) {
        return new OptionT<>(body);
    }
    public <MT extends Monad<Option<T>, M>> MT run() {
        return (MT)this.body;
    }
    public <U> OptionT<U, M> map(final Function<T, U> function) {
        return optionT(
            (Monad<Option<U>, M>) this.body.map(new Function<Option<T>, Option<U>>() {
                @Override
                public Option<U> apply(Option<T> tOption) {
                    return tOption.map(function);
                }
            })
        );
    }
    public <U> OptionT<U, M> amap(final Option<Function<T, U>> function) {
        return optionT(
            (Monad<Option<U>, M>) this.body.map(new Function<Option<T>, Option<U>>() {
                @Override
                public Option<U> apply(Option<T> tOption) {
                    return tOption.amap(function);
                }
            })
        );
    }
    public <U> OptionT<U, M> flatMap(final Function<T, Option<U>> function) {
        return optionT(
            (Monad<Option<U>, M>) this.body.map(new Function<Option<T>, Option<U>>() {
                @Override
                public Option<U> apply(Option<T> tOption) {
                    return tOption.flatMap(function);
                }
            })
        );
    }
    public <MT extends Monad<Boolean, M>> MT isDefined() {
        return (MT)this.body.map(new Function<Option<T>, Boolean>() {
            @Override
            public Boolean apply(Option<T> tOption) {
                return tOption.isDefined();
            }
        });
    }
    public OptionT<T, M> filter(final Function<T, Boolean> function) {
        return optionT(
            (Monad<Option<T>, M>) this.body.map(new Function<Option<T>, Option<T>>() {
                @Override
                public Option<T> apply(Option<T> tOption) {
                    return tOption.filter(function);
                }
            })
        );
    }
    public <MT extends Monad<T, M>> MT get() {
        return (MT)this.body.map(new Function<Option<T>, T>() {
            @Override
            public T apply(Option<T> tOption) {
                return tOption.get();
            }
        });
    }
    public <MT extends Monad<T, M>> MT orNull() {
        return (MT)this.body.map(new Function<Option<T>, T>() {
            @Override
            public T apply(Option<T> tOption) {
                return tOption.orNull();
            }
        });
    }
    public <MT extends Monad<T, M>> MT orElse(final T elseValue) {
        return (MT)this.body.map(new Function<Option<T>, T>() {
            @Override
            public T apply(Option<T> tOption) {
                return tOption.orElse(elseValue);
            }
        });
    }
    public <MT extends Monad<T, M>> MT or(final Option<T> elseValue) {
        return (MT)this.body.map(new Function<Option<T>, Option<T>>() {
            @Override
            public Option<T> apply(Option<T> tOption) {
                return tOption.or(elseValue);
            }
        });
    }
    public <U, MU extends Monad<U, M>> MU fold(final U ifNone, final Function<T, U> function) {
        return (MU)this.body.map(new Function<Option<T>, U>() {
            @Override
            public U apply(Option<T> tOption) {
                return tOption.fold(ifNone, function);
            }
        });
    }
    @Override
    public String toString() {
        return "OptionT{" + this.body + '}';
    }
}
