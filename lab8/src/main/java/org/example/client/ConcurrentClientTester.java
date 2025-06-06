package org.example.client;

import org.example.collectionClasses.commands.Answer;
import org.example.collectionClasses.commands.ICommand;
import org.example.collectionClasses.commands.InfoCommand;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrentClientTester {
    private static final int THREAD_COUNT = 30;
    private static final int REQUESTS_PER_CLIENT = 30;
    private static final CyclicBarrier startBarrier = new CyclicBarrier(THREAD_COUNT);
    private static final AtomicInteger completedRequests = new AtomicInteger(0);
    private static final AtomicInteger atomicInteger = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        

        for (int i = 0; i < THREAD_COUNT; i++) {
            final int clientId = i;
            executor.submit(() -> {
                try {
                    ClientNetworkManager networkManager = new ClientNetworkManager("localhost", 57486);
                    
                    // Ждем, пока все клиенты будут готовы
                    startBarrier.await();
                    
                    for (int j = 0; j < REQUESTS_PER_CLIENT; j++) {
                        ICommand command = new InfoCommand();
                        Answer response = networkManager.sendCommandAndGetResponse(command);
                        completedRequests.incrementAndGet();
                        System.out.printf("[Client-%d][Req-%d] Response: %s%n", clientId, j, response);
                        if (response.toString().equals("Сервер недоступен для получения ответа")) {
                            atomicInteger.incrementAndGet();
                        }
                    }
                } catch (Exception e) {
                    System.err.printf("[Client-%d] Error: %s%n", clientId, e.getMessage());
                }
            });
        }

        System.out.println("=== STARTING TEST ===");
        long testStartTime = System.currentTimeMillis();

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
        System.out.println(atomicInteger);
    }
}