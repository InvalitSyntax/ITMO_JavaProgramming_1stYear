package org.example.client;

import org.example.collectionClasses.commands.ExecuteScriptCommand;
import org.example.collectionClasses.commands.ICommand;
import org.example.collectionClasses.commands.InfoCommand;
import org.example.collectionClasses.commands.ShowCommand;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrentClientTester {
    private static final int THREAD_COUNT = 30;
    private static final int REQUESTS_PER_CLIENT = 100;
    private static final CountDownLatch startLatch = new CountDownLatch(1);
    private static final AtomicInteger completedRequests = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        long testStartTime = System.currentTimeMillis();

        for (int i = 0; i < THREAD_COUNT; i++) {
            final int clientId = i;
            executor.submit(() -> {
                try {
                    ClientNetworkManager networkManager = new ClientNetworkManager("localhost", 57486);
                    
                    startLatch.await();
                    
                    for (int j = 0; j < REQUESTS_PER_CLIENT; j++) {
                        ICommand command = new InfoCommand();
                        String response = networkManager.sendCommandAndGetResponse(command);
                        completedRequests.incrementAndGet();
                        System.out.printf("[Client-%d][Req-%d] Response: %s%n", clientId, j, response.trim());
                    }
                } catch (Exception e) {
                    System.err.printf("[Client-%d] Error: %s%n", clientId, e.getMessage());
                }
            });
        }


        Thread.sleep(1000);
        System.out.println("=== STARTING TEST ===");
        startLatch.countDown();

        executor.shutdown();
        executor.awaitTermination(2, TimeUnit.MINUTES);

        long testDuration = System.currentTimeMillis() - testStartTime;
        System.out.printf(
            "=== TEST COMPLETED ===\n" +
            "Threads: %d\n" +
            "Requests per thread: %d\n" +
            "Total requests: %d\n" +
            "Test duration: %d ms\n" +
            "Requests per second: %.2f%n",
            THREAD_COUNT, REQUESTS_PER_CLIENT, 
            THREAD_COUNT * REQUESTS_PER_CLIENT,
            testDuration,
            (THREAD_COUNT * REQUESTS_PER_CLIENT * 1000.0) / testDuration
        );
    }
}