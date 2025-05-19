package org.example.collectionClasses.commands;

import org.example.collectionClasses.app.AppController;
import org.example.collectionClasses.app.DBManager;
import org.example.collectionClasses.app.IOManager;

public class AuthorizeCommand extends ICommand{
    public AuthorizeCommand() {
        super();
    }

    @Override
    public void execute(AppController app, String[] args) {
        IOManager ioManager = app.getIoManager();
        DBManager dbManager = app.getDbManager();
        if (args.length < 2) {
            ioManager.writeMessage("Вы не указали логин и пароль!\n", false);
            return;
        }
        this.condition = dbManager.authorize(app, args[0], args[1]);
    }
}