package org.escalate42.javaz.common.function.extra;

/**
 * Created by vdubs
 * on 10/20/14.
 */
public abstract class Function3<A1, A2, A3, B> {
    public abstract B apply3(A1 a1, A2 a2, A3 a3);
    public Function2<A2, A3, B> carry(A1 a1) {
        return new Carried(a1);
    }
    private class Carried extends Function2<A2, A3, B> {
        private final A1 a1;
        private Carried(A1 a1) {
            this.a1 = a1;
        }
        @Override public B apply2(A2 a2, A3 a3) {
            return apply3(a1, a2, a3);
        }
    }
}
