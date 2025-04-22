package org.example.client;

import org.example.client.exceptions.ClientException;
import org.example.client.handler.ClientCommandProcessor;
import org.example.client.handler.ConsoleInputHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class InteractiveUDPClient extends AbstractUDPClient {
    private final ClientCommandProcessor commandProcessor;
    private final int timeoutMs;

    public InteractiveUDPClient(String serverHost, int serverPort, int timeoutMs) {
        super(serverHost, serverPort);
        this.commandProcessor = new ClientCommandProcessor();
        this.timeoutMs = timeoutMs;
    }

    @Override
    public void sendMessage(String message) throws IOException {
        InetAddress serverAddress = InetAddress.getByName(serverHost);
        byte[] sendData = message.getBytes();

        DatagramPacket sendPacket = new DatagramPacket(
                sendData, sendData.length, serverAddress, serverPort
        );
        socket.send(sendPacket);
        System.out.println("[Клиент] Отправлено: " + message);
    }

    public String receiveResponse() throws IOException {
        byte[] receiveData = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        socket.receive(receivePacket);
        return new String(receivePacket.getData(), 0, receivePacket.getLength());
    }

    public void startInteractiveMode() {
        try (ConsoleInputHandler inputHandler = new ConsoleInputHandler()) {
            initSocket();
            socket.setSoTimeout(timeoutMs);
            System.out.println("Клиент запущен. Введите сообщение (или 'exit' для выхода):");

            while (true) {
                try {
                    String input = inputHandler.readLine();
                    if (commandProcessor.isExitCommand(input)) {
                        break;
                    }
                    if (commandProcessor.isSpecialCommand(input)) {
                        continue;  // Пропускаем обработку специальных команд
                    }

                    sendMessage(input);
                    String response = receiveResponse();
                    System.out.println("[Клиент] Ответ сервера: " + response);

                } catch (SocketTimeoutException e) {
                    System.err.println("[Клиент] Таймаут: сервер не ответил за " + timeoutMs + " мс.");
                } catch (IOException e) {
                    throw new ClientException("Ошибка ввода-вывода", e);
                }
            }
        } catch (Exception e) {
            System.err.println("[Клиент] Ошибка: " + e.getMessage());
        }
    }
}

