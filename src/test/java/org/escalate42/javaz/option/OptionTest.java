package org.escalate42.javaz.option;

import org.escalate42.javaz.common.function.Function;
import org.junit.Test;

import java.util.Optional;

import static org.escalate42.javaz.option.OptionImpl.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by vdubs
 * on 8/20/14.
 */
public class OptionTest {

    @Test
    public void fromToOptionalTest() {
        final Function<Optional<String>, Optional<String>> fun =
                (o) -> fromOptional(o.map(String::toUpperCase)).map(String::toLowerCase).toOptional();
        final Optional<String> optional = Optional.of("value");
        final Optional<String> emptyOptional = Optional.empty();
        final Optional<String> processedNonEmptyOptional = fun.apply(optional);
        final Optional<String> processedEmptyOptional = fun.apply(emptyOptional);
        assertEquals(optional, processedNonEmptyOptional);
        assertEquals(emptyOptional, processedEmptyOptional);
    }

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
        assertEquals(some("onetwo"), option("one").map(s -> s + "two"));
        //noinspection AssertEqualsBetweenInconvertibleTypes
        assertEquals(none(), option((String) null).map(s -> s + "two"));
    }

    @Test
    public void maybeAMapTest() {
        final OptionOps id = OptionOps.id;
        assertEquals(some("onetwo"), option("one").amap(id.pure(s -> s + "two")));
    }

    @Test
    public void maybeMMapTest() {
        assertEquals(some("onetwo"), option("one").flatMap(s -> some(s + "two")));
        assertEquals(none(), option(null));
    }

    @Test
    public void maybeOpsTest() {
        final OptionOps id = OptionOps.id;
        assertEquals(some("onetwo"), id.map(option("one"), s -> s + "two"));
        assertEquals(some("onetwo"), id.amap(option("one"), id.pure(s -> s + "two")));
        assertEquals(some("onetwo"), id.flatMap(option("one"), s -> some(s + "two")));
    }

    @Test
    public void yieldForTest() {
        final Option<String> result = OptionOps.id.yieldFor(
                some("1"), some("2"), some("3"),
                (s1, s2, s3) -> s1 + s2 + s3
        );
        assertEquals(some("123"), result);
    }
}
