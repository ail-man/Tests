package com.ail.home.concurrency;

import java.util.concurrent.CompletableFuture;
import org.junit.Test;

public class CompletableFutureTest {

    @Test
    public void completableFutureTest() {
        CompletableFuture.supplyAsync(this::supply).thenAccept(this::accept);
        System.out.println("root " + Thread.currentThread().getId());
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
        System.out.println("supply " + Thread.currentThread().getId());
        return "message";
    }

    private void accept(String s) {
        sleep(1000);
        System.out.println("accept " + s + " " + Thread.currentThread().getId());
    }

}
