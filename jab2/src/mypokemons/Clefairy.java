package mypokemons;
import mymoves.*;

public class Clefairy extends Cleffa {
    public Clefairy(String name, int lvl){
        super(name, lvl);

        super.setStats(70, 45, 48, 60, 65, 35);

        Pound pound = new Pound(40, 100);

        super.addMove(pound);
    }
}