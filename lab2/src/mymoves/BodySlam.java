package mymoves;
import static utils.Util.chance;

import ru.ifmo.se.pokemon.*;

public final class BodySlam extends PhysicalMove {
    public BodySlam(double pow, double acc){
        super(Type.NORMAL, pow, acc);
    }

    @Override
    protected String describe() {
        return "использует Body Slam";
    }

    @Override
    protected void applyOppEffects(Pokemon def){
        if (chance(0.3)){
            Effect.paralyze(def);
        }
    }
}
