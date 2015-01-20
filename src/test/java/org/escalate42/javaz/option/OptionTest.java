package org.escalate42.javaz.option;

import org.escalate42.javaz.common.function.Function;
import org.escalate42.javaz.common.function.extra.Function3;
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
        final Function<String, String> function = new Function<String, String>() {
            @Override
            public String apply(final String s) {
                return s + "two";
            }
        };
        assertEquals(some("onetwo"), option("one").fmap(function));
        //noinspection AssertEqualsBetweenInconvertibleTypes
        assertEquals(none(), option((String) null).fmap(function));
    }

    @Test
    public void maybeAMapTest() {
        final Function<String, String> function = new Function<String, String>() {
            @Override
            public String apply(final String s) {
                return s + "two";
            }
        };
        final Option<Function<String, String>> appF = OptionOps.id.pure(function);
        assertEquals(some("onetwo"), option("one").amap(appF));
    }

    @Test
    public void maybeMMapTest() {
        final Function<String, Option<String>> function = new Function<String, Option<String>>() {
            @Override
            public Option<String> apply(final String s) {
                return some(s + "two");
            }
        };
        assertEquals(some("onetwo"), option("one").mmap(function));
        assertEquals(none(), option(null));
    }

    @Test
    public void maybeOpsTest() {
        final OptionOps id = OptionOps.id;
        final Function<String, String> function = new Function<String, String>() {
            @Override
            public String apply(final String s) {
                return s + "two";
            }
        };
        final Function<String, Option<String>> fm = new Function<String, Option<String>>() {
            @Override
            public Option<String> apply(final String s) {
                return some(s + "two");
            }
        };
        final Option<Function<String, String>> appF = id.pure(function);
        assertEquals(some("onetwo"), id.fmap(option("one"), function));
        assertEquals(some("onetwo"), id.amap(option("one"), appF));
        assertEquals(some("onetwo"), id.mmap(option("one"), fm));
    }

    @Test
    public void yieldForTest() {
        final Option<String> result = OptionOps.id.yieldFor(
            some("1"), some("2"), some("3"),
            new Function3<String, String, String, String>() {
                @Override public String apply(String s, String s2, String s3) {
                    return s + s2 + s3;
                }
            }
        );
        assertEquals(some("123"), result);
    }
}
