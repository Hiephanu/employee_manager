package com.example.ncc_spring.service.client;

import reactor.core.publisher.Mono;

import java.time.Duration;

public class MonoExample {

    public Mono<String> delayedResult() {
        return Mono.delay(Duration.ofSeconds(1))
                .map(delay -> "Result after 5 seconds");
    }

    public static void main(String[] args) throws InterruptedException {
        MonoExample example = new MonoExample();

        example.delayedResult()
                .subscribe(
                        result -> System.out.println("Received result: " + result),
                        error -> System.err.println("Error occurred: " + error),
                        () -> System.out.println("Processing completed")
                );

        // Tiếp tục thực hiện các công việc khác mà không cần chờ đợi kết quả từ delayedResult()
        System.out.println("Continuing other tasks...");
        Thread.sleep(6000);
    }
}