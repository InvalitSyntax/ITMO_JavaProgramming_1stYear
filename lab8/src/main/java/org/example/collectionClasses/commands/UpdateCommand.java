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
    public void setElement(IOManager ioManager) {
        ModelBuilder modelBuilder = new ModelBuilder(ioManager);
        this.spaceMarine = modelBuilder.build();
        this.spaceMarine.setUserLogin(login);
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
            if (id < 1){
                ioManager.writeMessage("Вы ввели недопустимый ID!", false);
                return;
            }
            SpaceMarine sm = this.spaceMarine;
            sm.setId(id);
            
            boolean updated = app.getSpaceMarineCollectionManager().getMarines().stream()
                .anyMatch(marine -> marine.getId() == id);
            
            if (updated) {
                if (app.getSpaceMarineCollectionManager().checkLogin(id, login)) {
                    if (app.getDbManager().updateElement(id, sm)) {
                        app.loadModel();
                        ioManager.writeMessage("Элемент обновлен\n", false);
                    } else {
                        ioManager.writeMessage("Не удалось обновить элемент\n", false);
                    }
                } else {
                    ioManager.writeMessage("""
                    Этот элемент коллекции пренадлежит не вам!\s
                    \n""", false);
                    return;
                }
            } else {
                ioManager.writeMessage("""
                    Элемент коллекции с таким id не найден!\s
                    \n""", false);
            }
        } catch (NumberFormatException e) {
            ioManager.writeMessage("Вы ввели не число в качестве id\n", false);
        }
    }
}