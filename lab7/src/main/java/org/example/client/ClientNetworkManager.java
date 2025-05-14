package org.example.client;

import org.example.collectionClasses.commands.Answer;
import org.example.collectionClasses.commands.ICommand;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class ClientNetworkManager {
    private static final int SERVER_TIMEOUT_MS = 3000;
    private final String host;
    private final int port;
    
    public ClientNetworkManager(String host, int port) {
        this.host = host;
        this.port = port;
    }
    
    public String sendCommandAndGetResponse(ICommand command) throws Exception {
        try (DatagramChannel channel = DatagramChannel.open()) {
            channel.configureBlocking(false);
            
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream objOut = new ObjectOutputStream(byteOut);
            objOut.writeObject(command);
            byte[] data = byteOut.toByteArray();
            
            long startTime = System.currentTimeMillis();
            boolean sent = false;
            
            while (!sent && (System.currentTimeMillis() - startTime) < SERVER_TIMEOUT_MS) {
                try {
                    channel.send(ByteBuffer.wrap(data), new InetSocketAddress(host, port));
                    sent = true;
                } catch (IOException e) {
                    Thread.sleep(100);
                }
            }
            
            if (!sent) {
                System.out.println("Сервер недоступен для отправки команды");
            }

            ByteBuffer responseBuffer = ByteBuffer.allocate(16384);
            startTime = System.currentTimeMillis();
            
            while ((System.currentTimeMillis() - startTime) < SERVER_TIMEOUT_MS) {
                try {
                    InetSocketAddress serverAddress = (InetSocketAddress) channel.receive(responseBuffer);
                    if (serverAddress != null) {
                        responseBuffer.flip();
                        byte[] responseData = new byte[responseBuffer.remaining()];
                        responseBuffer.get(responseData);
                        
                        try (ObjectInputStream objIn = new ObjectInputStream(new ByteArrayInputStream(responseData))) {
                            Answer answer = (Answer) objIn.readObject();
                            return answer.toString();
                        }
                    }
                } catch (IOException e) {
                    Thread.sleep(100);
                }
            }
            
            System.out.println("Сервер недоступен для получения ответа");
            return "";
        }
    }
}