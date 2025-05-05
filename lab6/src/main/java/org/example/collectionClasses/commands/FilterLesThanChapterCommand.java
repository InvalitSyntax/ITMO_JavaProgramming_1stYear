package org.example.collectionClasses.commands;

import org.example.collectionClasses.app.AppController;
import org.example.collectionClasses.app.IOManager;
import org.example.collectionClasses.app.ModelBuilder;
import org.example.collectionClasses.model.Chapter;
import org.example.collectionClasses.model.SpaceMarine;

import java.util.ArrayDeque;

/**
 * Команда для фильтрации элементов, значение поля chapter которых меньше заданного.
 *
 * @author ISyntax
 * @version 1.0
 */
public class FilterLesThanChapterCommand extends ICommand {
    public FilterLesThanChapterCommand() {
        super();
    }

    @Override
    public void execute(AppController app, String[] args) {
        IOManager ioManager = app.getIoManager();
        Chapter newChapter = new ModelBuilder(app).buildChapter();
        ArrayDeque<SpaceMarine> marineArrayDeque = app.getSpaceMarineCollectionManager().getMarines();
        for (SpaceMarine marine : marineArrayDeque) {
            Chapter sMChapter = marine.getChapter();
            if (newChapter.compareTo(sMChapter) > 0) {
                ioManager.writeMessage(marine + "\n", false);
            }
        }
    }
}