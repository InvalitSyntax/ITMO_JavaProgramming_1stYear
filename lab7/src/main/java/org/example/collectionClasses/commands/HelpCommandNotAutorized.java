package org.example.collectionClasses.commands;

import org.example.collectionClasses.app.AppController;

/**
 * Команда для вывода справки по доступным командам.
 *
 * @author ISyntax
 * @version 1.0
 */
public class HelpCommandNotAutorized extends ICommand {
    public HelpCommandNotAutorized() {
        super();
    }
    @Override
    public void execute(AppController app, String[] args) {
        app.getIoManager().writeMessage("""
            Зарегистрируйтесь или авторизируйтесь с помощью команд:
                authorize <login> <password>
                register <login> <password>

                вместо <> вставьте необходимое соответственно
            Или же введите exit, что бы выйти из программы выйти из программы
            """, false);
    }
}