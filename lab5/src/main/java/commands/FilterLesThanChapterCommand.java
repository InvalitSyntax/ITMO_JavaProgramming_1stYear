package commands;

import app.AppController;
import app.IOManager;
import app.ModelBuilder;
import model.Chapter;
import model.SpaceMarine;
import model.Weapon;

import java.util.ArrayDeque;
import java.util.HashMap;

public class FilterLesThanChapterCommand implements Command {
    @Override
    public void execute(AppController app, String[] args) {
        IOManager ioManager = app.getIoManager();
        Chapter newChapter = new ModelBuilder(app).buildChapter();
        ArrayDeque<SpaceMarine> marineArrayDeque = app.getSpaceMarineCollectionManager().getMarines();
        for(SpaceMarine marine : marineArrayDeque){
            Chapter sMChapter = marine.getChapter();
            if (newChapter.compareTo(sMChapter) > 0){
                ioManager.writeMessage(marine.toString()+"\n", false);
            }
        }
    }
}
