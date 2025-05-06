package org.example.collectionClasses.commands;

import org.example.collectionClasses.app.AppController;
import org.example.collectionClasses.app.IOManager;
import org.example.collectionClasses.model.SpaceMarine;

import java.util.ArrayDeque;

/**
 * Команда для подсчета элементов, значение поля loyal которых меньше заданного.
 *
 * @author ISyntax
 * @version 1.0
 */
public class CountLessThanLoyalCommand extends ICommand {
    public CountLessThanLoyalCommand() {
        super();
    }
    
    @Override
    public void execute(AppController app, String[] args) {
        IOManager ioManager = app.getIoManager();
        
        if (args.length == 0) {
            ioManager.writeMessage("Вы не ввели loyal, по которому сравнивать!\n", false);
            return;
        }
        
        try {
            boolean loyal = Boolean.parseBoolean(args[0]);
            long count = app.getSpaceMarineCollectionManager().getMarines().stream()
                .filter(marine -> marine.getLoyal() != loyal)
                .count();
            
            ioManager.writeMessage("Количество элементов: " + count + "\n", false);
        } catch (IllegalArgumentException e) {
            ioManager.writeMessage("Вы ввели не boolean значение!\n", false);
        }
    }
}