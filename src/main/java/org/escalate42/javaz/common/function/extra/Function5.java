package org.escalate42.javaz.common.function.extra;

/**
 * Created by vadimvd
 * on 21.10.2014 22:00.
 */
public abstract class Function5<A1, A2, A3, A4, A5, B> {
    public abstract B apply5(A1 a1, A2 a2, A3 a3, A4 a4, A5 a5);
    public Function4<A2, A3, A4, A5, B> carry(A1 a1) {
        return new Carried(a1);
    }
    private class Carried extends Function4<A2, A3, A4, A5, B> {
        private final A1 a1;
        private Carried(A1 a1) {
            this.a1 = a1;
        }
        @Override public B apply4(A2 a2, A3 a3, A4 a4, A5 a5) {
            return apply5(a1, a2, a3, a4, a5);
        }
    }
}