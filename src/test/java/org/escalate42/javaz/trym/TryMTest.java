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

    final Try<Void, String> successfull = tryF(new TryFunction<Void, String>() {
        @Override public String apply(Void s) { return "success"; }
    });

    final Try<Void, String> failure = tryF(new TryFunction<Void, String>() {
        @Override public String apply(Void s) throws Exception { throw fail; }
    });

    @Test
    public void fmapTest() {
        final Function<String, String> function = new Function<String, String>() {
            @Override public String apply(String s) { return s + "f"; }
        };
        final Exception tfail = new Exception("tfail");
        final TryFunction<String, String> tryFunction = new TryFunction<String, String>() {
            @Override public String apply(String s) throws Exception { throw tfail; }
        };
        assertEquals(success("successf"), successfull.apply(null).fmap(function));
        assertEquals(fail(tfail), successfull.apply(null).fmap(tryFunction));
        assertEquals(fail(fail), failure.apply(null).fmap(function));
        assertEquals(fail(fail), failure.apply(null).fmap(tryFunction));
    }

    @Test
    public void amapTest() {
        final Exception tfail = new Exception("tfail");
        final Function<String, String> function = new Function<String, String>() {
            @Override public String apply(String s) { return s + "f"; }
        };
        final TryM<Function<String, String>> fas = success(function);
        final TryM<Function<String, String>> faf = fail(tfail);
        final TryFunction<String, String> tryFunction = new TryFunction<String, String>() {
            @Override public String apply(String s) throws Exception { throw tfail; }
        };
        final TryM<TryFunction<String, String>> tfas = success(tryFunction);
        final TryM<TryFunction<String, String>> tfaf = fail(tfail);
        assertEquals(success("successf"), successfull.apply(null).amap(fas));
        assertEquals(fail(tfail), successfull.apply(null).amap(tfas));
        assertEquals(fail(tfail), successfull.apply(null).amap(faf));
        assertEquals(fail(tfail), successfull.apply(null).amap(tfaf));
        assertEquals(fail(fail), failure.apply(null).amap(fas));
        assertEquals(fail(fail), failure.apply(null).amap(tfas));
        assertEquals(fail(tfail), failure.apply(null).amap(faf));
        assertEquals(fail(tfail), failure.apply(null).amap(tfaf));
    }

    @Test
    public void mmapTest() {
        final Exception tfail = new Exception("tfail");
        final Function<String, TryM<String>> fs = new Function<String, TryM<String>>() {
            @Override public TryM<String> apply(String s) { return success(s + "f"); }
        };
        final Function<String, TryM<String>> ff = new Function<String, TryM<String>>() {
            @Override public TryM<String> apply(String s) { return fail(tfail); }
        };
        final TryFunction<String, TryM<String>> tft = new TryFunction<String, TryM<String>>() {
            @Override public TryM<String> apply(String s) throws Exception { throw tfail; }
        };
        assertEquals(success("successf"), successfull.apply(null).mmap(fs));
        assertEquals(fail(tfail), successfull.apply(null).mmap(ff));
        assertEquals(fail(tfail), successfull.apply(null).mmap(tft));
        assertEquals(fail(fail), failure.apply(null).mmap(fs));
        assertEquals(fail(fail), failure.apply(null).mmap(ff));
        assertEquals(fail(fail), failure.apply(null).mmap(tft));
    }
}
