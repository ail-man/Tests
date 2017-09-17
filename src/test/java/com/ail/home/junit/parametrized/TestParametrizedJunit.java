package com.ail.home.junit.parametrized;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

@RunWith(Parameterized.class)
public class TestParametrizedJunit {

    @Parameterized.Parameter()
    public String s;
    @Parameterized.Parameter(1)
    public String testName;

    @Parameterized.Parameters(name = "Test number {index}. Param: {0} - Name: {1}")
    public static Iterable<String[]> data() {
        return Arrays.asList(
                new String[]{"a", "test with 'a'"},
                new String[]{"b", "test with 'b'"},
                new String[]{"c", "test with 'c'"});
    }

    @Test
    public void testParam() {
        System.out.println(s);
    }
}

