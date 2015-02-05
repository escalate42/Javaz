package org.escalate42.javaz.future;

import org.junit.Test;

import static org.escalate42.javaz.future.FutureImpl.*;
import static org.junit.Assert.assertEquals;
import static org.escalate42.javaz.trym.TryMImpl.*;

/**
 * Created by vdubs
 * on 2/5/15.
 */
public class FutureTest {

    final Throwable exception = new Exception("BANG!");

    @Test
    public void testFlatMap() {
//        final Future<String> resultSuccess = future(() -> "one").flatMap(s1 -> completed(s1 + "two"));
        final Future<String> failure = completed(exception);
        final Future<String> resultFailure = future(() -> "one").flatMap(s1 -> failure);
//        assertEquals(success("onetwo"), resultSuccess.get());
        System.out.println("rf:" + resultFailure);
        System.out.println("f:" + failure);
        assertEquals(fail(exception), resultFailure.get());
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
                completed("one"), completed("two"), future(() -> "three"), future(() -> {throw new Exception("bang");}),
                (s1, s2, s3, s4) -> s1 + s2 + s3 + s4
        );
        Thread.sleep(3000);
        System.out.println(result + "->" + result.value());
        assertEquals(success("onetwothree"), result.get());
    }
}
