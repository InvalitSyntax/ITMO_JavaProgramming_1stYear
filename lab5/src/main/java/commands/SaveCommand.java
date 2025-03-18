package commands;

import app.AppController;
import app.SpaceMarineCollectionManager;
import app.XMLIOManager;

/**
 * Команда для сохранения коллекции в файл.
 *
 * @author ISyntax
 * @version 1.0
 */
public class SaveCommand implements Command {
    @Override
    public void execute(AppController app, String[] args) {
        XMLIOManager xmlioManager = app.getXmlioManager();
        SpaceMarineCollectionManager collectionManager = app.getSpaceMarineCollectionManager();
        xmlioManager.saveCollectionToFile(collectionManager);
    }
}