package commands;

import app.AppController;

/**
 * Интерфейс для выполнения команд.
 *
 * @author ISyntax
 * @version 1.0
 */
public interface Command {
    /**
     * Выполняет команду.
     *
     * @param app  контроллер приложения
     * @param args аргументы команды
     */
    void execute(AppController app, String[] args);
}