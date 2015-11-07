package com.ail.home.fortest;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

public class DoSomething {

    public String execute(Callable<String> call) throws Exception {
        return call.call();
    }

    public List<String> list() {
        return Arrays.asList("a", "b", "c", "d");
    }
}
