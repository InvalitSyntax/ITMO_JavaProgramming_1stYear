package org.example.collectionClasses.commands;

import org.example.collectionClasses.app.AppController;
import org.example.collectionClasses.app.SpaceMarineCollectionManager;

/**
 * Команда для очистки коллекции.
 *
 * @author ISyntax
 * @version 1.0
 */
public class ClearCommand implements Command {
    @Override
    public void execute(AppController app, String[] args) {
        SpaceMarineCollectionManager collectionManager = app.getSpaceMarineCollectionManager();
        collectionManager.clearMarines();
    }
}