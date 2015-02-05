package org.escalate42.javaz.common.monad;

import org.escalate42.javaz.common.applicative.ApplicativeOps;
import org.escalate42.javaz.common.function.Function;
import org.escalate42.javaz.common.function.extra.*;

/**
 * Created by vdubs
 * on 8/21/14.
 */
@SuppressWarnings("unchecked")
public interface MonadOps<M extends Monad<?, M>> extends ApplicativeOps<M> {

    public default <T1, T2, U, M1 extends Monad<T1, M>, M2 extends Monad<T2, M>, MU extends Monad<U, M>> MU yieldFor(
            M1 m1, M2 m2, Function2<T1, T2, U> function2
    ) {
        final Monad<Function<T2, U>, M> applicativeFunction = (Monad<Function<T2, U>, M>)m1.map(function2::carry);
        return (MU)m2.amap(applicativeFunction);
    }

    public default <T1, T2, T3, U,
            M1 extends Monad<T1, M>,
            M2 extends Monad<T2, M>,
            M3 extends Monad<T3, M>,
            MU extends Monad<U, M>
    > MU yieldFor(
            M1 m1, M2 m2, M3 m3, Function3<T1, T2, T3, U> function3
    ) {
        return (MU)m1.flatMap((t1) -> (MU)yieldFor(m2, m3, function3.carry(t1)));
    }

    public default <T1, T2, T3, T4, U,
            M1 extends Monad<T1, M>,
            M2 extends Monad<T2, M>,
            M3 extends Monad<T3, M>,
            M4 extends Monad<T4, M>,
            MU extends Monad<U, M>
    > MU yieldFor(
            M1 m1, M2 m2, M3 m3, M4 m4, Function4<T1, T2, T3, T4, U> function4
    ) {
        return (MU)m1.flatMap((t1) -> (MU)yieldFor(m2, m3, m4, function4.carry(t1)));
    }

    public default <T1, T2, T3, T4, T5, U,
            M1 extends Monad<T1, M>,
            M2 extends Monad<T2, M>,
            M3 extends Monad<T3, M>,
            M4 extends Monad<T4, M>,
            M5 extends Monad<T5, M>,
            MU extends Monad<U, M>
    > MU yieldFor(
            M1 m1, M2 m2, M3 m3, M4 m4, M5 m5, Function5<T1, T2, T3, T4, T5, U> function5
    ) {
        return (MU)m1.flatMap((t1) -> (MU)yieldFor(m2, m3, m4, m5, function5.carry(t1)));
    }

    public default <T1, T2, T3, T4, T5, T6, U,
            M1 extends Monad<T1, M>,
            M2 extends Monad<T2, M>,
            M3 extends Monad<T3, M>,
            M4 extends Monad<T4, M>,
            M5 extends Monad<T5, M>,
            M6 extends Monad<T6, M>,
            MU extends Monad<U, M>
    > MU yieldFor(
            M1 m1, M2 m2, M3 m3, M4 m4, M5 m5, M6 m6, Function6<T1, T2, T3, T4, T5, T6, U> function6
    ) {
        return (MU)m1.flatMap((t1) -> (MU)yieldFor(m2, m3, m4, m5, m6, function6.carry(t1)));
    }

    public default <T1, T2, T3, T4, T5, T6, T7, U,
            M1 extends Monad<T1, M>,
            M2 extends Monad<T2, M>,
            M3 extends Monad<T3, M>,
            M4 extends Monad<T4, M>,
            M5 extends Monad<T5, M>,
            M6 extends Monad<T6, M>,
            M7 extends Monad<T7, M>,
            MU extends Monad<U, M>
    > MU yieldFor(
            M1 m1, M2 m2, M3 m3, M4 m4, M5 m5, M6 m6, M7 m7, Function7<T1, T2, T3, T4, T5, T6, T7, U> function7
    ) {
        return (MU)m1.flatMap((t1) -> (MU)yieldFor(m2, m3, m4, m5, m6, m7, function7.carry(t1)));
    }

    public default <T1, T2, T3, T4, T5, T6, T7, T8, U,
            M1 extends Monad<T1, M>,
            M2 extends Monad<T2, M>,
            M3 extends Monad<T3, M>,
            M4 extends Monad<T4, M>,
            M5 extends Monad<T5, M>,
            M6 extends Monad<T6, M>,
            M7 extends Monad<T7, M>,
            M8 extends Monad<T8, M>,
            MU extends Monad<U, M>
    > MU yieldFor(
            M1 m1, M2 m2, M3 m3, M4 m4, M5 m5, M6 m6, M7 m7, M8 m8, Function8<T1, T2, T3, T4, T5, T6, T7, T8, U> function8
    ) {
        return (MU)m1.flatMap((t1) -> (MU)yieldFor(m2, m3, m4, m5, m6, m7, m8, function8.carry(t1)));
    }

    public default <T1, T2, T3, T4, T5, T6, T7, T8, T9, U,
            M1 extends Monad<T1, M>,
            M2 extends Monad<T2, M>,
            M3 extends Monad<T3, M>,
            M4 extends Monad<T4, M>,
            M5 extends Monad<T5, M>,
            M6 extends Monad<T6, M>,
            M7 extends Monad<T7, M>,
            M8 extends Monad<T8, M>,
            M9 extends Monad<T9, M>,
            MU extends Monad<U, M>
    > MU yieldFor(
            M1 m1, M2 m2, M3 m3, M4 m4, M5 m5, M6 m6, M7 m7, M8 m8, M9 m9, Function9<T1, T2, T3, T4, T5, T6, T7, T8, T9, U> function9
    ) {
        return (MU)m1.flatMap((t1) -> (MU)yieldFor(m2, m3, m4, m5, m6, m7, m8, m9, function9.carry(t1)));
    }

    public default <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, U,
            M1 extends Monad<T1, M>,
            M2 extends Monad<T2, M>,
            M3 extends Monad<T3, M>,
            M4 extends Monad<T4, M>,
            M5 extends Monad<T5, M>,
            M6 extends Monad<T6, M>,
            M7 extends Monad<T7, M>,
            M8 extends Monad<T8, M>,
            M9 extends Monad<T9, M>,
            M10 extends Monad<T10, M>,
            MU extends Monad<U, M>
    > MU yieldFor(
            M1 m1, M2 m2, M3 m3, M4 m4, M5 m5, M6 m6, M7 m7, M8 m8, M9 m9, M10 m10, Function10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, U> function10
    ) {
        return (MU)m1.flatMap((t1) -> (MU)yieldFor(m2, m3, m4, m5, m6, m7, m8, m9, m10, function10.carry(t1)));
    }
}
