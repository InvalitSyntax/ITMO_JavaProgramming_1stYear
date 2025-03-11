package commands;

import app.SpaceMarineCollectionManager;
import app.AppController;

public class ShowCommand implements Command {
    @Override
    public void execute(AppController app, String[] args) {
        SpaceMarineCollectionManager collectionManager = app.getSpaceMarineCollectionManager();
        app.getIoManager().writeMessage(collectionManager.toString()+"\n", false);
    }
}