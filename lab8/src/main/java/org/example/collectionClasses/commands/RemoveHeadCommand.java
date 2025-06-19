package org.example.collectionClasses.commands;

import org.example.collectionClasses.app.AppController;
import org.example.collectionClasses.model.SpaceMarine;

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
            SpaceMarine sm = app.getSpaceMarineCollectionManager().getMarines().getFirst();
            if (app.getSpaceMarineCollectionManager().checkLogin(sm.getId(), login)) {
                if (app.getDbManager().removeElementById(sm.getId())) {
                    app.getIoManager().writeMessage("Удален десантник\n" + sm + "\n", false);
                    app.loadModel();
                } else {
                    app.getIoManager().writeMessage("Не удалось удалить десантиника!\n" + sm + "\n", false);
                }
            } else {
                app.getIoManager().writeMessage("""
                        Этот элемент коллекции пренадлежит не вам!\s
                        \n""", false);
                        return;
            }
        } else {
                app.getIoManager().writeMessage("Коллекция уже пустая, ничего не удалено!\n", false);
        }
}
}