package org.escalate42.javaz.common.function.extra.carried;

import org.escalate42.javaz.common.function.Function;
import org.escalate42.javaz.common.function.extra.Function2;

/**
 * Created by vdubs
 * on 1/20/15.
 */
public class Carried2<A1, A2, B> implements Function<A2, B> {
    private final A1 a1;
    private final Function2<A1, A2, B> function2;
    public static <A1, A2, B> Function<A2, B> carry(A1 a1, Function2<A1, A2, B> function2) {
        return new Carried2<>(a1, function2);
    }
    private Carried2(A1 a1, Function2<A1, A2, B> function2) {
        this.a1 = a1;
        this.function2 = function2;
    }
    @Override public B apply(A2 a2) {
        return this.function2.apply(a1, a2);
    }
}
