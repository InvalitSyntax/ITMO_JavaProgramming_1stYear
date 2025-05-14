package org.example.collectionClasses.commands;

import org.example.collectionClasses.app.AppController;

/**
 * Команда для завершения работы программы.
 *
 * @author ISyntax
 * @version 1.0
 */
public class ExitCommand extends ICommand {
    public ExitCommand() {
        super();
    }

    @Override
    public void execute(AppController app, String[] args) {
        app.setTurnOn(false);
    }
}