package commands;

import app.AppController;
import app.SpaceMarineCollectionManager;

/**
 * Команда для вывода всех элементов коллекции.
 *
 * @author ISyntax
 * @version 1.0
 */
public class ShowCommand implements Command {
    @Override
    public void execute(AppController app, String[] args) {
        SpaceMarineCollectionManager collectionManager = app.getSpaceMarineCollectionManager();
        app.getIoManager().writeMessage(collectionManager.toString() + "\n", false);
    }
}