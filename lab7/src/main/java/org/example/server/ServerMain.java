package org.example.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

// FevL&5227
// pkill -u $USER java
// прикольный сайт: https://metanit.com/java/tutorial/8.10.php
// обаледенное описание классов-синхронизаторов https://habr.com/ru/articles/277669/    

public class ServerMain {
    private static final Logger logger = LogManager.getLogger(ServerMain.class);

    public static void main(String[] args) throws IOException, InterruptedException {
        logger.info("Запуск сервера...");
        Server server = new Server(57486);
        server.start();
    }
}