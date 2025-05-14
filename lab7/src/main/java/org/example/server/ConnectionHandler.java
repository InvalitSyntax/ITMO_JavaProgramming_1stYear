package org.example.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.collectionClasses.app.AppController;
import org.example.collectionClasses.commands.ICommand;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class ConnectionHandler {
    private static final Logger logger = LogManager.getLogger(ConnectionHandler.class);
    private final RequestReader requestReader = new RequestReader();
    private final CommandProcessor commandProcessor = new CommandProcessor();
    private final ResponseSender responseSender = new ResponseSender();

    public void processIncomingData(DatagramChannel channel, AppController appController) throws IOException {
        Runnable task = () -> {
            try {
                ByteBuffer buffer = ByteBuffer.allocate(4096);
                InetSocketAddress clientAddress = (InetSocketAddress) channel.receive(buffer);

                if (clientAddress != null) {
                    buffer.flip();
                    byte[] receivedData = new byte[buffer.remaining()];
                    buffer.get(receivedData);
                    
                    logger.info("Получены данные от клиента: {}", clientAddress);

                    ICommand receivedCommand = requestReader.readRequest(receivedData);
                    if (receivedCommand != null) {
                        String response = commandProcessor.processCommand(receivedCommand, appController);
                        try {
                            responseSender.sendResponse(response, clientAddress, channel);
                        } catch (IOException e) {
                            logger.error("Не удалось отправить ответ клиенту {}", clientAddress);
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("Ошибка в потоке обработки запроса", e);
            }
        };

        new Thread(task).start();
    }
}