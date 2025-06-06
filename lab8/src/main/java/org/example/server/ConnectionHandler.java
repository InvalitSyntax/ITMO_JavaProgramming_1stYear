package org.example.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.collectionClasses.app.AppController;
import org.example.collectionClasses.commands.Answer;
import org.example.collectionClasses.commands.ICommand;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;

public class ConnectionHandler {
    private static final Logger logger = LogManager.getLogger(ConnectionHandler.class);
    private final RequestReader requestReader = new RequestReader();
    private final CommandProcessor commandProcessor = new CommandProcessor();
    private final ResponseSender responseSender = new ResponseSender();
    
    //private static final ForkJoinPool requestProcessingPool = new ForkJoinPool();
    //private static final ForkJoinPool responseSendingPool = new ForkJoinPool();

    private static final ForkJoinPool requestProcessingPool = new ForkJoinPool(
        Runtime.getRuntime().availableProcessors(),
        pool -> {
            ForkJoinWorkerThread worker = ForkJoinPool.defaultForkJoinWorkerThreadFactory.newThread(pool);
            worker.setName("request-worker-" + worker.getPoolIndex());
            return worker;
        },
        null,
        true //async
    );

    private static final ForkJoinPool responseSendingPool = new ForkJoinPool(
        Math.max(2, Runtime.getRuntime().availableProcessors() / 2),
        pool -> {
            ForkJoinWorkerThread worker = ForkJoinPool.defaultForkJoinWorkerThreadFactory.newThread(pool);
            worker.setName("response-worker-" + worker.getPoolIndex());
            return worker;
        },
        null,
        true
    );

    private static final ThreadLocal<ByteBuffer> threadLocalBuffer = ThreadLocal.withInitial(
        () -> ByteBuffer.allocateDirect(4096)
    );


    public void processIncomingData(DatagramChannel channel, AppController appController) throws IOException {
        //ByteBuffer buffer = threadLocalBuffer.get();
        ByteBuffer buffer = ByteBuffer.allocate(4096);
        buffer.clear();
        InetSocketAddress clientAddress = (InetSocketAddress) channel.receive(buffer);

        if (clientAddress != null) {
            buffer.flip();
            byte[] receivedData = new byte[buffer.remaining()];
            buffer.get(receivedData);
            Thread readingThread = new Thread(() -> {
                try {
                    logger.info("Получены данные от клиента: {}", clientAddress);

                    requestProcessingPool.execute(() -> {
                        ICommand receivedCommand = requestReader.readRequest(receivedData);
                        if (receivedCommand != null) {
                            Answer answer = commandProcessor.processCommand(receivedCommand, appController);
                            
                            responseSendingPool.execute(() -> {
                                try {
                                    responseSender.sendResponse(answer, clientAddress, channel);
                                } catch (IOException e) {
                                    logger.error("Не удалось отправить ответ клиенту {}", clientAddress);
                                }
                            });
                        }
                    });
                } catch (Exception e) {
                logger.error("Ошибка при чтении запроса", e);
                }
            });
            readingThread.setDaemon(true);
            readingThread.start();
        }
    }
}