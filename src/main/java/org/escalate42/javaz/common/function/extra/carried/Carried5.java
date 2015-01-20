package org.escalate42.javaz.common.function.extra.carried;

import org.escalate42.javaz.common.function.extra.*;

/**
 * Created by vdubs
 * on 1/20/15.
 */
public class Carried5<A1, A2, A3, A4, A5, B> implements Function4<A2, A3, A4, A5, B> {
    private final A1 a1;
    private final Function5<A1, A2, A3, A4, A5, B> function5;
    public static <A1, A2, A3, A4, A5, B> Function4<A2, A3, A4, A5, B> carry(A1 a1, Function5<A1, A2, A3, A4, A5, B> function5) {
        return new Carried5<>(a1, function5);
    }
    private Carried5(A1 a1, Function5<A1, A2, A3, A4, A5, B> function5) {
        this.a1 = a1;
        this.function5 = function5;
    }
    @Override public B apply(A2 a2, A3 a3, A4 a4, A5 a5) {
        return this.function5.apply(a1, a2, a3, a4, a5);
    }
}