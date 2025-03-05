package commands;


import collection.SpaceMarineCollectionManager;
import controll.AppController;

public class ClearCommand implements Command{
    @Override
    public void execute(AppController app, String[] args) {
        SpaceMarineCollectionManager collectionManager = app.getSpaceMarineCollectionManager();
        collectionManager.clearMarines();
    }
}
