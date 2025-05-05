package org.example.collectionClasses.commands;

import org.example.collectionClasses.app.AppController;
import org.example.collectionClasses.app.SpaceMarineCollectionManager;

/**
 * Команда для вывода информации о коллекции.
 *
 * @author ISyntax
 * @version 1.0
 */
public class InfoCommand extends ICommand {
    public InfoCommand() {
        super();
    }

    @Override
    public void execute(AppController app, String[] args) {
        SpaceMarineCollectionManager collectionManager = app.getSpaceMarineCollectionManager();
        app.getIoManager().writeMessage(collectionManager.getInfo() + "\n", false);
    }
}