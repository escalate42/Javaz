package org.escalate42.javaz.trym;

import org.escalate42.javaz.common.function.Function;
import org.escalate42.javaz.common.function.ThrowableClosure;
import org.escalate42.javaz.common.function.ThrowableFunction;
import org.junit.Test;
import static org.escalate42.javaz.trym.TryMOps.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by vdubs
 * on 8/22/14.
 */
@SuppressWarnings("AssertEqualsBetweenInconvertibleTypes")
public class TryMTest {

    final Exception fail = new Exception("fail");

    final TryF<Void, String> successfull = tryF(s -> "success");

    final TryF<Void, String> failure = tryF(s -> { throw fail; });

    @Test
    public void fmapTest() {
        final Function<String, String> function = s -> s + "f";
        final Exception tfail = new Exception("tfail");
        final ThrowableFunction<String, String> throwableFunction = s -> { throw tfail; };
        assertEquals(success("successf"), successfull.apply(null).map(function));
        assertEquals(fail(tfail), successfull.apply(null).mapT(throwableFunction));
        assertEquals(fail(fail), failure.apply(null).map(function));
        assertEquals(fail(fail), failure.apply(null).mapT(throwableFunction));
    }

    @Test
    public void amapTest() {
        final Exception tfail = new Exception("tfail");
        final Function<String, String> function = s -> s + "f";
        final TryM<Function<String, String>> fas = success(function);
        final TryM<Function<String, String>> faf = fail(tfail);
        final ThrowableFunction<String, String> throwableFunction = s -> { throw tfail; };
        final TryM<ThrowableFunction<String, String>> tfas = success(throwableFunction);
        final TryM<ThrowableFunction<String, String>> tfaf = fail(tfail);
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
        final ThrowableFunction<String, TryM<String>> tft = (s) -> { throw tfail; };
        assertEquals(success("successf"), successfull.apply(null).flatMap(fs));
        assertEquals(fail(tfail), successfull.apply(null).flatMap(ff));
        assertEquals(fail(tfail), successfull.apply(null).flatMapT(tft));
        assertEquals(fail(fail), failure.apply(null).flatMap(fs));
        assertEquals(fail(fail), failure.apply(null).flatMap(ff));
        assertEquals(fail(fail), failure.apply(null).flatMapT(tft));
    }

    @Test
    public void closureTest() {
        final TryM<String> success = tryC(() -> "test").apply();
        final ThrowableClosure<String> failing = () -> {throw this.fail;};
        final TryM<String> failure = tryC(failing).apply();
        assertEquals(success("TEST"), success.map(String::toUpperCase));
        assertEquals(fail(fail), failure.map(String::toUpperCase));
    }
}
