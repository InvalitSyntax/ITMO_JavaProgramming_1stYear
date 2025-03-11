package commands;

import app.AppController;
import app.IOManager;
import model.SpaceMarine;

import java.util.ArrayDeque;

public class RemoveByIdCommand implements Command {
    @Override
    public void execute(AppController app, String[] args) {
        IOManager ioManager = app.getIoManager();
        ArrayDeque<SpaceMarine> marineArrayDeque = app.getSpaceMarineCollectionManager().getMarines();
        if (args.length == 0) {
            ioManager.writeMessage("Вы не ввели id элемента коллекции!\n", false);
        }
        else{
            int id = Integer.parseInt(args[0]);
            boolean flag = false;
            for(SpaceMarine dragon : marineArrayDeque){
                if (dragon.getId() == id){
                    flag = true;
                    app.getSpaceMarineCollectionManager().removeMarineById(id);
                    ioManager.writeMessage("Десантник удален\n", false);
                    break;
                }
            }
            if (!flag){
                ioManager.writeMessage("""
                        Элемент коллекции с таким id не найден!\s
                        Введите show, чтобы вывести список доступных элементов.
                        \n""", false);
            }

        }
    }
}
