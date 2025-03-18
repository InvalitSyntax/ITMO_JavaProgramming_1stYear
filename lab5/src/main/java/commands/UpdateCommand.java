package commands;

import app.AppController;
import app.IOManager;
import app.ModelBuilder;
import model.SpaceMarine;

import java.util.ArrayDeque;

/**
 * Команда для обновления элемента коллекции по ID.
 *
 * @author ISyntax
 * @version 1.0
 */
public class UpdateCommand implements Command {
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
                    SpaceMarine sm = new ModelBuilder(app).build();
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