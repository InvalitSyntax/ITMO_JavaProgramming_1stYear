package commands;

import collectionManager.SpaceMarineCollection;

public class InfoCommand implements Command {
    private final SpaceMarineCollection collection;
    public InfoCommand(SpaceMarineCollection collection) {
        this.collection = collection;
    }

    @Override
    public void execute(String[] args) {
        System.out.println(collection.getInfo());
    }
}
