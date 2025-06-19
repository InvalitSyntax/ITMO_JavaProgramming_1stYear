package org.example.client;

import org.example.collectionClasses.app.CommandManager;
import org.example.collectionClasses.app.IOManager;
import org.example.collectionClasses.commands.AddCommand;
import org.example.collectionClasses.commands.Answer;
import org.example.collectionClasses.commands.AuthorizeCommand;
import org.example.collectionClasses.commands.ClearCommand;
import org.example.collectionClasses.commands.CountByWeaponeTypeCommand;
import org.example.collectionClasses.commands.CountLessThanLoyalCommand;
import org.example.collectionClasses.commands.ExecuteScriptCommand;
import org.example.collectionClasses.commands.ExitCommand;
import org.example.collectionClasses.commands.FilterLesThanChapterCommand;
import org.example.collectionClasses.commands.HelpCommand;
import org.example.collectionClasses.commands.HelpCommandNotAutorized;
import org.example.collectionClasses.commands.ICommand;
import org.example.collectionClasses.commands.InfoCommand;
import org.example.collectionClasses.commands.RegisterCommand;
import org.example.collectionClasses.commands.RemoveByIdCommand;
import org.example.collectionClasses.commands.RemoveFirstCommand;
import org.example.collectionClasses.commands.RemoveGreater;
import org.example.collectionClasses.commands.RemoveHeadCommand;
import org.example.collectionClasses.commands.ShowCommand;
import org.example.collectionClasses.commands.UpdateCommand;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.function.Supplier;

public class ClientApp {
    private final IOManager ioManager;
    private final CommandManager commandManager;
    private final ClientNetworkManager networkManager;
    private boolean authorized;
    private String login, password;
    
    public ClientApp(IOManager ioManager, CommandManager commandManager, 
                    ClientNetworkManager networkManager) {
        this.ioManager = ioManager;
        this.commandManager = commandManager;
        this.networkManager = networkManager;
        registerCommandsWhenNotAutorized();
    }
    
    public void run() {
        System.out.print("""
            Зарегистрируйтесь или авторизируйтесь с помощью команд:
                authorize <login> <password>
                register <login> <password>

                вместо <> вставьте необходимое соответственно
            """);
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
                        boolean condition = processCommand(command.get(), tokens);
                        if (this.authorized == false){
                            this.authorized = condition;
                            if (this.authorized == true){
                                this.login = tokens[1];
                                this.password = tokens[2];
                            }
                            registerCommandsWhenAutorized();
                        }
                    } else {
                        System.out.println("Неизвестная команда: " + tokens[0]);
                    }
                } catch (Exception e) {
                    System.out.print("Ошибка:" + e);
                }
            }
        }
    }
    
    private boolean processCommand(ICommand command, String[] tokens) throws Exception {
        if (this.authorized == true){
            command.login = this.login;
            command.password = this.password;
        }
        String[] commandArgs = Arrays.copyOfRange(tokens, 1, tokens.length);
        command.setArgs(commandArgs);
        command.setElement(ioManager);
        
        if (command.needToExecutePartOnClient) {
            command.partlyExecute(ioManager);
        }
        
        Answer answer = networkManager.sendCommandAndGetResponse(command);
        if (!answer.toString().equals("")){
            System.out.println("Ответ от сервера: \n" + answer.toString());
        }
        return answer.condition();
    }

    private void registerCommandsWhenAutorized() {
        if (authorized == false){
            return;
        }
        commandManager.clearCommands();
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
    }

    private void registerCommandsWhenNotAutorized() {
        commandManager.clearCommands();
        commandManager.putCommand("help", HelpCommandNotAutorized::new);
        commandManager.putCommand("authorize", AuthorizeCommand::new);
        commandManager.putCommand("register", RegisterCommand::new);
    }
    // --- Добавлено для GUI ---
    private static ClientApp guiClientApp;
    public static void initForGUI(IOManager ioManager, CommandManager commandManager, ClientNetworkManager networkManager) {
        guiClientApp = new ClientApp(ioManager, commandManager, networkManager);
    }
    public static Answer loginWithAnswer(String login, String password) throws Exception {
        if (guiClientApp == null) throw new IllegalStateException("ClientApp не инициализирован для GUI");
        String[] tokens = {"authorize", login, password};
        AuthorizeCommand cmd = new AuthorizeCommand();
        return guiClientApp.processCommandWithAnswer(cmd, tokens);
    }
    public static Answer registerWithAnswer(String login, String password) throws Exception {
        if (guiClientApp == null) throw new IllegalStateException("ClientApp не инициализирован для GUI");
        String[] tokens = {"register", login, password};
        RegisterCommand cmd = new RegisterCommand();
        return guiClientApp.processCommandWithAnswer(cmd, tokens);
    }
    public static Answer getCollectionFromServer() throws Exception {
        if (guiClientApp == null) throw new IllegalStateException("ClientApp не инициализирован для GUI");
        String[] tokens = {"update_collection"};
        ICommand cmd = new org.example.collectionClasses.commands.UpdateCollectionCommand();
        return guiClientApp.processCommandWithAnswer(cmd, tokens);
    }
    // Новый метод для возврата Answer
    public Answer processCommandWithAnswer(ICommand command, String[] tokens) throws Exception {
        if (this.authorized == true){
            command.login = this.login;
            command.password = this.password;
        }
        String[] commandArgs = Arrays.copyOfRange(tokens, 1, tokens.length);
        command.setArgs(commandArgs);
        command.setElement(ioManager);
        if (command.needToExecutePartOnClient) {
            command.partlyExecute(ioManager);
        }
        Answer answer = networkManager.sendCommandAndGetResponse(command);
        // Логгируем ответ сервера в консоль всегда
        if (answer != null && !answer.toString().isEmpty()) {
            System.out.println("Ответ от сервера: " + answer.toString());
        }
        return answer;
    }

    public static Answer processCommandFromGUI(ICommand cmd, String[] tokens) throws Exception {
        if (guiClientApp == null) throw new IllegalStateException("ClientApp не инициализирован для GUI");
        return guiClientApp.processCommandWithAnswer(cmd, tokens);
    }
    public static void setGuiLogin(String login) {
        if (guiClientApp != null) guiClientApp.login = login;
        if (guiClientApp != null) guiClientApp.authorized = true;
    }
    public static void setGuiPassword(String password) {
        if (guiClientApp != null) guiClientApp.password = password;
        if (guiClientApp != null) guiClientApp.authorized = true;
    }
    // --- Для GUI: поддержка скриптов ---
    public static void executeScriptInGUI(String scriptPath) throws Exception {
        if (guiClientApp == null) throw new IllegalStateException("ClientApp не инициализирован для GUI");
            while (true) {
            String input = guiClientApp.ioManager.getRawStringInput();
            if (input == null) {
                break; // Выход из цикла, если ввод пустой - закончились сканеры
            } else {
                String[] tokens = input.trim().split(" ");
                if (tokens[0].equals("exit")) {
                    break;
                }
                
                try {
                    Supplier<ICommand> command = guiClientApp.commandManager.getCommand(tokens[0]);
                    if (command != null) {
                        boolean condition = guiClientApp.processCommand(command.get(), tokens);
                        if (guiClientApp.authorized == false){
                            guiClientApp.authorized = condition;
                            if (guiClientApp.authorized == true){
                                guiClientApp.login = tokens[1];
                                guiClientApp.password = tokens[2];
                            }
                            guiClientApp.registerCommandsWhenAutorized();
                        }
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