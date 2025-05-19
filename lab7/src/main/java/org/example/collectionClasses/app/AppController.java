package org.example.collectionClasses.app;

import org.example.collectionClasses.commands.*;

/**
 * Контроллер приложения, управляющий выполнением команд и взаимодействием между компонентами.
 *
 * @author ISyntax
 * @version 1.0
 */
public class AppController {
    private final CommandManager commandManager;
    private final IOManager ioManager;
    private final DBManager dbManager;
    private SpaceMarineCollectionManager spaceMarineCollectionManager;
    private boolean isTurnOn;

    /**
     * Конструктор контроллера приложения.
     *
     * @param commandManager               менеджер команд
     * @param spaceMarineCollectionManager менеджер коллекции космических десантников
     * @param xmlioManager                 менеджер ввода/вывода XML
     * @param ioManager                    менеджер ввода/вывода
     */
    public AppController(CommandManager commandManager, SpaceMarineCollectionManager spaceMarineCollectionManager, IOManager ioManager, DBManager dbManager) {
        this.commandManager = commandManager;
        this.spaceMarineCollectionManager = spaceMarineCollectionManager;
        this.ioManager = ioManager;
        this.dbManager = dbManager;

        putCommands();
        loadModel();
        isTurnOn = true;
    }

    /**
     * Загружает модель коллекции из файла.
     */
    public void loadModel() {
        this.spaceMarineCollectionManager = dbManager.loadCollection();
    }

    /**
     * Регистрирует команды в менеджере команд.
     */
    private void putCommands() {
        commandManager.putCommand("help", HelpCommandServer::new);
        commandManager.putCommand("show", ShowCommand::new);
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

    public DBManager getDbManager() {
        return dbManager;
    }

    public synchronized SpaceMarineCollectionManager getSpaceMarineCollectionManager() {
        return spaceMarineCollectionManager;
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