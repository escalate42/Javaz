package org.escalate42.javaz.common.function.extra;

/**
 * Created by vdubs
 * on 10/20/14.
 */
public abstract class Function4<A1, A2, A3, A4, B> {
    public abstract B apply4(A1 a1, A2 a2, A3 a3, A4 a4);
    public Function3<A2, A3, A4, B> carry(A1 a1) {
        return new Carried(a1);
    }
    private class Carried extends Function3<A2, A3, A4, B> {
        private final A1 a1;
        private Carried(A1 a1) {
            this.a1 = a1;
        }
        @Override public B apply3(A2 a2, A3 a3, A4 a4) {
            return apply4(a1, a2, a3, a4);
        }
    }
}
