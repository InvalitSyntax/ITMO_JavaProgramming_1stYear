package commands;

public class ExitCommand implements Command {
    @Override
    public void execute(String[] args) {
        System.exit(0);
    }
}
