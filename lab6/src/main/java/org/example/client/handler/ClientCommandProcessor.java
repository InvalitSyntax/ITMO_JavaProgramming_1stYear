package org.example.client.handler;

public class ClientCommandProcessor {
    private static final String EXIT_COMMAND = "exit";

    public boolean isExitCommand(String input) {
        return input != null && EXIT_COMMAND.equalsIgnoreCase(input.trim());
    }

    public boolean isSpecialCommand(String input) {
        // Можно добавить другие команды (например, "help")
        return isExitCommand(input);
    }
}
