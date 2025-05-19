package org.example.collectionClasses.app;

import org.example.collectionClasses.commands.ICommand;
import org.example.collectionClasses.commands.Command;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

// https://javarush.com/quests/lectures/questsyntaxpro.level02.lecture05

/**
 * Менеджер команд, управляющий регистрацией и выполнением команд.
 *
 * @author ISyntax
 * @version 1.0
 */
public class CommandManager {
    private final Map<String, Supplier<ICommand>> commandMap;

    /**
     * Конструктор менеджера команд.
     */
    public CommandManager() {
        commandMap = new HashMap<>();
    }

    /**
     * Регистрирует команду.
     *
     * @param commandName имя команды
     * @param command     объект команды
     */
    public void putCommand(String commandName, Supplier<ICommand> command) {
        commandMap.put(commandName, command);
    }

    public void clearCommands(){
        commandMap.clear();
    }

    /**
     * Выполняет команду.
     *
     * @param app    контроллер приложения
     * @param tokens название команды + её аргументы
     */
    public void executeCommand(AppController app, String[] tokens) {
        Supplier<ICommand> command = commandMap.get(tokens[0]);
        if (command != null) {
            String[] args = Arrays.copyOfRange(tokens, 1, tokens.length);
            ((ICommand) command.get()).execute(app, args);
        } else {
            System.out.println("Неизвестная команда: " + tokens[0]);
        }
    }

    public boolean hasCommand(String commandName) {
        return commandMap.containsKey(commandName);
    }

    public Supplier<ICommand> getCommand(String commandName) {
        return commandMap.get(commandName);
    }
}
