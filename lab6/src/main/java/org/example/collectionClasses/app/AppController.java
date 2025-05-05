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
    private final XMLIOManager xmlioManager;
    private final IOManager ioManager;
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
        commandManager.putCommand("save", SaveCommand::new);
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