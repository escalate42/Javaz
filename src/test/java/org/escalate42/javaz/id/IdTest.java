package org.escalate42.javaz.id;

import org.escalate42.javaz.common.function.Function;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.escalate42.javaz.id.IdOps.*;

/**
 * Created by vdubs
 * on 8/25/14.
 */
public class IdTest {

    @Test
    public void fmapTest() {
        final Function<String, String> function = s -> s + "two";
        assertEquals(id("onetwo"), id("one").map(function));
    }

    @Test
    public void amapTest() {
        final Function<String, String> function = s -> s + "two";
        final Id<Function<String, String>> fa = id.pure(function);
        assertEquals(id("onetwo"), id("one").amap(fa));
    }

    @Test
    public void mmapTest() {
        final Function<String, Id<String>> function = s -> id.pure(s + "two");
        assertEquals(id("onetwo"), id("one").flatMap(function));
    }
}
