package org.example.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.collectionClasses.app.AppController;
import org.example.collectionClasses.commands.ICommand;

public class CommandProcessor {
    private static final Logger logger = LogManager.getLogger(CommandProcessor.class);

    public synchronized String processCommand(ICommand receivedCommand, AppController appController) {
        receivedCommand.execute(appController, receivedCommand.getArgs());
        String writedMessages = appController.getIoManager().popWritedMessages();
        String result = writedMessages.length() != 0 ? writedMessages : "Команда выполнена";
        logger.info("Команда обработана, результат: {}", result);
        return result;
    }
}