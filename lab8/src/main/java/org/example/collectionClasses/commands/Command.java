package org.example.collectionClasses.commands;

import org.example.collectionClasses.app.AppController;
import java.io.Serializable;

/**
 * Интерфейс для выполнения команд.
 *
 * @author ISyntax
 * @version 1.0
 */
public interface Command extends Serializable {
    /**
     * Выполняет команду.
     *
     * @param app  контроллер приложения
     * @param args аргументы команды
     */
    void execute(AppController app, String[] args);
}