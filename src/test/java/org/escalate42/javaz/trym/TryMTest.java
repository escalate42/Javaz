package org.escalate42.javaz.trym;

import org.escalate42.javaz.common.function.F;
import org.escalate42.javaz.common.function.TF;
import org.junit.Test;
import static org.escalate42.javaz.trym.TryM.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by vdubs
 * on 8/22/14.
 */
@SuppressWarnings("AssertEqualsBetweenInconvertibleTypes")
public class TryMTest {

    final Exception fail = new Exception("fail");

    final Try<Void, String> successfull = tryF(new TF<Void, String>() {
        @Override public String apply(Void s) { return "success"; }
    });

    final Try<Void, String> failure = tryF(new TF<Void, String>() {
        @Override public String apply(Void s) throws Exception { throw fail; }
    });

    @Test
    public void fmapTest() {
        final F<String, String> f = new F<String, String>() {
            @Override public String apply(String s) { return s + "f"; }
        };
        final Exception tfail = new Exception("tfail");
        final TF<String, String> tf = new TF<String, String>() {
            @Override public String apply(String s) throws Exception { throw tfail; }
        };
        assertEquals(success("successf"), successfull.apply(null).fmap(f));
        assertEquals(fail(tfail), successfull.apply(null).fmap(tf));
        assertEquals(fail(fail), failure.apply(null).fmap(f));
        assertEquals(fail(fail), failure.apply(null).fmap(tf));
    }

    @Test
    public void amapTest() {
        final Exception tfail = new Exception("tfail");
        final F<String, String> f = new F<String, String>() {
            @Override public String apply(String s) { return s + "f"; }
        };
        final TryM<F<String, String>> fas = success(f);
        final TryM<F<String, String>> faf = fail(tfail);
        final TF<String, String> tf = new TF<String, String>() {
            @Override public String apply(String s) throws Exception { throw tfail; }
        };
        final TryM<TF<String, String>> tfas = success(tf);
        final TryM<TF<String, String>> tfaf = fail(tfail);
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
        final F<String, TryM<String>> fs = new F<String, TryM<String>>() {
            @Override public TryM<String> apply(String s) { return success(s + "f"); }
        };
        final F<String, TryM<String>> ff = new F<String, TryM<String>>() {
            @Override public TryM<String> apply(String s) { return fail(tfail); }
        };
        final TF<String, TryM<String>> tft = new TF<String, TryM<String>>() {
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
