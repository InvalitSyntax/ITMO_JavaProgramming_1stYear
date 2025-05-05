package org.example.collectionClasses.commands;

import org.example.collectionClasses.app.AppController;

/**
 * Команда для вывода и удаления первого элемента коллекции.
 *
 * @author ISyntax
 * @version 1.0
 */
public class RemoveHeadCommand extends ICommand {
    public RemoveHeadCommand() {
        super();
    }

    @Override
    public void execute(AppController app, String[] args) {
        if (!app.getSpaceMarineCollectionManager().getMarines().isEmpty()) {
            app.getIoManager().writeMessage("Удален десантник\n" + app.getSpaceMarineCollectionManager().getMarines().getFirst() + "\n", false);
            app.getSpaceMarineCollectionManager().getMarines().removeFirst();
        } else {
            app.getIoManager().writeMessage("Коллекция уже пустая, ничего не удалено!\n", false);
        }
    }
}