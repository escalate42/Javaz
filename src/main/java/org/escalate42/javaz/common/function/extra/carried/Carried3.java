package org.escalate42.javaz.common.function.extra.carried;

import org.escalate42.javaz.common.function.extra.Function2;
import org.escalate42.javaz.common.function.extra.Function3;

/**
 * Created by vdubs
 * on 1/20/15.
 */
public class Carried3<A1, A2, A3, B> implements Function2<A2, A3, B> {
    private final A1 a1;
    private final Function3<A1, A2, A3, B> function3;
    public static <A1, A2, A3, B> Function2<A2, A3, B> carry(A1 a1, Function3<A1, A2, A3, B> function3) {
        return new Carried3<>(a1, function3);
    }
    private Carried3(A1 a1, Function3<A1, A2, A3, B> function3) {
        this.a1 = a1;
        this.function3 = function3;
    }
    @Override public B apply(A2 a2, A3 a3) {
        return this.function3.apply(a1, a2, a3);
    }
}
