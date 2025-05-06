package org.example.client;

import org.example.collectionClasses.app.CommandManager;
import org.example.collectionClasses.app.IOManager;
import org.example.collectionClasses.commands.ICommand;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.function.Supplier;

public class ClientApp {
    private final IOManager ioManager;
    private final CommandManager commandManager;
    private final ClientNetworkManager networkManager;
    
    public ClientApp(IOManager ioManager, CommandManager commandManager, 
                    ClientNetworkManager networkManager) {
        this.ioManager = ioManager;
        this.commandManager = commandManager;
        this.networkManager = networkManager;
    }
    
    public void run() {
        System.out.print("Введите команду (список команд вы можете посмотреть, написав <help> и нажав Enter)\n");
        
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                String input = ioManager.getRawStringInput();
                if (input == null) {
                    System.out.print("Введите команду (список команд вы можете посмотреть, написав <help> и нажав Enter)\n");
                } else {
                    String[] tokens = input.trim().split(" ");
                    if (tokens[0].equals("exit")) {
                        break;
                    }
                    
                    try {
                        Supplier<ICommand> command = commandManager.getCommand(tokens[0]);
                        if (command != null) {
                            processCommand(command.get(), tokens);
                        } else {
                            System.out.println("Неизвестная команда: " + tokens[0]);
                        }
                    } catch (Exception e) {
                        System.out.print("Ошибка:" + e);
                    }
                }
            }
        }
    }
    
    private void processCommand(ICommand command, String[] tokens) throws Exception {
        String[] commandArgs = Arrays.copyOfRange(tokens, 1, tokens.length);
        command.setArgs(commandArgs);
        command.setElement(ioManager);
        
        if (command.needToExecutePartOnClient) {
            command.partlyExecute(ioManager);
        }
        
        String response = networkManager.sendCommandAndGetResponse(command);
        if (!response.equals("")){
            System.out.println("Ответ от сервера: \n" + response);
        }
    }
}