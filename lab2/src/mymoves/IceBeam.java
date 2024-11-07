package mymoves;

import ru.ifmo.se.pokemon.*;
import static utils.Util.chance;

public final class IceBeam extends SpecialMove {
    public IceBeam (double pow, double acc){
        super(Type.ICE, pow, acc);
    }

    @Override
    protected String describe() {
        return "использует Ice Beam";
    }

    @Override
    protected void applyOppEffects(Pokemon def){
        if (chance(0.1)) {
            Effect.freeze(def);
        }
    }
}
