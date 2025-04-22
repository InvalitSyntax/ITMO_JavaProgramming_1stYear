package org.example.collectionClasses.commands;

import org.example.collectionClasses.app.AppController;
import org.example.collectionClasses.app.SpaceMarineCollectionManager;
import org.example.collectionClasses.app.XMLIOManager;

/**
 * Команда для сохранения коллекции в файл.
 *
 * @author ISyntax
 * @version 1.0
 */
public class SaveCommand implements Command {
    @Override
    public void execute(AppController app, String[] args) {
        XMLIOManager xmlioManager = app.getXmlioManager();
        SpaceMarineCollectionManager collectionManager = app.getSpaceMarineCollectionManager();
        xmlioManager.saveCollectionToFile(collectionManager);
    }
}