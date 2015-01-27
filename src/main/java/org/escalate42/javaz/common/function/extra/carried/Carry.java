package org.escalate42.javaz.common.function.extra.carried;

import org.escalate42.javaz.common.function.Closure;
import org.escalate42.javaz.common.function.Function;
import org.escalate42.javaz.common.function.extra.*;

/**
 * Created by vdubs
 * on 1/20/15.
 */
public class Carry {
    public static <A, B> Closure<B> carry(A a, Function<A, B> function) {
        return Carried.carry(a, function);
    }
    public static <A1, A2, B> Function<A2, B> carry(A1 a1, Function2<A1, A2, B> function2) {
        return Carried2.carry(a1, function2);
    }
    public static <A1, A2, A3, B> Function2<A2, A3, B> carry(A1 a1, Function3<A1, A2, A3, B> function3) {
        return Carried3.carry(a1, function3);
    }
    public static <A1, A2, A3, A4, B> Function3<A2, A3, A4, B> carry(A1 a1, Function4<A1, A2, A3, A4, B> function4) {
        return Carried4.carry(a1, function4);
    }
    public static <A1, A2, A3, A4, A5, B> Function4<A2, A3, A4, A5, B> carry(A1 a1, Function5<A1, A2, A3, A4, A5, B> function5) {
        return Carried5.carry(a1, function5);
    }
    public static <A1, A2, A3, A4, A5, A6, B> Function5<A2, A3, A4, A5, A6, B> carry(A1 a1, Function6<A1, A2, A3, A4, A5, A6, B> function6) {
        return Carried6.carry(a1, function6);
    }
    public static <A1, A2, A3, A4, A5, A6, A7, B> Function6<A2, A3, A4, A5, A6, A7, B> carry(A1 a1, Function7<A1, A2, A3, A4, A5, A6, A7, B> function7) {
        return Carried7.carry(a1, function7);
    }
    public static <A1, A2, A3, A4, A5, A6, A7, A8, B> Function7<A2, A3, A4, A5, A6, A7, A8, B> carry(A1 a1, Function8<A1, A2, A3, A4, A5, A6, A7, A8, B> function8) {
        return Carried8.carry(a1, function8);
    }
    public static <A1, A2, A3, A4, A5, A6, A7, A8, A9, B> Function8<A2, A3, A4, A5, A6, A7, A8, A9, B> carry(A1 a1, Function9<A1, A2, A3, A4, A5, A6, A7, A8, A9, B> function9) {
        return Carried9.carry(a1, function9);
    }
    public static <A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, B> Function9<A2, A3, A4, A5, A6, A7, A8, A9, A10, B> carry(A1 a1, Function10<A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, B> function10) {
        return Carried10.carry(a1, function10);
    }
}
