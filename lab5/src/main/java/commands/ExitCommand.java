package commands;

import app.AppController;

/**
 * Команда для завершения работы программы.
 *
 * @author ISyntax
 * @version 1.0
 */
public class ExitCommand implements Command {
    @Override
    public void execute(AppController app, String[] args) {
        app.setTurnOn(false);
    }
}