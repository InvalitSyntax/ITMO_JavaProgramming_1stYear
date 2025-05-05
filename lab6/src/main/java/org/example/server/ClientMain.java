package org.example.server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Arrays;
import java.util.Scanner;
import java.util.function.Supplier;

import org.example.collectionClasses.app.CommandManager;
import org.example.collectionClasses.app.IOManager;
import org.example.collectionClasses.commands.ICommand;
import org.example.collectionClasses.commands.AddCommand;
import org.example.collectionClasses.commands.Answer;
import org.example.collectionClasses.commands.ClearCommand;
import org.example.collectionClasses.commands.CountByWeaponeTypeCommand;
import org.example.collectionClasses.commands.CountLessThanLoyalCommand;
import org.example.collectionClasses.commands.ExecuteScriptCommand;
import org.example.collectionClasses.commands.ExitCommand;
import org.example.collectionClasses.commands.FilterLesThanChapterCommand;
import org.example.collectionClasses.commands.HelpCommand;
import org.example.collectionClasses.commands.InfoCommand;
import org.example.collectionClasses.commands.RemoveByIdCommand;
import org.example.collectionClasses.commands.RemoveFirstCommand;
import org.example.collectionClasses.commands.RemoveGreater;
import org.example.collectionClasses.commands.RemoveHeadCommand;
import org.example.collectionClasses.commands.SaveCommand;
import org.example.collectionClasses.commands.ShowCommand;
import org.example.collectionClasses.commands.UpdateCommand;

public class ClientMain {
    public static void main(String[] args) throws IOException {
        try (Scanner scanner = new Scanner(System.in)) {
            IOManager ioManager = new IOManager();
            ioManager.setIsClient(true);
            CommandManager commandManager = new CommandManager();
            commandManager.putCommand("help", HelpCommand::new);
            commandManager.putCommand("exit", ExitCommand::new);
            commandManager.putCommand("show", ShowCommand::new);
            commandManager.putCommand("info", InfoCommand::new);
            commandManager.putCommand("clear", ClearCommand::new);
            commandManager.putCommand("add", AddCommand::new);
            commandManager.putCommand("update", UpdateCommand::new);
            commandManager.putCommand("remove_by_id", RemoveByIdCommand::new);
            commandManager.putCommand("remove_first", RemoveFirstCommand::new);
            commandManager.putCommand("remove_head", RemoveHeadCommand::new);
            commandManager.putCommand("group_counting_by_weapon_type", CountByWeaponeTypeCommand::new);
            commandManager.putCommand("count_less_than_loyal", CountLessThanLoyalCommand::new);
            commandManager.putCommand("filter_less_than_chapter", FilterLesThanChapterCommand::new);
            commandManager.putCommand("remove_greater", RemoveGreater::new);
            commandManager.putCommand("execute_script", ExecuteScriptCommand::new);


            System.out.print("Введите команду (список команд вы можете посмотреть, написав <help> и нажав Enter)\n");
            while (true) {
                String input = ioManager.getRawStringInput();
                if (input == null) {
                    System.out.print("Введите команду (список команд вы можете посмотреть, написав <help> и нажав Enter)\n");
                } else {
                    String[] tokens = input.trim().split(" ");
                    try {
                        Supplier<ICommand> commandSupplier = commandManager.getCommand(tokens[0]);
                        if (commandSupplier != null) {
                            ICommand command = commandSupplier.get();
                            String[] commandArgs = Arrays.copyOfRange(tokens, 1, tokens.length);
                            command.setArgs(commandArgs);
                            command.setElement(ioManager);
                            if (command.needToExecutePartOnClient){
                                command.partlyExecute(ioManager);
                            }
                            // Отправка команды
                            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
                            ObjectOutputStream objOut = new ObjectOutputStream(byteOut);
                            objOut.writeObject(command);
                            byte[] data = byteOut.toByteArray();

                            DatagramChannel channel = DatagramChannel.open();
                            channel.send(ByteBuffer.wrap(data), new InetSocketAddress("localhost", 8000));

                            // Получение ответа
                            ByteBuffer responseBuffer = ByteBuffer.allocate(4096);
                            InetSocketAddress serverAddress = (InetSocketAddress) channel.receive(responseBuffer);
                            if (serverAddress != null) {
                                responseBuffer.flip();
                                byte[] responseData = new byte[responseBuffer.remaining()];
                                responseBuffer.get(responseData);
                                
                                try (ObjectInputStream objIn = new ObjectInputStream(new ByteArrayInputStream(responseData))) {
                                    Answer answer = (Answer) objIn.readObject();
                                    System.out.println("Ответ от сервера: \n" + answer.toString());
                                }
                            }
                        } else {
                            System.out.println("Неизвестная команда: " + tokens[0]);
                        }
                    } catch (Exception e) {
                        System.out.print("Ошибка: 324789" + e);
                    }
                }
            }
        }
    }
}