package org.example.server;


import java.io.IOException;

public class ServerMain {
    public static void main(String[] args) {
        try {
            NonBlockingUDPServer server = new NonBlockingUDPServer(9876, 1024);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
