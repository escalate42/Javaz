package org.escalate42.javaz.common.function.extra;

import org.escalate42.javaz.common.function.F;

/**
 * Created by vdubs
 * on 10/20/14.
 */
public abstract class F2<A1, A2, B> {
    public abstract B apply2(A1 a1, A2 a2);
    public F<A2, B> carry(A1 a) {
        return new Carried(a);
    }
    private class Carried implements F<A2, B> {
        private final A1 a1;
        private Carried(A1 a1) {
            this.a1 = a1;
        }
        @Override public B apply(A2 a2) {
            return apply2(a1, a2);
        }
    }
}
