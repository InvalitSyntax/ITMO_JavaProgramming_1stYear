package app;

import model.*;

import java.util.function.Predicate;

public class ModelBuilder {
    private final IOManager ioManager;
    private boolean inQuietMode = false;
    private final SpaceMarineCollectionManager spaceMarineCollectionManager;

    public ModelBuilder(AppController appController) {
        this.ioManager = appController.getIoManager();
        this.spaceMarineCollectionManager = appController.getSpaceMarineCollectionManager();
    }

    public void setQuietMode(boolean inQuietMode) {
        this.inQuietMode = inQuietMode;
    }

    public SpaceMarine build() {
        SpaceMarine spaceMarine = new SpaceMarine();

        ioManager.writeMessage("Введите непустое имя космического десантника:\n", inQuietMode);
        spaceMarine.setName(ioManager.getValidStringInput(string -> (string != null && !string.isEmpty())));

        ioManager.writeMessage("Введите координаты космического десантника:\n", inQuietMode);
        spaceMarine.setCoordinates(buildCoordinates());

        ioManager.writeMessage("Введите здоровье (дробное число, должно быть больше 0)\n", inQuietMode);
        spaceMarine.setHealth(ioManager.getValidNumberInput(Float::parseFloat, a -> (a > 0)));

        ioManager.writeMessage("Введите лояльность (true или что-либо другое, тогда установится false)\n", inQuietMode);
        spaceMarine.setLoyal(ioManager.getBooleanInput(Boolean::parseBoolean));

        ioManager.writeMessage("Если вы хотите установить оружие введите <да>, иначе что-либо иное\n", inQuietMode);
        if (ioManager.getRawStringInput().equalsIgnoreCase("да")){
            ioManager.writeMessage("Введите оружие из списка:\n", inQuietMode);
            for (Weapon c : Weapon.values()) {
                ioManager.writeMessage(c + "\n", inQuietMode);
            }
            spaceMarine.setWeaponType(ioManager.getEnumInput(Weapon.class));
        }

        ioManager.writeMessage("Если вы хотите установить оружие ближнего боя введите <да>, иначе что-либо иное\n", inQuietMode);
        if (ioManager.getRawStringInput().equalsIgnoreCase("да")){
            ioManager.writeMessage("Введите оружие ближнего боя из списка:\n", inQuietMode);
            for (MeleeWeapon c : MeleeWeapon.values()) {
                ioManager.writeMessage(c + "\n", inQuietMode);
            }
            spaceMarine.setMeleeWeapon(ioManager.getEnumInput(MeleeWeapon.class));
        }

        ioManager.writeMessage("Если вы хотите установить главу ведите <да>, иначе что-либо иное\n", inQuietMode);
        if (ioManager.getRawStringInput().equalsIgnoreCase("да")){
            spaceMarine.setChapter(buildChapter());
        }

        spaceMarine.setId(spaceMarineCollectionManager.getNextFreeId());

        return spaceMarine;
    }

    public Coordinates buildCoordinates() {
        Coordinates coordinates = new Coordinates();

        ioManager.writeMessage("Если вы хотите ввести координату x, напишите <да>, иначе же напишите что-либо ино\n", inQuietMode);
        if (ioManager.getRawStringInput().equalsIgnoreCase("да")){
            ioManager.writeMessage("Введите координату x (дробное число)\n", inQuietMode);
            coordinates.setX(ioManager.getNumberInput(Double::parseDouble));
        }

        ioManager.writeMessage("Введите координату y (дробное число, должно быть больше -501) космического десантника:\n", inQuietMode);
        coordinates.setY(ioManager.getValidNumberInput(Float::parseFloat, a -> (a > -501)));

        return coordinates;
    }

    public Chapter buildChapter() {
        Chapter chapter = new Chapter();

        ioManager.writeMessage("Введите имя (не может быть пустым):\n", inQuietMode);
        chapter.setName(ioManager.getValidStringInput(string -> (string != null && !string.isEmpty())));

        ioManager.writeMessage("Введите мир (может быть пустым):\n", inQuietMode);
        chapter.setWorld(ioManager.getRawStringInput());

        return chapter;
    }
}
