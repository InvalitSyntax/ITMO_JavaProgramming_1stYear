package commands;

import app.AppController;
import app.IOManager;
import app.ModelBuilder;
import model.Chapter;
import model.SpaceMarine;

import java.util.ArrayDeque;

public class RemoveGreater implements Command {
    //remove_greater
    @Override
    public void execute(AppController app, String[] args) {
        IOManager ioManager = app.getIoManager();
        SpaceMarine newMarine = new ModelBuilder(app).build();
        ArrayDeque<SpaceMarine> marineArrayDeque = app.getSpaceMarineCollectionManager().getMarines();
        for(SpaceMarine marine : marineArrayDeque){
            if (newMarine.compareTo(marine) < 0){
                app.getSpaceMarineCollectionManager().removeMarine(marine);
            }
        }
    }
}
