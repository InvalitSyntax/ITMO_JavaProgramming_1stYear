package commands;

import app.SpaceMarineCollectionManager;
import app.AppController;

public class InfoCommand implements Command {
    @Override
    public void execute(AppController app, String[] args) {
        SpaceMarineCollectionManager collectionManager = app.getSpaceMarineCollectionManager();
        app.getIoManager().writeMessage(collectionManager.getInfo()+"\n", false);
    }
}
