package input;

import commands.*;
import controll.AppController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

// https://javarush.com/quests/lectures/questsyntaxpro.level02.lecture05

public class CommandManager {
    private Map<String, Command> commandMap;

    public CommandManager() {
        commandMap = new HashMap<>();
    }

    public void putCommand(String commandName, Command command) {
        commandMap.put(commandName, command);
    }

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
