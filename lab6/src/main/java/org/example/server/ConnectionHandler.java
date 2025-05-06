package org.example.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.collectionClasses.app.AppController;
import org.example.collectionClasses.commands.ICommand;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class ConnectionHandler {
    private static final Logger logger = LogManager.getLogger(ConnectionHandler.class);
    private final RequestReader requestReader = new RequestReader();
    private final CommandProcessor commandProcessor = new CommandProcessor();
    private final ResponseSender responseSender = new ResponseSender();

    public void processIncomingData(DatagramChannel channel, ByteBuffer buffer, AppController appController) throws IOException {
        buffer.clear();
        InetSocketAddress clientAddress = (InetSocketAddress) channel.receive(buffer);

        if (clientAddress != null) {
            logger.info("Получены данные от клиента: {}", clientAddress);
            buffer.flip();
            byte[] receivedData = new byte[buffer.remaining()];
            buffer.get(receivedData);

            ICommand receivedCommand = requestReader.readRequest(receivedData);
            if (receivedCommand != null) {
                String response = commandProcessor.processCommand(receivedCommand, appController);
                responseSender.sendResponse(response, clientAddress, channel);
            }
        }
    }
}