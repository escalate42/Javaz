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
    public Monad<Option<T>, M> run() {
        return this.body;
    }
    public <U> OptionT<U, M> fmap(final Function<T, U> function) {
        return optionT(
            (Monad<Option<U>, M>) this.body.fmap(new Function<Option<T>, Option<U>>() {
                @Override public Option<U> apply(Option<T> tOption) {
                    return tOption.fmap(function);
                }
            })
        );
    }
    public <U> OptionT<U, M> amap(final Option<Function<T, U>> function) {
        return optionT(
            (Monad<Option<U>, M>) this.body.fmap(new Function<Option<T>, Option<U>>() {
                @Override public Option<U> apply(Option<T> tOption) {
                    return tOption.amap(function);
                }
            })
        );
    }
    public <U> OptionT<U, M> mmap(final Function<T, Option<U>> function) {
        return optionT(
            (Monad<Option<U>, M>) this.body.fmap(new Function<Option<T>, Option<U>>() {
                @Override public Option<U> apply(Option<T> tOption) {
                    return tOption.mmap(function);
                }
            })
        );
    }
    public Monad<Boolean, M> isDefined() {
        return (Monad<Boolean, M>)this.body.fmap(new Function<Option<T>, Boolean>() {
            @Override public Boolean apply(Option<T> tOption) {
                return tOption.isDefined();
            }
        });
    }
    public OptionT<T, M> filter(final Function<T, Boolean> function) {
        return optionT(
            (Monad<Option<T>, M>) this.body.fmap(new Function<Option<T>, Option<T>>() {
                @Override public Option<T> apply(Option<T> tOption) {
                    return tOption.filter(function);
                }
            })
        );
    }
    public Monad<T, M> get() {
        return (Monad<T, M>)this.body.fmap(new Function<Option<T>, T>() {
            @Override public T apply(Option<T> tOption) {
                return tOption.get();
            }
        });
    }
    public Monad<T, M> orNull() {
        return (Monad<T, M>)this.body.fmap(new Function<Option<T>, T>() {
            @Override public T apply(Option<T> tOption) {
                return tOption.orNull();
            }
        });
    }
    public Monad<T, M> orElse(final T elseValue) {
        return (Monad<T, M>)this.body.fmap(new Function<Option<T>, T>() {
            @Override public T apply(Option<T> tOption) {
                return tOption.orElse(elseValue);
            }
        });
    }
    public Monad<Option<T>, M> or(final Option<T> elseValue) {
        return (Monad<Option<T>, M>)this.body.fmap(new Function<Option<T>, Option<T>>() {
            @Override public Option<T> apply(Option<T> tOption) {
                return tOption.or(elseValue);
            }
        });
    }
    public <U> Monad<U, M> fold(final U ifNone, final Function<T, U> function) {
        return (Monad<U, M>)this.body.fmap(new Function<Option<T>, U>() {
            @Override public U apply(Option<T> tOption) {
                return tOption.fold(ifNone, function);
            }
        });
    }
    @Override
    public String toString() {
        return "OptionT{" + this.body + '}';
    }
}
