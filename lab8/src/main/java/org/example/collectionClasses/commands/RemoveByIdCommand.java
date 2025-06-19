package org.example.collectionClasses.commands;

import org.example.collectionClasses.app.AppController;
import org.example.collectionClasses.app.IOManager;
import org.example.collectionClasses.model.SpaceMarine;

import java.util.ArrayDeque;

/**
 * Команда для удаления элемента из коллекции по его ID.
 *
 * @author ISyntax
 * @version 1.0
 */
public class RemoveByIdCommand extends ICommand {
    public RemoveByIdCommand() {
        super();
    }

    @Override
    public void execute(AppController app, String[] args) {
        IOManager ioManager = app.getIoManager();
        if (args.length == 0) {
            ioManager.writeMessage("Вы не ввели id элемента коллекции!\n", false);
            return;
        }
        try {
            int id = Integer.parseInt(args[0]);
            // Проверка прав пользователя
            if (app.getSpaceMarineCollectionManager().checkLogin(id, login)) {
                // Удаление из базы данных
                if (app.getDbManager().removeElementById(id)) {
                    // Перезагрузка коллекции из базы
                    app.loadModel();
                    ioManager.writeMessage("Десантник удален\n", false);
                } else {
                    ioManager.writeMessage("Не удалось удалить элемент\n", false);
                }
            } else {
                ioManager.writeMessage("У вас нет прав для удаления этого десантника\n", false);
            }
        } catch (NumberFormatException e) {
            ioManager.writeMessage("Неверный формат ID\n", false);
        }
    }
}