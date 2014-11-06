package org.escalate42.javaz.writer;

import org.escalate42.javaz.common.function.Function;
import org.escalate42.javaz.common.monad.Monad;
import org.escalate42.javaz.common.monoid.Monoid;
import org.escalate42.javaz.common.monoid.MonoidOps;
import org.escalate42.javaz.common.tuple.Tuple2;

/**
 * Created by vadimvd
 * on 05.11.2014 20:25.
 */
@SuppressWarnings("unchecked")
public class WriterT<T, MT extends Monoid<?, MT>, A, MA extends Monad<?, MA>> {
    private final Monad<Tuple2<A, MT>, MA> body;
    private final MonoidOps<T, MT> monoidOps;
    private WriterT(Monad<Tuple2<A, MT>, MA> body, MonoidOps<T, MT> ops) {
        this.body = body;
        this.monoidOps = ops;
    }
    public static <T, MT extends Monoid<?, MT>, A, MA extends Monad<?, MA>> WriterT<T, MT, A, MA> writerTM(Monad<Tuple2<A, MT>, MA> body, final MonoidOps<T, MT> ops) {
        return new WriterT<>(body, ops);
    }
    public static <T, MT extends Monoid<?, MT>, A, MA extends Monad<?, MA>> WriterT<T, MT, A, MA> writerT(Monad<A, MA> body, final MonoidOps<T, MT> ops) {
        return new WriterT<>(
            (Monad<Tuple2<A, MT>, MA>)body.fmap(new Function<A, Tuple2<A, MT>>() {
                @Override public Tuple2<A, MT> apply(A a) {
                    return Tuple2.t(a, ops.mempty());
                }
            }),
            ops
        );
    }
    public Monad<Tuple2<A, T>, MA> run() {
        return (Monad<Tuple2<A, T>, MA>)this.body.fmap(new Function<Tuple2<A, MT>, Object>() {
            @Override public Tuple2<A, T> apply(Tuple2<A, MT> mtaTuple2) {
                return Tuple2.t(mtaTuple2.first, (T) mtaTuple2.second.value());
            }
        });
    }
    public WriterT<T, MT, A, MA> tell(final T t) {
        return writerTM(
            (Monad<Tuple2<A, MT>, MA>) this.body.fmap(new Function<Tuple2<A, MT>, Tuple2<A, MT>>() {
                @Override public Tuple2<A, MT> apply(Tuple2<A, MT> amtTuple2) {
                    return Tuple2.t(amtTuple2.first, amtTuple2.second.mappend(monoidOps.wrap(t)));
                }
            }),
            this.monoidOps
        );
    }
    public <U> WriterT<T, MT, U, MA> fmap(final Function<A, U> function) {
        return writerTM(
            (Monad<Tuple2<U, MT>, MA>) this.body.fmap(new Function<Tuple2<A, MT>, Tuple2<U, MT>>() {
                @Override public Tuple2<U, MT> apply(Tuple2<A, MT> tuple) {
                    return Tuple2.t(function.apply(tuple.first), tuple.second);
                }
            }),
            this.monoidOps
        );
    }
    @Override
    public String toString() {
        return "WriterT{" + this.body + '}';
    }
}
