import objects.World;

import static objects.Simulate.startSimulation;

public class Main {
    public static void main(String[] args) {
        World world = World.getInstance();
        world.initWorld(3, 2);

        startSimulation(world, 4);

        /*
        todo: вопросы
         */
    }
}
