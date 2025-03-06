package controll;

import input.CommandManager;
import collection.SpaceMarineCollectionManager;
import storage.XMLIOManager;
import commands.*;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*TODO:
    info - поправить вывод даты
    -help : вывести справку по доступным командам
    -info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)
    -show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении
    add {element} : добавить новый элемент в коллекцию
    update id {element} : обновить значение элемента коллекции, id которого равен заданному
    remove_by_id id : удалить элемент из коллекции по его id
    -clear : очистить коллекцию
    -save : сохранить коллекцию в файл
    execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.
    -exit : завершить программу (без сохранения в файл)
    remove_first : удалить первый элемент из коллекции
    remove_head : вывести первый элемент коллекции и удалить его
    remove_greater {element} : удалить из коллекции все элементы, превышающие заданный
    group_counting_by_weapon_type : сгруппировать элементы коллекции по значению поля weaponType, вывести количество элементов в каждой группе
    count_less_than_loyal loyal : вывести количество элементов, значение поля loyal которых меньше заданного
    filter_less_than_chapter chapter : вывести элементы, значение поля chapter которых меньше заданного


*/

public class AppController {
    private CommandManager commandManager;
    private SpaceMarineCollectionManager spaceMarineCollectionManager;
    private XMLIOManager xmlioManager;

    public AppController(CommandManager commandManager, SpaceMarineCollectionManager spaceMarineCollectionManager, XMLIOManager xmlioManager) {
        this.commandManager = commandManager;
        this.spaceMarineCollectionManager = spaceMarineCollectionManager;
        this.xmlioManager = xmlioManager;

        putCommands();
        loadModel();

    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public SpaceMarineCollectionManager getSpaceMarineCollectionManager() {
        return spaceMarineCollectionManager;
    }

    public XMLIOManager getXmlioManager() {
        return xmlioManager;
    }

    private void loadModel() {
        spaceMarineCollectionManager = xmlioManager.loadCollectionFromFile();
    }

    private void putCommands() {
        commandManager.putCommand("help", new HelpCommand());
        commandManager.putCommand("exit", new ExitCommand());
        commandManager.putCommand("show", new ShowCommand());
        commandManager.putCommand("info", new InfoCommand());
        commandManager.putCommand("clear", new ClearCommand());
        commandManager.putCommand("save", new SaveCommand());
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            String[] tokens = input.trim().split(" ");
            if (tokens.length == 0){
                System.out.println("Введите команду (список команд вы можете посмотреть, написав <help> и нажав Enter)");
            } else commandManager.executeCommand(this, tokens);
        }
    }


}
