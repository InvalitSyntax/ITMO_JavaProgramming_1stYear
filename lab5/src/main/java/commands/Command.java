package commands;
import app.AppController;

public interface Command {
    void execute(AppController app, String[] args);
}