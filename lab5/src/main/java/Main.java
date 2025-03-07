import app.SpaceMarineCollectionManager;
import app.AppController;
import app.CommandManager;
import app.XMLIOManager;

import javax.xml.bind.JAXBException;
import java.io.IOException;

/*

TODO: переписать/разобраться с адаптером
 сделать коллекцию единичной(приватный генератор), подумать над тем, что загруженная коллекция является новой(ну и ладно)
 генерация id

 */

public class Main {
    public static void main(String[] args) throws JAXBException, IOException {
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