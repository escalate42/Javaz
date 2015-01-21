package org.escalate42.javaz.trym;

import org.escalate42.javaz.common.function.Function;
import org.escalate42.javaz.common.function.TryFunction;
import org.junit.Test;
import static org.escalate42.javaz.trym.TryMImpl.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by vdubs
 * on 8/22/14.
 */
@SuppressWarnings("AssertEqualsBetweenInconvertibleTypes")
public class TryMTest {

    final Exception fail = new Exception("fail");

    final Try<Void, String> successfull = tryF(s -> "success");

    final Try<Void, String> failure = tryF(s -> { throw fail; });

    @Test
    public void fmapTest() {
        final Function<String, String> function = s -> s + "f";
        final Exception tfail = new Exception("tfail");
        final TryFunction<String, String> tryFunction = s -> { throw tfail; };
        assertEquals(success("successf"), successfull.apply(null).map(function));
        assertEquals(fail(tfail), successfull.apply(null).mapT(tryFunction));
        assertEquals(fail(fail), failure.apply(null).map(function));
        assertEquals(fail(fail), failure.apply(null).mapT(tryFunction));
    }

    @Test
    public void amapTest() {
        final Exception tfail = new Exception("tfail");
        final Function<String, String> function = s -> s + "f";
        final TryM<Function<String, String>> fas = success(function);
        final TryM<Function<String, String>> faf = fail(tfail);
        final TryFunction<String, String> tryFunction = s -> { throw tfail; };
        final TryM<TryFunction<String, String>> tfas = success(tryFunction);
        final TryM<TryFunction<String, String>> tfaf = fail(tfail);
        assertEquals(success("successf"), successfull.apply(null).amap(fas));
        assertEquals(fail(tfail), successfull.apply(null).amapT(tfas));
        assertEquals(fail(tfail), successfull.apply(null).amap(faf));
        assertEquals(fail(tfail), successfull.apply(null).amapT(tfaf));
        assertEquals(fail(fail), failure.apply(null).amap(fas));
        assertEquals(fail(fail), failure.apply(null).amapT(tfas));
        assertEquals(fail(tfail), failure.apply(null).amap(faf));
        assertEquals(fail(tfail), failure.apply(null).amapT(tfaf));
    }

    @Test
    public void mmapTest() {
        final Exception tfail = new Exception("tfail");
        final Function<String, TryM<String>> fs = s -> success(s + "f");
        final Function<String, TryM<String>> ff = s -> fail(tfail);
        final TryFunction<String, TryM<String>> tft = (s) -> { throw tfail; };
        assertEquals(success("successf"), successfull.apply(null).flatMap(fs));
        assertEquals(fail(tfail), successfull.apply(null).flatMap(ff));
        assertEquals(fail(tfail), successfull.apply(null).flatMapT(tft));
        assertEquals(fail(fail), failure.apply(null).flatMap(fs));
        assertEquals(fail(fail), failure.apply(null).flatMap(ff));
        assertEquals(fail(fail), failure.apply(null).flatMapT(tft));
    }
}
