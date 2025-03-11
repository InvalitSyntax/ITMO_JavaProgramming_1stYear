package commands;

import app.AppController;
import app.IOManager;
import app.ModelBuilder;
import app.SpaceMarineCollectionManager;
import model.SpaceMarine;

import java.util.ArrayDeque;

public class UpdateCommand implements Command {
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
                    SpaceMarine sm = new ModelBuilder(app, id).build();
                    app.getSpaceMarineCollectionManager().replaceMarineById(id, sm);
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
