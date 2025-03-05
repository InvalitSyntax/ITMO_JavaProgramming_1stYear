package commands;

import collection.SpaceMarineCollectionManager;
import controll.AppController;
import storage.XMLIOManager;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public class SaveCommand implements Command{
    @Override
    public void execute(AppController app, String[] args) {
        XMLIOManager xmlioManager = app.getXmlioManager();
        SpaceMarineCollectionManager collectionManager = app.getSpaceMarineCollectionManager();
        xmlioManager.saveCollectionToFile(collectionManager);
    }
}