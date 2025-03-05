package commands;

import collectionManager.SpaceMarineCollection;

public class ShowCommand implements Command {
    private final SpaceMarineCollection collection;

    public ShowCommand(SpaceMarineCollection collection) {
        this.collection = collection;
    }

    @Override
    public void execute(String[] args) {
        System.out.println(collection);
    }
}