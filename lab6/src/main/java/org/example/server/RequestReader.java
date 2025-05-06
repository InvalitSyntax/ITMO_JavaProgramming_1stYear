package org.example.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.collectionClasses.commands.ICommand;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class RequestReader {
    private static final Logger logger = LogManager.getLogger(RequestReader.class);

    public ICommand readRequest(byte[] receivedData) {
        try (ObjectInputStream objIn = new ObjectInputStream(new ByteArrayInputStream(receivedData))) {
            ICommand receivedCommand = (ICommand) objIn.readObject();
            logger.info("Получена команда: {}", receivedCommand);
            return receivedCommand;
        } catch (IOException | ClassNotFoundException e) {
            logger.error("Ошибка при десериализации команды", e);
            return null;
        }
    }
}