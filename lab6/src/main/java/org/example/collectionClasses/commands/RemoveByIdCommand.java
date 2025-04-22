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
public class RemoveByIdCommand implements Command {
    @Override
    public void execute(AppController app, String[] args) {
        IOManager ioManager = app.getIoManager();
        ArrayDeque<SpaceMarine> marineArrayDeque = app.getSpaceMarineCollectionManager().getMarines();
        if (args.length == 0) {
            ioManager.writeMessage("Вы не ввели id элемента коллекции!\n", false);
        } else {
            int id = Integer.parseInt(args[0]);
            boolean flag = false;
            for (SpaceMarine marine : marineArrayDeque) {
                if (marine.getId() == id) {
                    flag = true;
                    app.getSpaceMarineCollectionManager().removeMarineById(id);
                    ioManager.writeMessage("Десантник удален\n", false);
                    break;
                }
            }
            if (!flag) {
                ioManager.writeMessage("""
                        Элемент коллекции с таким id не найден!\s
                        Введите show, чтобы вывести список доступных элементов.
                        \n""", false);
            }
        }
    }
}