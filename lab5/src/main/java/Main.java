import collectionManager.CollectionLoaderFromFile;
import collectionManager.CollectionSaverInFile;
import collectionManager.SpaceMarineCollection;
import collectionObjects.*;

import java.io.IOException;

/*

TODO: переписать/разобраться с конвертором
 сделать коллекцию единичной(приватный генератор)
 поправить дату инициализации

 */



public class Main {
    public static void main(String[] args) throws IOException {
        // Создаем объекты
        Coordinates coordinates1 = new Coordinates(10.5, -500.0f);
        Chapter chapter1 = new Chapter("Blood Angels", "Baal");
        SpaceMarine marine1 = new SpaceMarine(1, "Captain Titus", coordinates1, 100.0f, true, Weapon.BOLT_RIFLE, MeleeWeapon.CHAIN_SWORD, chapter1);

        Coordinates coordinates2 = new Coordinates(15.0, -300.0f);
        Chapter chapter2 = new Chapter("Ultramarines", "Macragge");
        SpaceMarine marine2 = new SpaceMarine(2, "Captain Sicarius", coordinates2, 95.0f, true, Weapon.COMBI_PLASMA_GUN, MeleeWeapon.POWER_SWORD, chapter2);

        // Создаем коллекцию и добавляем объекты
        SpaceMarineCollection collection = new SpaceMarineCollection();
        collection.addMarine(marine1);
        collection.addMarine(marine2);

        // Выводим коллекцию
        System.out.println(collection);

        try {
            CollectionSaverInFile.save("output.xml", collection.getMarines());
            collection.setMarines(CollectionLoaderFromFile.load("output.xml"));
            System.out.println("Коллекция успешно сохранена в файл!");
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении файла: " + e.getMessage());
        }

        System.out.println(collection);
    }
}