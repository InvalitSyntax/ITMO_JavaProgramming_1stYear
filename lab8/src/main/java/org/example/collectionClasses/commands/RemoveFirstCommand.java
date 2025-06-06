package org.example.collectionClasses.commands;

import org.example.collectionClasses.app.AppController;
import org.example.collectionClasses.model.SpaceMarine;

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
            SpaceMarine sm = app.getSpaceMarineCollectionManager().getMarines().getFirst();
            if (app.getSpaceMarineCollectionManager().checkLogin(sm.getId(), login)) {
                if (app.getDbManager().removeElementById(sm.getId())) {
                    app.getIoManager().writeMessage("Удален десантник под id: "  + sm.getId() + "\n", false);
                    app.loadModel();
                } else {
                    app.getIoManager().writeMessage("Не удалось удалить десантиника!\n" + sm + "\n", false);
                }
            } else {
                app.getIoManager().writeMessage("""
                        Этот элемент коллекции пренадлежит не вам!\s
                        Введите show, чтобы вывести список доступных для вас элементов.
                        \n""", false);
                        return;
            }
        } else {
                app.getIoManager().writeMessage("Коллекция уже пустая, ничего не удалено!\n", false);
        }
    }
}