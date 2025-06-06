package org.example.collectionClasses.commands;

import org.example.collectionClasses.app.AppController;

/**
 * Команда для вывода справки по доступным командам.
 *
 * @author ISyntax
 * @version 1.0
 */
public class HelpCommandServer extends ICommand {
    public HelpCommandServer() {
        super();
    }
    @Override
    public void execute(AppController app, String[] args) {
        app.getIoManager().writeMessage("""
                Доступны следующие команды:
                    save - уже не работает - всё в бд
                    exit - завершить работу сервера, сохранив коллекцию
                    show - показать коллекцию
                    help - просмотреть доступные команды
                """, false);
    }
}