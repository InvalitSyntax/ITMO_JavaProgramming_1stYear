package org.example.collectionClasses.commands;

import org.example.collectionClasses.app.AppController;

/**
 * Команда для удаления первого элемента из коллекции.
 *
 * @author ISyntax
 * @version 1.0
 */
public class RemoveFirstCommand extends ICommand {
    public RemoveFirstCommand() {
        super();
    }
    @Override
    public void execute(AppController app, String[] args) {
        if (!app.getSpaceMarineCollectionManager().getMarines().isEmpty()) {
            app.getIoManager().writeMessage("Удален десантник под id: " + app.getSpaceMarineCollectionManager().getMarines().getFirst().getId() + "\n", false);
            app.getSpaceMarineCollectionManager().getMarines().removeFirst();
        } else {
            app.getIoManager().writeMessage("Коллекция уже пустая, ничего не удалено!\n", false);
        }
    }
}