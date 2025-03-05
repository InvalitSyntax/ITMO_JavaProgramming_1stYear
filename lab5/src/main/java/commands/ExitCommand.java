package commands;

import controll.AppController;

public class ExitCommand implements Command {
    @Override
    public void execute(AppController app, String[] args) {
        System.exit(0);
    }
}
