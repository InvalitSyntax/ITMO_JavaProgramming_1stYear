import objects.*;
import objects.actions.Simulate;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Violation.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        World world = World.getInstance();
        world.initWorld(3, 2);

        /*System.out.println(world.getLittleManArrayList());
        System.out.println(world.getListPolicemen());
        System.out.println(world.getPersonArrayList());
        System.out.println(world.getWordArrayList());*/


        Simulate.startSimulation(world, 2);

        //анчекд исключения!!!
    }
}