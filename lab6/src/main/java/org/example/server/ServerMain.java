package org.example.server;

import java.io.*;
import java.lang.reflect.Array;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Arrays;

import org.example.collectionClasses.app.AppController;
import org.example.collectionClasses.app.CommandManager;
import org.example.collectionClasses.app.IOManager;
import org.example.collectionClasses.app.SpaceMarineCollectionManager;
import org.example.collectionClasses.app.XMLIOManager;
import org.example.collectionClasses.commands.Answer;
import org.example.collectionClasses.commands.ICommand;

public class ServerMain {
    public static void main(String[] args) throws IOException, InterruptedException {
        if (args.length == 0) {
            System.out.println("Для работы приложения напишите названия файла в виде:\n<name.xml>, где name - ваше желаемое название файла");
        } else {
            SpaceMarineCollectionManager spaceMarineCollectionManager = new SpaceMarineCollectionManager();
            XMLIOManager xmlioManager = new XMLIOManager(args[0]);
            CommandManager commandManager = new CommandManager();
            IOManager ioManager = new IOManager();

            AppController appController = new AppController(commandManager, spaceMarineCollectionManager, xmlioManager, ioManager);

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            InetSocketAddress address = new InetSocketAddress(8000);
            DatagramChannel channel = DatagramChannel.open();
            channel.bind(address);
            channel.configureBlocking(false);
            ByteBuffer buffer = ByteBuffer.allocate(4096);

            System.out.println("Вы можете ввести команду (список команд вы можете посмотреть, написав <help> и нажав Enter)\n");

            while (true) {
                buffer.clear();
                InetSocketAddress clientAddress = (InetSocketAddress) channel.receive(buffer);
                
                if (clientAddress != null) {
                    System.out.println("Данные пришли!");
                    buffer.flip();
                    byte[] receivedData = new byte[buffer.remaining()];
                    buffer.get(receivedData);
                    
                    try (ObjectInputStream objIn = new ObjectInputStream(new ByteArrayInputStream(receivedData))) {
                        ICommand receivedCommand = (ICommand) objIn.readObject();
                        System.out.println("Получено: " + receivedCommand.toString());

                        receivedCommand.execute(appController, receivedCommand.getArgs());
                        
                        // Обработка команды и формирование ответа
                        Object result = receivedCommand.toString();
                        Answer answer = new Answer(result);
                        
                        // Сериализация и отправка ответа
                        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
                        ObjectOutputStream objOut = new ObjectOutputStream(byteOut);
                        objOut.writeObject(answer);
                        byte[] responseData = byteOut.toByteArray();
                        
                        ByteBuffer responseBuffer = ByteBuffer.wrap(responseData);
                        channel.send(responseBuffer, clientAddress);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }

                if (reader.ready()) {
                    String input = reader.readLine();
                    if (input == null) {
                        ioManager.writeMessage("Введите команду (список команд вы можете посмотреть, написав <help> и нажав Enter)\n", false);
                    } else {
                        String[] tokens = input.trim().split(" ");
                        // System.out.println(Arrays.toString(tokens));
                        if (tokens[0].equals("exit")) {
                            commandManager.executeCommand(appController, new String[] {"save"});
                            System.out.println("Выход из программы...");
                            break;
                        } else {
                            try {
                                commandManager.executeCommand(appController, tokens);
                            } catch (Exception e) {
                                ioManager.writeMessage(e.getMessage(), true);
                            }
                        }
                    }
                    // System.out.println("Вы ввели: " + input);
                }

                Thread.yield();
            }
        }
    }
}