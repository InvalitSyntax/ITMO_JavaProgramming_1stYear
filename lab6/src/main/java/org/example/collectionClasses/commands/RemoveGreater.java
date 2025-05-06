package org.example.collectionClasses.commands;

import org.example.collectionClasses.app.AppController;
import org.example.collectionClasses.app.IOManager;
import org.example.collectionClasses.app.ModelBuilder;
import org.example.collectionClasses.model.SpaceMarine;

import java.util.ArrayDeque;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Команда для удаления всех элементов, превышающих заданный.
 *
 * @author ISyntax
 * @version 1.0
 */
public class RemoveGreater extends ICommand {
    public RemoveGreater() {
        super();
    }
    
    @Override
    public void setElement(IOManager ioManager) {
        ModelBuilder modelBuilder = new ModelBuilder(ioManager);
        this.spaceMarine = modelBuilder.build();
    }
    
    @Override
    public void execute(AppController app, String[] args) {
        IOManager ioManager = app.getIoManager();
        SpaceMarine newMarine = this.spaceMarine;
        
        List<SpaceMarine> toRemove = app.getSpaceMarineCollectionManager().getMarines().stream()
            .filter(marine -> newMarine.compareTo(marine) < 0)
            .collect(Collectors.toList());
        
        toRemove.forEach(marine -> {
            app.getSpaceMarineCollectionManager().removeMarine(marine);
            ioManager.writeMessage("Удален десантник: \n" + marine + "\n", false);
        });
    }
}