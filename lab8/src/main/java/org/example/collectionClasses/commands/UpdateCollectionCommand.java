package org.example.collectionClasses.commands;

import org.example.collectionClasses.app.AppController;
import org.example.collectionClasses.model.SpaceMarine;
import java.util.List;

public class UpdateCollectionCommand extends ICommand {
    public UpdateCollectionCommand() {
        super();
    }

    @Override
    public void execute(AppController app, String[] args) {
        // Получаем коллекцию SpaceMarine с сервера
        List<SpaceMarine> collection = new java.util.ArrayList<>(app.getSpaceMarineCollectionManager().getMarines());
        Answer answer = new Answer("Collection updated", true);
        answer.setCollection(collection);
        this.answer = answer;
        // Возвращаем ответ через ConnectionHandler (ответ будет возвращён из processCommand)
        // Просто ничего не делаем, ответ будет возвращён как результат команды
        // Можно сохранить в поле, если нужно: this.answer = answer;
        // Но главное, чтобы Answer был возвращён из processCommand
        // Если нужно, можно добавить геттер для этого ответа
        // Например: this.answer = answer;
        // Но если ConnectionHandler возвращает результат execute, этого достаточно
        // return answer; // если сигнатура позволяет
    }
}
