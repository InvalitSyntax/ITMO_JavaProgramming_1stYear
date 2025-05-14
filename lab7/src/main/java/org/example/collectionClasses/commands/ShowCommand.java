package org.example.collectionClasses.commands;

import org.example.collectionClasses.app.AppController;
import org.example.collectionClasses.app.SpaceMarineCollectionManager;
import org.example.collectionClasses.model.SpaceMarine;

import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * Команда для вывода всех элементов коллекции, отсортированных по местоположению.
 *
 * @author ISyntax
 * @version 1.0
 */
public class ShowCommand extends ICommand {
    public ShowCommand() {
        super();
    }
    
    @Override
    public void execute(AppController app, String[] args) {
        SpaceMarineCollectionManager collectionManager = app.getSpaceMarineCollectionManager();
        
        String sortedCollection = collectionManager.getMarines().stream()
                .sorted(Comparator.comparingDouble((SpaceMarine sm) -> sm.getCoordinates().getX())
                        .thenComparing(sm -> sm.getCoordinates().getY()))
                .map(SpaceMarine::toString)
                .collect(Collectors.joining("\n\n"));
        
        app.getIoManager().writeMessage(sortedCollection + "\n", false);
    }
}