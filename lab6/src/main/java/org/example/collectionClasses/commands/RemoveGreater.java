package org.example.collectionClasses.commands;

import org.example.collectionClasses.app.AppController;
import org.example.collectionClasses.app.IOManager;
import org.example.collectionClasses.app.ModelBuilder;
import org.example.collectionClasses.model.SpaceMarine;

import java.util.ArrayDeque;

/**
 * Команда для удаления всех элементов, превышающих заданный.
 *
 * @author ISyntax
 * @version 1.0
 */
public class RemoveGreater implements Command {
    @Override
    public void execute(AppController app, String[] args) {
        IOManager ioManager = app.getIoManager();
        SpaceMarine newMarine = new ModelBuilder(app).build();
        ArrayDeque<SpaceMarine> marineArrayDeque = app.getSpaceMarineCollectionManager().getMarines();
        for (SpaceMarine marine : marineArrayDeque) {
            if (newMarine.compareTo(marine) < 0) {
                app.getSpaceMarineCollectionManager().removeMarine(marine);
                ioManager.writeMessage("Удален десантник: \n" + marine.toString() + "\n", false);
            }
        }
    }
}