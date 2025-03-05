package commands;

import collection.SpaceMarineCollectionManager;
import controll.AppController;

public class InfoCommand implements Command {
    @Override
    public void execute(AppController app, String[] args) {
        SpaceMarineCollectionManager collectionManager = app.getSpaceMarineCollectionManager();
        System.out.println(collectionManager.getInfo());
    }
}
