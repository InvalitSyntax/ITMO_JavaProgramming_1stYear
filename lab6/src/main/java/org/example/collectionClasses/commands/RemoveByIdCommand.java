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
            boolean removed = app.getSpaceMarineCollectionManager().getMarines()
                .removeIf(marine -> marine.getId() == id);
            
            if (removed) {
                ioManager.writeMessage("Десантник удален\n", false);
            } else {
                ioManager.writeMessage("""
                    Элемент коллекции с таким id не найден!\s
                    Введите show, чтобы вывести список доступных элементов.
                    \n""", false);
            }
        } catch (NumberFormatException e) {
            ioManager.writeMessage("Неверный формат ID\n", false);
        }
    }
}