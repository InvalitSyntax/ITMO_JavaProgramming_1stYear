package org.example.collectionClasses.app;

import org.example.collectionClasses.model.*;

/**
 * Класс для построения объектов модели.
 *
 * @author ISyntax
 * @version 1.0
 */
public class ModelBuilder {
    private final IOManager ioManager;
    private final SpaceMarineCollectionManager spaceMarineCollectionManager;
    private boolean inQuietMode = false;

    /**
     * Конструктор ModelBuilder.
     *
     * @param appController контроллер приложения
     */
    public ModelBuilder(AppController appController) {
        this.ioManager = appController.getIoManager();
        this.spaceMarineCollectionManager = appController.getSpaceMarineCollectionManager();
    }

    /**
     * Устанавливает режим тихого ввода.
     *
     * @param inQuietMode флаг тихого режима
     */
    public void setQuietMode(boolean inQuietMode) {
        this.inQuietMode = inQuietMode;
    }

    /**
     * Создает объект SpaceMarine.
     *
     * @return объект SpaceMarine
     */
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
        if (ioManager.getRawStringInput().equalsIgnoreCase("да")) {
            ioManager.writeMessage("Введите оружие из списка:\n", inQuietMode);
            for (Weapon c : Weapon.values()) {
                ioManager.writeMessage(c + "\n", inQuietMode);
            }
            spaceMarine.setWeaponType(ioManager.getEnumInput(Weapon.class));
        }

        ioManager.writeMessage("Если вы хотите установить оружие ближнего боя введите <да>, иначе что-либо иное\n", inQuietMode);
        if (ioManager.getRawStringInput().equalsIgnoreCase("да")) {
            ioManager.writeMessage("Введите оружие ближнего боя из списка:\n", inQuietMode);
            for (MeleeWeapon c : MeleeWeapon.values()) {
                ioManager.writeMessage(c + "\n", inQuietMode);
            }
            spaceMarine.setMeleeWeapon(ioManager.getEnumInput(MeleeWeapon.class));
        }

        ioManager.writeMessage("Если вы хотите установить главу ведите <да>, иначе что-либо иное\n", inQuietMode);
        if (ioManager.getRawStringInput().equalsIgnoreCase("да")) {
            spaceMarine.setChapter(buildChapter());
        }

        spaceMarine.setId(SpaceMarine.freeId);

        return spaceMarine;
    }

    /**
     * Создает объект Coordinates.
     *
     * @return объект Coordinates
     */
    public Coordinates buildCoordinates() {
        Coordinates coordinates = new Coordinates();

        ioManager.writeMessage("Если вы хотите ввести координату x, напишите <да>, иначе же напишите что-либо ино\n", inQuietMode);
        if (ioManager.getRawStringInput().equalsIgnoreCase("да")) {
            ioManager.writeMessage("Введите координату x (дробное число)\n", inQuietMode);
            coordinates.setX(ioManager.getNumberInput(Double::parseDouble));
        }

        ioManager.writeMessage("Введите координату y (дробное число, должно быть больше -501) космического десантника:\n", inQuietMode);
        coordinates.setY(ioManager.getValidNumberInput(Float::parseFloat, a -> (a > -501)));

        return coordinates;
    }

    /**
     * Создает объект Chapter.
     *
     * @return объект Chapter
     */
    public Chapter buildChapter() {
        Chapter chapter = new Chapter();

        ioManager.writeMessage("Введите имя (не может быть пустым):\n", inQuietMode);
        chapter.setName(ioManager.getValidStringInput(string -> (string != null && !string.isEmpty())));

        ioManager.writeMessage("Введите мир (может быть пустым):\n", inQuietMode);
        chapter.setWorld(ioManager.getRawStringInput());

        return chapter;
    }
}