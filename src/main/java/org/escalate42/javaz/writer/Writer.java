package org.escalate42.javaz.writer;

import org.escalate42.javaz.common.function.Function;
import org.escalate42.javaz.common.monoid.Monoid;
import org.escalate42.javaz.common.tuple.Tuple2;
import org.escalate42.javaz.id.Id;

/**
 * Created by vdubs
 * on 11/6/14.
 */
@SuppressWarnings("unchecked")
public class Writer<T, MT extends Monoid<?, MT>, A> extends WriterT<T, MT, A, Id<?>> {
    private Writer(A body, Monoid<T, MT> context) {
        super(Id.id(body), context);
    }
    public static <T, MT extends Monoid<?, MT>, A> Writer<T, MT, A> writer(A body, Monoid<T, MT> context) {
        return new Writer<>(body, context);
    }
    @Override
    public Id<Tuple2<A, T>> run() {
        return (Id<Tuple2<A, T>>) super.run();
    }
    public Tuple2<A, T> runWriter() {
        return ((Id<Tuple2<A, T>>) super.run()).value;
    }
    @Override
    public Id<T> exec() {
        return (Id<T>)super.exec();
    }
    public T execWriter() {
        return ((Id<T>)super.exec()).value;
    }
    @Override
    public Writer<T, MT, A> tell(final T t) {
        final WriterT<T, MT, A, Id<?>> wt = super.tell(t);
        return writer(((Id<A>)wt.body).value, wt.context);
    }
    @Override
    public <U> Writer<T, MT, U> map(final Function<A, U> function) {
        final WriterT<T, MT, U, Id<?>> wt = super.map(function);
        return writer(((Id<U>)wt.body).value, wt.context);
    }
    @Override
    public String toString() {
        return "Writer{" + this.body + '}';
    }
}
