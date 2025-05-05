package org.example.collectionClasses.commands;

import org.example.collectionClasses.app.AppController;
import org.example.collectionClasses.app.ModelBuilder;
import org.example.collectionClasses.model.SpaceMarine;

/**
 * Команда для добавления нового элемента в коллекцию.
 *
 * @author ISyntax
 * @version 1.0
 */
public class AddCommand extends ICommand{
    public AddCommand() {
        super();
    }
    @Override
    public void execute(AppController app, String[] args) {
        ModelBuilder modelBuilder = new ModelBuilder(app);
        SpaceMarine sm = modelBuilder.build();
        app.getIoManager().writeMessage("Ваш созданный и добавленный десантник:\n" + sm.toString() + "\n", false);
        app.getSpaceMarineCollectionManager().addMarine(sm);
    }
}