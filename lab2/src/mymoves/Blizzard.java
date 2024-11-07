package mymoves;

import static utils.Util.chance;
import ru.ifmo.se.pokemon.*;

public final class Blizzard extends SpecialMove {
    public Blizzard (double pow, double acc){
        super(Type.ICE, pow, acc);
    }

    @Override
    protected String describe() {
        return "использует Blizzard";
    }

    @Override
    protected void applyOppEffects(Pokemon def){
        if (chance(0.1)) {
            Effect.freeze(def);
        }
    }

}
