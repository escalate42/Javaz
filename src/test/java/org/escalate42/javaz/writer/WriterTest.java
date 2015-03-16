package org.escalate42.javaz.writer;

import org.escalate42.javaz.common.function.Function;
import org.escalate42.javaz.common.monoid.impl.ListMonoid;
import org.escalate42.javaz.common.monoid.impl.StringMonoid;
import org.escalate42.javaz.common.tuple.Tuple2;
import org.escalate42.javaz.option.Option;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.escalate42.javaz.option.OptionOps.*;

/**
 * Created by vdubs
 * on 1/21/15.
 */
public class WriterTest {

    @Test
    public void writerTest() {
        final Writer<String, StringMonoid, String> writer = Writer.writer("String body", StringMonoid.mempty());
        final Tuple2<List<String>, String> expected = Tuple2.t(
                new ArrayList<String>() {{add("STRING"); add("BODY");}},
                "toUpperCase, splitting by whitespace"
        );
        final Tuple2<List<String>, String> tuple = writer
            .tell("toUpperCase, ")
            .map(String::toUpperCase)
            .tell("splitting by whitespace")
            .map((s) -> Arrays.asList(s.split(" ")))
            .runWriter();
        assertEquals(expected, tuple);
    }

    @Test
    public void writerTTest() {
        final WriterT<List<String>, ListMonoid<?>, String, Option<?>> writerT = WriterT.writerT(option("String body"), ListMonoid.mempty());
        final WriterT<List<String>, ListMonoid<?>, String, Option<?>> emptyWriterT = WriterT.writerT(option(null), ListMonoid.mempty());
        final Option<Tuple2<List<String>, List<String>>> expected = option(Tuple2.t(
                new ArrayList<String>() {{
                    add("STRING");
                    add("BODY");
                }},
                new ArrayList<String>() {{
                    add("toUpperCase");
                    add("splitting by whitespace");
                }}
        ));
        final Function<WriterT<List<String>, ListMonoid<?>, String, Option<?>>, Option<Tuple2<List<String>, List<String>>>> fun =
                (w) -> w.tell(ListMonoid.asList("toUpperCase"))
                        .map(String::toUpperCase)
                        .tell(ListMonoid.asList("splitting by whitespace"))
                        .map((s) -> Arrays.asList(s.split(" ")))
                        .run();
        final Option<Tuple2<List<String>, List<String>>> actual = fun.apply(writerT);
        assertEquals(expected, actual);
        assertEquals(none(), fun.apply(emptyWriterT));
    }
}
