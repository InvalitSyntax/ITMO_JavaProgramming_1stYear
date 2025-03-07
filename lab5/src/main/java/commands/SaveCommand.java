package commands;

import app.SpaceMarineCollectionManager;
import app.AppController;
import app.XMLIOManager;

public class SaveCommand implements Command{
    @Override
    public void execute(AppController app, String[] args) {
        XMLIOManager xmlioManager = app.getXmlioManager();
        SpaceMarineCollectionManager collectionManager = app.getSpaceMarineCollectionManager();
        xmlioManager.saveCollectionToFile(collectionManager);
    }
}