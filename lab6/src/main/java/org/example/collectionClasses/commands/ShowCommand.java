package org.example.collectionClasses.commands;

import org.example.collectionClasses.app.AppController;
import org.example.collectionClasses.app.SpaceMarineCollectionManager;

/**
 * Команда для вывода всех элементов коллекции.
 *
 * @author ISyntax
 * @version 1.0
 */
public class ShowCommand implements Command {
    @Override
    public void execute(AppController app, String[] args) {
        SpaceMarineCollectionManager collectionManager = app.getSpaceMarineCollectionManager();
        app.getIoManager().writeMessage(collectionManager.toString() + "\n", false);
    }
}