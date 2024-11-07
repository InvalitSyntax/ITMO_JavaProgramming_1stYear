package mypokemons;
import ru.ifmo.se.pokemon.*;
import mymoves.*;

public class Cleffa extends Pokemon {
    public Cleffa(String name, int lvl){
        super(name, lvl);

        super.setType(Type.FAIRY);
        super.setStats(50, 25, 28, 45, 55, 15);

        ThunderWave thunderWave = new ThunderWave(0, 90);
        Sing sing = new Sing(0, 55);

        super.setMove(thunderWave, sing);
    }
}
