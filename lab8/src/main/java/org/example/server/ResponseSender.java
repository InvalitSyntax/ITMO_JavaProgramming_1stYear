package org.example.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.collectionClasses.commands.Answer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class ResponseSender {
    private static final Logger logger = LogManager.getLogger(ResponseSender.class);

    public void sendResponse(Answer answer, InetSocketAddress clientAddress, DatagramChannel channel) throws IOException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream objOut = new ObjectOutputStream(byteOut);
        objOut.writeObject(answer);
        byte[] responseData = byteOut.toByteArray();

        ByteBuffer responseBuffer = ByteBuffer.wrap(responseData);
        channel.send(responseBuffer, clientAddress);
        logger.info("Ответ отправлен клиенту: {}", clientAddress);
    }
}