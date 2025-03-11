package commands;

import app.AppController;

public class ExitCommand implements Command {
    @Override
    public void execute(AppController app, String[] args) {
        app.setTurnOn(false);
    }
}
