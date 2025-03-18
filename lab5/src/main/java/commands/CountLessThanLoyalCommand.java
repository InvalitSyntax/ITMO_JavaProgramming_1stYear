package commands;

import app.AppController;
import app.IOManager;
import model.SpaceMarine;

import java.util.ArrayDeque;

/**
 * Команда для подсчета элементов, значение поля loyal которых меньше заданного.
 *
 * @author ISyntax
 * @version 1.0
 */
public class CountLessThanLoyalCommand implements Command {
    @Override
    public void execute(AppController app, String[] args) {
        IOManager ioManager = app.getIoManager();
        ArrayDeque<SpaceMarine> marineArrayDeque = app.getSpaceMarineCollectionManager().getMarines();
        if (args.length == 0) {
            ioManager.writeMessage("Вы не ввели loyal, по которому сравнивать!\n", false);
        } else {
            boolean loyal;
            switch (args[0].toLowerCase()) {
                case "true": loyal = true; break;
                case "false": loyal = false; break;
                default: ioManager.writeMessage("Вы ввели не boolean значение!\n", false); return;
            }
            int count = 0;
            for (SpaceMarine marine : marineArrayDeque) {
                if (marine.getLoyal() != loyal) {
                    count++;
                }
            }
            ioManager.writeMessage("Количество элементов: " + count + "\n", false);
        }
    }
}