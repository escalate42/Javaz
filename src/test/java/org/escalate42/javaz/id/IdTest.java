package org.escalate42.javaz.id;

import org.escalate42.javaz.common.function.F;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.escalate42.javaz.id.Id.*;

/**
 * Created by vdubs
 * on 8/25/14.
 */
public class IdTest {

    private final IdOps id = IdOps.id;

    @Test
    public void fmapTest() {
        final F<String, String> f = new F<String, String>() {
            @Override public String apply(String s) { return s + "two"; }
        };
        assertEquals(id("onetwo"), id("one").fmap(f));
    }

    @Test
    public void amapTest() {
        final F<String, String> f = new F<String, String>() {
            @Override public String apply(String s) { return s + "two"; }
        };
        final Id<F<String, String>> fa = id.pure(f);
        assertEquals(id("onetwo"), id("one").amap(fa));
    }

    @Test
    public void mmapTest() {
        final F<String, Id<String>> f = new F<String, Id<String>>() {
            @Override public Id<String> apply(String s) { return id.pure(s + "two"); }
        };
        assertEquals(id("onetwo"), id("one").mmap(f));
    }
}
