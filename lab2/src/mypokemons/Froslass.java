package mypokemons;
import ru.ifmo.se.pokemon.*;
import mymoves.*;


public final class Froslass extends Pokemon {
    public Froslass(String name, int lvl){
        super(name, lvl);

        super.setType(Type.GHOST, Type.ICE);
        super.setStats(70, 80, 70, 80, 70, 110);

        Swagger swagger = new Swagger(0, 85);
        IceBeam iceBeam = new IceBeam(90, 100);
        Blizzard blizzard = new Blizzard(110, 70);
        ThunderWave thunderWave = new ThunderWave(0, 90);

        super.setMove(iceBeam, blizzard, swagger, thunderWave);
    }
}
