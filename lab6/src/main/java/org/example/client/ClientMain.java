package org.example.client;

import java.io.IOException;

public class ClientMain {
    private static final int TIMEOUT_MS = 1000; // 5 секунд

    public static void main(String[] args) {
        InteractiveUDPClient client = new InteractiveUDPClient("localhost", 9876, TIMEOUT_MS);
        client.startInteractiveMode();
    }
}