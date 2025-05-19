package org.example.collectionClasses.commands;

import org.example.collectionClasses.app.AppController;
import org.example.collectionClasses.app.IOManager;
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
    public void setElement(IOManager ioManager){
        ModelBuilder modelBuilder = new ModelBuilder(ioManager);
        this.spaceMarine = modelBuilder.build();
        this.spaceMarine.setUserLogin(login);
    }
    @Override
    public void execute(AppController app, String[] args) {
        boolean result = app.getDbManager().addElement(this.spaceMarine);
        if (result == true){
            app.getIoManager().writeMessage("Ваш созданный и добавленный десантник:\n" + this.spaceMarine.toString() + "\n", false);
            app.getSpaceMarineCollectionManager().addMarine(this.spaceMarine);
        } else{
            app.getIoManager().writeMessage("Не удалось сохранить десантника\n", false);
        }
    }
}