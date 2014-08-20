package org.escalate42.javaz.maybe;

import org.escalate42.javaz.F;
import org.junit.Test;
import static org.escalate42.javaz.maybe.Maybe.*;
import static org.junit.Assert.*;

/**
 * Created by vdubs
 * on 8/20/14.
 */
public class MaybeTest {

    @Test
    public void maybeTest() {
        assertEquals(just("some string"), maybe("some string"));
        assertEquals(none(), maybe(null));
    }

    @Test
    public void maybeFMapTest() {
        final F<String, String> f = new F<String, String>() {
            @Override
            public String apply(final String s) {
                return s + "two";
            }
        };
        assertEquals(just("onetwo"), maybe("one").fmap(f));
        //noinspection AssertEqualsBetweenInconvertibleTypes
        assertEquals(none(), maybe((String) null).fmap(f));
    }

    @Test
    public void maybeMMapTest() {
        final F<String, Maybe<String>> f = new F<String, Maybe<String>>() {
            @Override
            public Maybe<String> apply(final String s) {
                return just(s + "two");
            }
        };
        assertEquals(just("onetwo"), maybe("one").mmap(f));
        assertEquals(none(), maybe(null));
    }

    @Test
    public void maybeOrTest() {
        final Maybe<String> first = none();
        final Maybe<String> second = maybe("second");
        assertEquals("second", first.or(second).orElse("third"));
    }
}
