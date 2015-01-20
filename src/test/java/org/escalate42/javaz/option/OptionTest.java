package org.escalate42.javaz.option;

import org.junit.Test;
import static org.escalate42.javaz.option.OptionImpl.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by vdubs
 * on 8/20/14.
 */
public class OptionTest {

    @Test
    public void maybeTest() {
        assertEquals(some("some string"), option("some string"));
        assertEquals(none(), option(null));
    }

    @Test
    public void maybeOrTest() {
        final Option<String> first = none();
        final Option<String> second = option("second");
        assertEquals("second", first.or(second).orElse("third"));
    }

    @Test
    public void maybeFMapTest() {
        assertEquals(some("onetwo"), option("one").fmap(s -> s + "two"));
        //noinspection AssertEqualsBetweenInconvertibleTypes
        assertEquals(none(), option((String) null).fmap(s -> s + "two"));
    }

    @Test
    public void maybeAMapTest() {
        final OptionOps id = OptionOps.id;
        assertEquals(some("onetwo"), option("one").amap(id.pure(s -> s + "two")));
    }

    @Test
    public void maybeMMapTest() {
        assertEquals(some("onetwo"), option("one").mmap(s -> some(s + "two")));
        assertEquals(none(), option(null));
    }

    @Test
    public void maybeOpsTest() {
        final OptionOps id = OptionOps.id;
        assertEquals(some("onetwo"), id.fmap(option("one"), s -> s + "two"));
        assertEquals(some("onetwo"), id.amap(option("one"), id.pure(s -> s + "two")));
        assertEquals(some("onetwo"), id.mmap(option("one"), s -> some(s + "two")));
    }

    @Test
    public void yieldForTest() {
        final Option<String> result = OptionOps.id.yieldFor(some("1"), some("2"), some("3"), (s1, s2, s3) -> s1 + s2 + s3);
        assertEquals(some("123"), result);
    }
}
