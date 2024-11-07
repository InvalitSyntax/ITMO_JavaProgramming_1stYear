package mypokemons;
import mymoves.*;

public final class Clefable extends Clefairy {
    public Clefable(String name, int lvl){
        super(name, lvl);

        super.setStats(95, 70, 73, 95, 90, 60);

        DisarmingVoice disarmingVoice = new DisarmingVoice(40, Double.POSITIVE_INFINITY);

        super.addMove(disarmingVoice);
    }
}
