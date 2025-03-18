package app;

import commands.*;

/**
 * Контроллер приложения, управляющий выполнением команд и взаимодействием между компонентами.
 *
 * @author ISyntax
 * @version 1.0
 */
public class AppController {
    private final CommandManager commandManager;
    private SpaceMarineCollectionManager spaceMarineCollectionManager;
    private final XMLIOManager xmlioManager;
    private final IOManager ioManager;
    private boolean isTurnOn;

    /**
     * Конструктор контроллера приложения.
     *
     * @param commandManager               менеджер команд
     * @param spaceMarineCollectionManager менеджер коллекции космических десантников
     * @param xmlioManager                 менеджер ввода/вывода XML
     * @param ioManager                    менеджер ввода/вывода
     */
    public AppController(CommandManager commandManager, SpaceMarineCollectionManager spaceMarineCollectionManager, XMLIOManager xmlioManager, IOManager ioManager) {
        this.commandManager = commandManager;
        this.spaceMarineCollectionManager = spaceMarineCollectionManager;
        this.xmlioManager = xmlioManager;
        this.ioManager = ioManager;

        putCommands();
        loadModel();
        isTurnOn = true;
    }

    /**
     * Загружает модель коллекции из файла.
     */
    private void loadModel() {
        spaceMarineCollectionManager = xmlioManager.loadCollectionFromFile();
    }

    /**
     * Регистрирует команды в менеджере команд.
     */
    private void putCommands() {
        commandManager.putCommand("help", new HelpCommand());
        commandManager.putCommand("exit", new ExitCommand());
        commandManager.putCommand("show", new ShowCommand());
        commandManager.putCommand("info", new InfoCommand());
        commandManager.putCommand("clear", new ClearCommand());
        commandManager.putCommand("save", new SaveCommand());
        commandManager.putCommand("add", new AddCommand());
        commandManager.putCommand("update", new UpdateCommand());
        commandManager.putCommand("remove_by_id", new RemoveByIdCommand());
        commandManager.putCommand("remove_first", new RemoveFirstCommand());
        commandManager.putCommand("remove_head", new RemoveHeadCommand());
        commandManager.putCommand("group_counting_by_weapon_type", new CountByWeaponeTypeCommand());
        commandManager.putCommand("count_less_than_loyal", new CountLessThanLoyalCommand());
        commandManager.putCommand("filter_less_than_chapter", new FilterLesThanChapterCommand());
        commandManager.putCommand("remove_greater", new RemoveGreater());
        commandManager.putCommand("execute_script", new ExecuteScriptCommand());
    }

    /**
     * Запускает приложение.
     */
    public void run() {
        ioManager.writeMessage("Введите команду (список команд вы можете посмотреть, написав <help> и нажав Enter)\n", false);
        while (isTurnOn) {
            String input = ioManager.getRawStringInput();
            if (input == null) {
                ioManager.writeMessage("Введите команду (список команд вы можете посмотреть, написав <help> и нажав Enter)\n", false);
            } else {
                String[] tokens = input.trim().split(" ");
                try {
                    commandManager.executeCommand(this, tokens);
                } catch (Exception e) {
                    ioManager.writeMessage(e.getMessage(), true);
                }
            }
        }
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

    public IOManager getIoManager() {
        return ioManager;
    }

    public boolean isTurnOn() {
        return isTurnOn;
    }

    public void setTurnOn(boolean turnOn) {
        isTurnOn = turnOn;
    }
}