package com.ail.home.concurrency;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import org.junit.Test;

public class CompletableFutureTest {

    @Test
    public void completableFutureTest() throws ExecutionException, InterruptedException {
        CompletableFuture
                .supplyAsync(this::supply)
                .thenAccept(this::accept);
        System.out.println("main " + Thread.currentThread().getId());
        sleep(3000);
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String supply() {
        sleep(1000); // REMOVE THIS TO SEE DIFFERENT BEHAVIOUR
        System.out.println("supply " + Thread.currentThread().getId());
        return "message";
    }

    private void accept(String s) {
        sleep(1000);
        System.out.println("accept " + s + " " + Thread.currentThread().getId());
    }

}
