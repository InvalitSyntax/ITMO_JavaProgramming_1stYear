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
    
    // Для GUI: десантник задаётся напрямую, а не через ModelBuilder
    // @Override
    // public void setElement(IOManager ioManager) {
    //     ModelBuilder modelBuilder = new ModelBuilder(ioManager);
    //     this.spaceMarine = modelBuilder.build();
    //     this.spaceMarine.setUserLogin(login);
    // }
    public void setSpaceMarine(SpaceMarine marine) {
        this.spaceMarine = marine;
        if (marine != null) marine.setUserLogin(login);
    }
    
    @Override
    public void execute(AppController app, String[] args) {
        IOManager ioManager = app.getIoManager();
        SpaceMarine newMarine = this.spaceMarine;
        if (newMarine == null) {
            ioManager.writeMessage("SpaceMarine не задан!\n", false);
            return;
        }
        List<SpaceMarine> toRemove = app.getSpaceMarineCollectionManager().getMarines().stream()
            .filter(marine -> newMarine.compareTo(marine) < 0)
            .collect(Collectors.toList());
        StringBuilder sb = new StringBuilder();
        toRemove.forEach(marine -> {
            if (app.getSpaceMarineCollectionManager().checkLogin(marine.getId(), login)) {
                app.getDbManager().removeElementById(marine.getId());
                app.loadModel();
                sb.append("Удален десантник: \n").append(marine).append("\n");
            } else {
                sb.append("Элемент коллекции под id ").append(marine.getId()).append(" пренадлежит не вам! и он не был удалён\n");
            }
        });
        ioManager.writeMessage(sb.toString(), false);
    }
}