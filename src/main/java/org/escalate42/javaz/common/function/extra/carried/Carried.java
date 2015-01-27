package org.escalate42.javaz.common.function.extra.carried;

import org.escalate42.javaz.common.function.Closure;
import org.escalate42.javaz.common.function.Function;

/**
 * Created by vdubs
 * on 1/27/15.
 */
public class Carried<A, B> implements Closure<B> {
    private final A a;
    private final Function<A, B> function;
    public static <A, B> Closure<B> carry(A a, Function<A, B> function) {
        return new Carried<>(a, function);
    }
    private Carried(A a, Function<A, B> function) {
        this.a = a;
        this.function = function;
    }
    @Override public B apply() {
        return this.function.apply(this.a);
    }
}
