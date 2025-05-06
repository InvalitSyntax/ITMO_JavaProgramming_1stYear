package org.example.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class ServerMain {
    private static final Logger logger = LogManager.getLogger(ServerMain.class);

    public static void main(String[] args) throws IOException, InterruptedException {
        if (args.length == 0) {
            logger.error("Для работы приложения напишите названия файла в виде: <name.xml>");
            System.out.println("Для работы приложения напишите названия файла в виде:\n<name.xml>, где name - ваше желаемое название файла");
            return;
        }

        logger.info("Запуск сервера...");
        Server server = new Server(5252, args[0]);
        server.start();
    }
}