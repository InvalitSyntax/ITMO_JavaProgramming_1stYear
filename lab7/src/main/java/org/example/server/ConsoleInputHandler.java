package org.example.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.collectionClasses.app.AppController;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;

public class ConsoleInputHandler {
    private static final Logger logger = LogManager.getLogger(ConsoleInputHandler.class);

    public void processConsoleInput(AppController appController) {
        try (Scanner scanner = new Scanner(System.in)){
            while (scanner.hasNext()) {
                    String input = scanner.nextLine();
                    if (input == null) {
                        System.out.print("Введите команду (список команд вы можете посмотреть, написав <help> и нажав Enter)\n");
                    } else {
                        handleConsoleCommand(input, appController);
                }
            }
        }
    }

    private void handleConsoleCommand(String input, AppController appController) {
        String[] tokens = input.trim().split(" ");
        if (tokens[0].equals("exit")) {
            handleExitCommand(appController);
        } else {
            try {
                appController.getCommandManager().executeCommand(appController, tokens);
                String writedMessages = appController.getIoManager().popWritedMessages();
                if (writedMessages.length() != 0) {
                    System.out.println(writedMessages);
                }
                logger.info("Консольная команда выполнена: {}", input);
            } catch (Exception e) {
                logger.error("Ошибка при выполнении консольной команды: {}", e.getMessage());
                System.out.println(e.getMessage());
            }
        }
    }

    private void handleExitCommand(AppController appController) {
        appController.getCommandManager().executeCommand(appController, new String[]{"save"});
        String writedMessages = appController.getIoManager().popWritedMessages();
        if (writedMessages.length() != 0) {
            System.out.println(writedMessages);
        }
        logger.info("Завершение работы сервера...");
        System.out.println("Выход из программы...");
        System.exit(0);
    }
}