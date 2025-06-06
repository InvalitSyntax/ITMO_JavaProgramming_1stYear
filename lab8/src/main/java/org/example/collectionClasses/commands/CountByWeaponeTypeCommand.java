package org.example.collectionClasses.commands;

import org.example.collectionClasses.app.AppController;
import org.example.collectionClasses.app.IOManager;
import org.example.collectionClasses.model.SpaceMarine;
import org.example.collectionClasses.model.Weapon;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Команда для группировки элементов коллекции по типу оружия.
 *
 * @author ISyntax
 * @version 1.0
 */
public class CountByWeaponeTypeCommand extends ICommand {
    public CountByWeaponeTypeCommand() {
        super();
    }

    @Override
    public void execute(AppController app, String[] args) {
        IOManager ioManager = app.getIoManager();
        
        var counter = app.getSpaceMarineCollectionManager().getMarines().stream()
            .map(SpaceMarine::getWeaponType)
            .filter(Objects::nonNull)
            .collect(Collectors.groupingBy(
                Function.identity(),
                Collectors.counting()
            ));
        
        ioManager.writeMessage(counter + "\n", false);
    }
}