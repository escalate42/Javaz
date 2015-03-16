package org.escalate42.javaz.future;

import org.junit.Test;

import static org.escalate42.javaz.future.FutureOps.*;
import static org.junit.Assert.assertEquals;
import static org.escalate42.javaz.trym.TryMOps.*;

/**
 * Created by vdubs
 * on 2/5/15.
 */
@SuppressWarnings("AssertEqualsBetweenInconvertibleTypes")
public class FutureTest {

    final Throwable exception = new RuntimeException("BANG!");

    @Test
    public void testFlatMap() throws InterruptedException {
        final Future<String> fsf = future(() -> "one").flatMap(s -> FutureOps.<String>completed(exception));
        final Future<String> ffs = FutureOps.<String>completed(exception).flatMap((s) -> future(() -> "one"));
        assertEquals(fail(exception), fsf.get());
        assertEquals(fail(exception), ffs.get());
    }

    @Test
    public void testYieldForSuccess() {
        final FutureOps id = FutureOps.id;
        final Future<String> result = id.yieldFor(
                completed("one"), future(() -> "two"), completed("three"),
                (s1, s2, s3) -> s1 + s2 + s3
        );
        assertEquals(success("onetwothree"), result.get());
    }

    @Test
    public void testYieldForFailure() throws InterruptedException {
        final FutureOps id = FutureOps.id;
        final Future<String> result = id.yieldFor(
                completed("one"), completed("two"), future(() -> {throw exception;}), future(() -> "three"),
                (s1, s2, s3, s4) -> s1 + s2 + s3 + s4
        );
        assertEquals(fail(exception), result.get());
    }
}
