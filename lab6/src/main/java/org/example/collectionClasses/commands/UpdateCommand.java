package org.example.collectionClasses.commands;

import org.example.collectionClasses.app.AppController;
import org.example.collectionClasses.app.IOManager;
import org.example.collectionClasses.app.ModelBuilder;
import org.example.collectionClasses.model.SpaceMarine;

import java.util.ArrayDeque;

/**
 * Команда для обновления элемента коллекции по ID.
 *
 * @author ISyntax
 * @version 1.0
 */
public class UpdateCommand extends ICommand {
    public UpdateCommand() {
        super();
    }
    @Override
    public void setElement(IOManager ioManager){
        ModelBuilder modelBuilder = new ModelBuilder(ioManager);
        this.spaceMarine = modelBuilder.build();
    }
    @Override
    public void execute(AppController app, String[] args) {
        IOManager ioManager = app.getIoManager();
        ArrayDeque<SpaceMarine> marineArrayDeque = app.getSpaceMarineCollectionManager().getMarines();
        if (args.length == 0) {
            ioManager.writeMessage("Вы не ввели id элемента коллекции!\n", false);
        } else {
            int id;
            try {
                id = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                ioManager.writeMessage("Вы ввели не число в качестве id\n", false);
                return;
            }

            boolean flag = false;
            for (SpaceMarine marine : marineArrayDeque) {
                if (marine.getId() == id) {
                    flag = true;
                    SpaceMarine sm = this.spaceMarine;
                    sm.setId(id);
                    app.getSpaceMarineCollectionManager().replaceMarineById(id, sm);
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