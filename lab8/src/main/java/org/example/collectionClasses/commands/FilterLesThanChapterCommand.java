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
    private Chapter chapterArg;
    public FilterLesThanChapterCommand() {
        super();
    }
    public void setChapterArg(Chapter chapter) {
        this.chapterArg = chapter;
    }
    public Chapter getChapterArg() {
        return chapterArg;
    }
    @Override
    public void execute(AppController app, String[] args) {
        IOManager ioManager = app.getIoManager();
        if (chapterArg == null) {
            ioManager.writeMessage("Chapter не задан\n", false);
            return;
        }
        app.getSpaceMarineCollectionManager().getMarines().stream()
            .filter(marine -> chapterArg.compareTo(marine.getChapter()) > 0)
            .forEach(marine -> ioManager.writeMessage(marine + "\n", false));
    }
}