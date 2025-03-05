import collection.SpaceMarineCollectionManager;
import controll.AppController;
import input.CommandManager;
import storage.XMLIOManager;

import javax.xml.bind.JAXBException;
import java.io.IOException;

/*

TODO: переписать/разобраться с адаптером
 сделать коллекцию единичной(приватный генератор), подумать над тем, что загруженная коллекция является новой(ну и ладно)
 генерация id

 */

public class Main {
    public static void main(String[] args) throws JAXBException, IOException {
        /* // Создаем объекты
        Coordinates coordinates1 = new Coordinates(10.5, -500.0f);
        Chapter chapter1 = new Chapter("Blood Angels", "Baal");
        SpaceMarine marine1 = new SpaceMarine(1, "Captain Titus", coordinates1, 100.0f, true, Weapon.BOLT_RIFLE, MeleeWeapon.CHAIN_SWORD, chapter1);

        Coordinates coordinates2 = new Coordinates(15.0, -300.0f);
        Chapter chapter2 = new Chapter("Ultramarines", "Macragge");
        SpaceMarine marine2 = new SpaceMarine(2, "Captain Sicarius", coordinates2, 95.0f, true, Weapon.COMBI_PLASMA_GUN, MeleeWeapon.POWER_SWORD, chapter2);

        // Создаем коллекцию и добавляем объекты
        SpaceMarineCollectionManager collection = new SpaceMarineCollectionManager();
        collection.addMarine(marine1);
        collection.addMarine(marine2);

        // Выводим коллекцию
        System.out.println(collection);

        // Сохранение коллекции в XML
        XMLUtil.saveCollectionToFile(collection, "collection.xml");

        // Загрузка коллекции из XML
        collection = XMLUtil.loadCollectionFromFile("collection.xml");
        System.out.println(collection);

        // new ConsoleManager(collection);

        */

        if (args.length == 0){
            System.out.println("Для работы приложения напишите названия файла в виде:\n<name.xml>, где name - ваше желаемое название файла");
        } else {
            SpaceMarineCollectionManager spaceMarineCollectionManager = new SpaceMarineCollectionManager();
            XMLIOManager xmlioManager = new XMLIOManager(args[0]);
            CommandManager commandManager = new CommandManager();

            AppController appController = new AppController(commandManager, spaceMarineCollectionManager, xmlioManager);
            appController.run();
        }
    }
}