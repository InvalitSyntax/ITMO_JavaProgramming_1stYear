package mypokemons;
import ru.ifmo.se.pokemon.*;
import mymoves.*;

public final class Lapras extends Pokemon {

    public Lapras(String name, int lvl){
        super(name, lvl);

        super.setType(Type.WATER, Type.ICE);
        super.setStats(130, 85, 80, 85, 95, 60);

        IceShard iceShard = new IceShard(40, 100, 1, 1);
        Psychic psychic = new Psychic(90, 100);
        Swagger swagger = new Swagger(0, 85);
        BodySlam bodySlam = new BodySlam(85, 100);

        super.setMove(iceShard, psychic, swagger, bodySlam);
    }
}
