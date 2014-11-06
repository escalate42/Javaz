package org.escalate42.javaz.writer;

import org.escalate42.javaz.common.function.Function;
import org.escalate42.javaz.common.monoid.Monoid;
import org.escalate42.javaz.common.tuple.Tuple2;
import org.escalate42.javaz.id.Id;

/**
 * Created by vdubs
 * on 11/6/14.
 */
public class Writer<T, MT extends Monoid<T, MT>, A> extends WriterT<T, MT, A, Id<?>> {
    private Writer(A body, MT context) {
        super(Id.id(body), context);
    }
    public static <T, MT extends Monoid<T, MT>, A> Writer<T, MT, A> writer(A body, MT context) {
        return new Writer<>(body, context);
    }
    @Override
    public Id<Tuple2<A, T>> run() {
        return (Id<Tuple2<A, T>>) super.run();
    }
    @Override
    public Id<T> exec() {
        return (Id<T>)super.exec();
    }
    public Writer<T, MT, A> tell(final T t) {
        final WriterT<T, MT, A, Id<?>> wt = super.tell(t);
        return writer(((Id<A>)wt.body).value, wt.context);
    }
    public <U> Writer<T, MT, U> fmap(final Function<A, U> function) {
        final WriterT<T, MT, U, Id<?>> wt = super.fmap(function);
        return writer(((Id<U>)wt.body).value, wt.context);
    }
    @Override
    public String toString() {
        return "Writer{" + this.body + '}';
    }
}
