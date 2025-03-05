package commands;
import controll.AppController;

public interface Command {
    void execute(AppController app, String[] args);
}
