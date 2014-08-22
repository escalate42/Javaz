package org.escalate42.javaz.either;

import org.escalate42.javaz.common.F;
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
        final F<String, String> f = new F<String, String>() {
            @Override public String apply(String s) { return s + "two"; }
        };
        final Either<String, String> left = left("one");
        assertEquals(left("one"), left.fmap(f));
        assertEquals(right("onetwo"), right("one").fmap(f));
    }

    @Test
    public void amapTest() {
        final F<String, String> f = new F<String, String>() {
            @Override public String apply(String s) { return s + "two"; }
        };
        final Either<?, F<String, String>> rightF = EitherOps.id.pure(f);
        final Either<String, F<String, String>> leftF = left("nofun");
        final Either<String, String> left = left("one");
        assertEquals(left("one"), left.amap(rightF));
        assertEquals(left("nofun"), left.amap(leftF));
        assertEquals(right("onetwo"), right("one").amap(rightF));
        assertEquals(left("nofun"), right("one").amap(leftF));
    }

    @Test
    public void mmapTest() {
        final F<String, Either<String, String>> rightF = new F<String, Either<String, String>>() {
            @Override public Either<String, String> apply(String s) { return right(s + "right"); }
        };
        final F<String, Either<String, String>> leftF = new F<String, Either<String, String>>() {
            @Override public Either<String, String> apply(String s) { return left(s + "left"); }
        };
        final Either<String, String> left = left("one");
        assertEquals(left("one"), left.mmap(rightF));
        assertEquals(left("one"), left.mmap(leftF));
        assertEquals(right("oneright"), right("one").mmap(rightF));
        assertEquals(left("oneleft"), right("one").mmap(leftF));
    }
}
