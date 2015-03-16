package org.escalate42.javaz.either;

import org.escalate42.javaz.common.function.Function;
import org.junit.Test;
import static org.escalate42.javaz.either.EitherOps.*;
import static org.junit.Assert.*;

/**
 * Created by vdubs
 * on 8/22/14.
 */
@SuppressWarnings("AssertEqualsBetweenInconvertibleTypes")
public class EitherTest {

    @Test
    public void fmapTest() {
        final Function<String, String> function = s -> s + "two";
        final Either<String, String> left = left("one");
        assertEquals(left("one"), left.map(function));
        assertEquals(right("onetwo"), right("one").map(function));
    }

    @Test
    public void amapTest() {
        final Function<String, String> function = s -> s + "two";
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
        final Function<String, Either<String, String>> rightFunction = s -> right(s + "right");
        final Function<String, Either<String, String>> leftFunction = s -> left(s + "left");
        final Either<String, String> left = left("one");
        assertEquals(left("one"), left.flatMap(rightFunction));
        assertEquals(left("one"), left.flatMap(leftFunction));
        assertEquals(right("oneright"), right("one").flatMap(rightFunction));
        assertEquals(left("oneleft"), right("one").flatMap(leftFunction));
    }

    @Test
    public void yieldForTestRight() {
        final Either<String, Integer> either = EitherOps.id.yieldFor(
                right("first"), right("second"), right("third").map(String::length),
                (s1, s2, i3) -> s1.length() + s2.length() + i3
        );
        assertEquals(right(16), either);
    }

    @Test
    public void yieldForTestLeft() {
        final Either<String, Integer> either = EitherOps.id.yieldFor(
                right("first"), left("fail"), right("third").map(String::length),
                (String s1, String s2, Integer i3) -> s1.length() + s2.length() + i3
        );
        assertEquals(left("fail"), either);
    }
}
