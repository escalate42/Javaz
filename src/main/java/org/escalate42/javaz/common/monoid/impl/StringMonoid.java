package org.escalate42.javaz.common.monoid.impl;

import org.escalate42.javaz.common.monoid.Monoid;
import org.escalate42.javaz.common.monoid.MonoidOps;

/**
 * Created by vadimvd
 * on 05.11.2014 20:39.
 */
public class StringMonoid implements Monoid<String, StringMonoid> {
    private final String value;
    private StringMonoid(String value) {
        this.value = value;
    }
    public static StringMonoid mempty() { return wrap(""); }
    public static StringMonoid wrap(String s) { return new StringMonoid(s); }
    @Override
    public StringMonoid mappend(Monoid<String, StringMonoid> another) {
        return Ops.stringMonoid.wrap(this.value() + another.value());
    }
    @Override
    public String value() {
        return this.value;
    }
    @Override
    public MonoidOps<String, StringMonoid> ops() {
        return Ops.stringMonoid;
    }
    @Override
    public String toString() {
        return value;
    }

    public static class Ops implements MonoidOps<String, StringMonoid> {
        public static final Ops stringMonoid = new Ops();
        private Ops() {}
        @Override
        public StringMonoid mempty() { return wrap(""); }
        @Override
        public StringMonoid wrap(String s) { return new StringMonoid(s); }
    }
}
