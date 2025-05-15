package org.example.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.collectionClasses.app.AppController;
import org.example.collectionClasses.commands.ICommand;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.ForkJoinPool;

public class ConnectionHandler {
    private static final Logger logger = LogManager.getLogger(ConnectionHandler.class);
    private final RequestReader requestReader = new RequestReader();
    private final CommandProcessor commandProcessor = new CommandProcessor();
    private final ResponseSender responseSender = new ResponseSender();
    
    private static final ForkJoinPool requestProcessingPool = new ForkJoinPool();
    private static final ForkJoinPool responseSendingPool = new ForkJoinPool();

    public void processIncomingData(DatagramChannel channel, AppController appController) throws IOException {
        Thread readingThread = new Thread(() -> {
            try {
                ByteBuffer buffer = ByteBuffer.allocate(4096);
                InetSocketAddress clientAddress = (InetSocketAddress) channel.receive(buffer);

                if (clientAddress != null) {
                    buffer.flip();
                    byte[] receivedData = new byte[buffer.remaining()];
                    buffer.get(receivedData);
                    
                    logger.info("Получены данные от клиента: {}", clientAddress);

                    requestProcessingPool.execute(() -> {
                        ICommand receivedCommand = requestReader.readRequest(receivedData);
                        if (receivedCommand != null) {
                            String response = commandProcessor.processCommand(receivedCommand, appController);
                            
                            responseSendingPool.execute(() -> {
                                try {
                                    responseSender.sendResponse(response, clientAddress, channel);
                                } catch (IOException e) {
                                    logger.error("Не удалось отправить ответ клиенту {}", clientAddress);
                                }
                            });
                        }
                    });
                }
            } catch (Exception e) {
                logger.error("Ошибка при чтении запроса", e);
            }
        });
        readingThread.setDaemon(true);
        readingThread.start();
    }
}