package mypokemons;
import ru.ifmo.se.pokemon.*;
import mymoves.*;

public final class Snorunt extends Pokemon {
    public Snorunt(String name, int lvl){
        super(name, lvl);

        super.setType(Type.ICE);
        super.setStats(50, 50, 50, 50, 50, 50);

        Swagger swagger = new Swagger(0, 85);
        IceBeam iceBeam = new IceBeam(90, 100);
        Blizzard blizzard = new Blizzard(110, 70);

        super.setMove(swagger, iceBeam, blizzard);
    }
}
