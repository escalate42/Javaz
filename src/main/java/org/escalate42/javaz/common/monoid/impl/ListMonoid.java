package org.escalate42.javaz.common.monoid.impl;

import org.escalate42.javaz.common.monoid.Monoid;
import org.escalate42.javaz.common.monoid.MonoidOps;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vdubs
 * on 11/6/14.
 */
public class ListMonoid<T> implements Monoid<List<T>, ListMonoid<?>> {
    private final List<T> value;
    public ListMonoid(List<T> value) {
        this.value = value;
    }
    public static <T> ListMonoid<T> mempty() { return wrap(new ArrayList<>()); }
    public static <T> ListMonoid<T> wrap(List<T> ts) { return new ListMonoid<>(ts); }
    public static <T> ListMonoid<T> wrapOne(final T ts) { return new ListMonoid<>( new ArrayList<T>(){{ add(ts); }} ); }
    public static <T> List<T> asList(final T ts) { return new ArrayList<T>(){{ add(ts); }}; }
    @Override
    public ListMonoid<T> mappend(Monoid<List<T>, ListMonoid<?>> another) {
        final List<T> newValue = new ArrayList<>();
        newValue.addAll(this.value());
        newValue.addAll(another.value());
        return (ListMonoid<T>)ops().wrap(newValue);
    }
    @Override
    public List<T> value() {
        return this.value;
    }
    @Override
    public MonoidOps<List<T>, ListMonoid<?>> ops() {
        return Ops.listMonoid();
    }
    @Override
    public String toString() { return this.value.toString(); }

    public static class Ops<T> implements MonoidOps<List<T>, ListMonoid<?>> {
        public static <T> Ops<T> listMonoid() { return new Ops<>(); }
        @Override
        public ListMonoid<T> mempty() { return wrap(new ArrayList<>()); }
        @Override
        public ListMonoid<T> wrap(List<T> ts) { return new ListMonoid<>(ts); }
    }
}
