package org.escalate42.javaz.either;

import org.escalate42.javaz.common.function.Function;
import org.junit.Test;
import static org.escalate42.javaz.either.Either.*;
import static org.junit.Assert.*;

/**
 * Created by vdubs
 * on 8/22/14.
 */
@SuppressWarnings("AssertEqualsBetweenInconvertibleTypes")
public class EitherTest {

    @Test
    public void fmapTest() {
        final Function<String, String> function = new Function<String, String>() {
            @Override public String apply(String s) { return s + "two"; }
        };
        final Either<String, String> left = left("one");
        assertEquals(left("one"), left.fmap(function));
        assertEquals(right("onetwo"), right("one").fmap(function));
    }

    @Test
    public void amapTest() {
        final Function<String, String> function = new Function<String, String>() {
            @Override public String apply(String s) { return s + "two"; }
        };
        final Either<?, Function<String, String>> rightF = EitherOps.id.pure(function);
        final Either<String, Function<String, String>> leftF = left("nofun");
        final Either<String, String> left = left("one");
        assertEquals(left("one"), left.amap(rightF));
        assertEquals(left("nofun"), left.amap(leftF));
        assertEquals(right("onetwo"), right("one").amap(rightF));
        assertEquals(left("nofun"), right("one").amap(leftF));
    }

    @Test
    public void mmapTest() {
        final Function<String, Either<String, String>> rightFunction = new Function<String, Either<String, String>>() {
            @Override public Either<String, String> apply(String s) { return right(s + "right"); }
        };
        final Function<String, Either<String, String>> leftFunction = new Function<String, Either<String, String>>() {
            @Override public Either<String, String> apply(String s) { return left(s + "left"); }
        };
        final Either<String, String> left = left("one");
        assertEquals(left("one"), left.mmap(rightFunction));
        assertEquals(left("one"), left.mmap(leftFunction));
        assertEquals(right("oneright"), right("one").mmap(rightFunction));
        assertEquals(left("oneleft"), right("one").mmap(leftFunction));
    }
}
