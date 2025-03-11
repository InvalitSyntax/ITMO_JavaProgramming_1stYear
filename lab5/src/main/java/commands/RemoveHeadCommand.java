package commands;

import app.AppController;

public class RemoveHeadCommand implements Command {
    @Override
    public void execute(AppController app, String[] args) {
        if (!app.getSpaceMarineCollectionManager().getMarines().isEmpty()) {
            app.getIoManager().writeMessage("Удален десантник\n" + app.getSpaceMarineCollectionManager().getMarines().getFirst().getId() + "\n", false);
            app.getSpaceMarineCollectionManager().getMarines().removeFirst();
        } else {
            app.getIoManager().writeMessage("Коллекция уже пустая, ничего не удалено!\n", false);
        }
    }
}