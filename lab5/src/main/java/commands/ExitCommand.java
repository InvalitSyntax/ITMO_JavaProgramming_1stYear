package commands;

import app.AppController;

public class ExitCommand implements Command {
    @Override
    public void execute(AppController app, String[] args) {
        System.exit(0);
    }
}
