package org.escalate42.javaz.common.tuple;

/**
 * Created by vdubs
 * on 8/25/14.
 */
public class Tuple2<A, B> {

    public final A first;
    public final B second;

    public static <AA, BB> Tuple2<AA, BB> t(AA first, BB second) { return new Tuple2<AA, BB>(first, second); }

    public Tuple2(A first, B second) {
        if (first == null || second == null) { throw new IllegalArgumentException("Values of WriterT<W, A> can not be null!"); }
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tuple2 tuple2 = (Tuple2) o;
        return this.first.equals(tuple2.first) && this.second.equals(tuple2.second);
    }

    @Override
    public int hashCode() {
        int result = first.hashCode();
        result = 31 * result + second.hashCode();
        return result;
    }

    @Override
    public String toString() { return "(" + this.first + ", " + this.second +')'; }
}
