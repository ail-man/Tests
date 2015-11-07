package com.ail.home.fortest;

import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.rules.Timeout;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class DoSomethingTest extends Assert {

    @Rule
    public final Timeout timeout = new Timeout(1000, TimeUnit.MILLISECONDS);

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    @Test
    public void throwsExceptionWithSpecificType() {
        thrown.expect(NullPointerException.class);
        throw new NullPointerException();
    }

    public static final String TEST = "ABCD";
    private DoSomething ds;

    @Before
    public void newObject() {
        ds = new DoSomething();
    }

    @Test
    public void shouldReturnString() throws Exception {
        String result = ds.execute(() -> TEST);
        assertEquals(TEST, result);
    }

    @Test
    public void shouldFilterResult() throws Exception {
        Optional<String> result = ds.list()
                .stream()
                .peek(System.out::println)
                .map(String::toUpperCase)
                .peek(System.out::println)
                .reduce((a, b) -> a + b);
        assertTrue(result.isPresent());
        assertEquals(TEST, result.get());
    }

    @Ignore
    @Test
    public void anotherInfinity() {
        while (true);
    }
}
