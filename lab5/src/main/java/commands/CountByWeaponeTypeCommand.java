package commands;

import app.AppController;
import app.IOManager;
import model.SpaceMarine;
import model.Weapon;

import java.util.ArrayDeque;
import java.util.HashMap;

/**
 * Команда для группировки элементов коллекции по типу оружия.
 *
 * @author ISyntax
 * @version 1.0
 */
public class CountByWeaponeTypeCommand implements Command {
    @Override
    public void execute(AppController app, String[] args) {
        IOManager ioManager = app.getIoManager();
        HashMap<Weapon, Integer> counter = new HashMap<>();
        for (Weapon weapon : Weapon.values()) {
            counter.put(weapon, 0);
        }
        ArrayDeque<SpaceMarine> marineArrayDeque = app.getSpaceMarineCollectionManager().getMarines();
        for (SpaceMarine marine : marineArrayDeque) {
            Weapon weapon = marine.getWeaponType();
            if (weapon != null) {
                counter.put(weapon, counter.get(weapon) + 1);
            }
        }
        ioManager.writeMessage(counter.toString() + "\n", false);
    }
}