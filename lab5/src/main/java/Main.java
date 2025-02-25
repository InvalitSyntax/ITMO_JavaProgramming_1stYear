
import collectionManager.JAXBUtil;
import collectionManager.SpaceMarineCollection;
import collectionObjects.*;

import javax.xml.bind.JAXBException;
import java.io.IOException;

/*

TODO: переписать/разобраться с адаптером
 сделать коллекцию единичной(приватный генератор)

 */

public class Main {
    public static void main(String[] args) throws JAXBException {
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

        // Сохранение коллекции в XML
        collectionManager.JAXBUtil.saveCollectionToFile(collection, "collection.xml");

        // Загрузка коллекции из XML
        SpaceMarineCollection loadedCollection = collectionManager.JAXBUtil.loadCollectionFromFile("collection.xml");
        System.out.println(loadedCollection);

        System.out.println(collection);
    }
}