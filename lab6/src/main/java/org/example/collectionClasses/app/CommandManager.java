package org.example.collectionClasses.app;

import org.example.collectionClasses.commands.Command;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

// https://javarush.com/quests/lectures/questsyntaxpro.level02.lecture05

/**
 * Менеджер команд, управляющий регистрацией и выполнением команд.
 *
 * @author ISyntax
 * @version 1.0
 */
public class CommandManager {
    private final Map<String, Command> commandMap;

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
    public void putCommand(String commandName, Command command) {
        commandMap.put(commandName, command);
    }

    /**
     * Выполняет команду.
     *
     * @param app    контроллер приложения
     * @param tokens название команды + её аргументы
     */
    public void executeCommand(AppController app, String[] tokens) {
        Command command = commandMap.get(tokens[0]);
        if (command != null) {
            String[] args = Arrays.copyOfRange(tokens, 1, tokens.length);
            command.execute(app, args);
        } else {
            System.out.println("Неизвестная команда: " + tokens[0]);
        }
    }
}
