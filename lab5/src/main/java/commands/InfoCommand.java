package commands;

import app.AppController;
import app.SpaceMarineCollectionManager;

/**
 * Команда для вывода информации о коллекции.
 *
 * @author ISyntax
 * @version 1.0
 */
public class InfoCommand implements Command {
    @Override
    public void execute(AppController app, String[] args) {
        SpaceMarineCollectionManager collectionManager = app.getSpaceMarineCollectionManager();
        app.getIoManager().writeMessage(collectionManager.getInfo() + "\n", false);
    }
}