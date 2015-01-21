package org.escalate42.javaz.writer;

import org.escalate42.javaz.common.function.Function;
import org.escalate42.javaz.common.monad.Monad;
import org.escalate42.javaz.common.monoid.Monoid;
import org.escalate42.javaz.common.tuple.Tuple2;

/**
 * Created by vadimvd
 * on 05.11.2014 20:25.
 */
@SuppressWarnings("unchecked")
public class WriterT<T, MT extends Monoid<?, MT>, A, MA extends Monad<?, MA>> {
    protected final Monoid<T, MT> context;
    protected final Monad<A, MA> body;
    protected WriterT(Monad<A, MA> body, Monoid<T, MT> context) {
        this.context = context;
        this.body = body;
    }
    public static <T, MT extends Monoid<?, MT>, A, MA extends Monad<?, MA>> WriterT<T, MT, A, MA> writerT(Monad<A, MA> body, Monoid<T, MT> context) {
        return new WriterT<>(body, context);
    }
    public <MAT extends Monad<Tuple2<A, T>, MA>> MAT run() {
        return (MAT)this.body.map(new Function<A, Tuple2<A, T>>() {
            @Override
            public Tuple2<A, T> apply(A a) {
                return Tuple2.t(a, context.value());
            }
        });
    }
    public Monad<T, MA> exec() {
        return (Monad<T, MA>)this.body.map(a -> context.value());
    }
    public WriterT<T, MT, A, MA> tell(final T t) {
        return writerT(this.body, (Monoid<T, MT>)this.context.mappend(this.context.ops().wrap(t)));
    }
    public <U> WriterT<T, MT, U, MA> map(final Function<A, U> function) {
        return writerT((Monad<U, MA>) this.body.map(function), this.context);
    }
    public <U> WriterT<T, MT, U, MA> flatMap(final Function<A, Monad<U, MA>> function) {
        return writerT((Monad<U, MA>)this.body.flatMap(function), this.context);
    }
    @Override
    public String toString() {
        return "WriterT{" + this.body + '}';
    }
}
