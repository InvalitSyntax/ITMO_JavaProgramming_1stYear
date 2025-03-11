package commands;

import app.AppController;
import app.ModelBuilder;
import model.SpaceMarine;

public class AddCommand implements Command {
    @Override
    public void execute(AppController app, String[] args) {
        ModelBuilder modelBuilder = new ModelBuilder(app);
        SpaceMarine sm = modelBuilder.build();
        app.getIoManager().writeMessage("Ваш созданный и добавленный десантник:\n"+sm.toString()+"\n", false);
        app.getSpaceMarineCollectionManager().addMarine(sm);
    }
}
