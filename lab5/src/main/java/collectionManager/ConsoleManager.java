package collectionManager;

import java.util.Scanner;

// https://javarush.com/quests/lectures/questsyntaxpro.level02.lecture05

public class ConsoleManager {
    private Scanner scanner;
    private SpaceMarineCollection spaceMarineCollection;

    public ConsoleManager(SpaceMarineCollection spaceMarineCollection) {
        scanner = new Scanner(System.in);
        this.spaceMarineCollection = spaceMarineCollection;
        waitInput();
    }

    public void waitInput() {
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            if (input.trim().equals("help")) {
                this.help();
            } else if (input.trim().equals("exit")) {
                break;
            } else if (input.trim().equals("show")) {
                show();
            }
        }
    }

    private void help() {
        System.out.println("""
                help : вывести справку по доступным командам
                info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)
                show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении
                add {element} : добавить новый элемент в коллекцию
                update id {element} : обновить значение элемента коллекции, id которого равен заданному
                remove_by_id id : удалить элемент из коллекции по его id
                clear : очистить коллекцию
                save : сохранить коллекцию в файл
                execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.
                exit : завершить программу (без сохранения в файл)
                remove_first : удалить первый элемент из коллекции
                remove_head : вывести первый элемент коллекции и удалить его
                remove_greater {element} : удалить из коллекции все элементы, превышающие заданный
                group_counting_by_weapon_type : сгруппировать элементы коллекции по значению поля weaponType, вывести количество элементов в каждой группе
                count_less_than_loyal loyal : вывести количество элементов, значение поля loyal которых меньше заданного
                filter_less_than_chapter chapter : вывести элементы, значение поля chapter которых меньше заданного
                """);
    }

    private void show() {
        System.out.println(spaceMarineCollection);
    }
}
