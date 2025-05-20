package org.example.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

// FevL&5227
// pkill -u $USER java

public class ServerMain {
    private static final Logger logger = LogManager.getLogger(ServerMain.class);

    public static void main(String[] args) throws IOException, InterruptedException {
        logger.info("Запуск сервера...");
        Server server = new Server(57486);
        server.start();
    }
}