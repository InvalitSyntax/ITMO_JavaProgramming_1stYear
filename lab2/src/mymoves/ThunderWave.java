package mymoves;
import ru.ifmo.se.pokemon.*;

public final class ThunderWave extends StatusMove {
    public ThunderWave(double pow, double acc){
        super(Type.ELECTRIC, pow, acc);
    }

    @Override
    protected String describe() {
        return "использует Thunder Wave";
    }

    @Override
    protected void applyOppEffects(Pokemon def){
        Effect.paralyze(def);
        new Effect().chance(0.25).turns(1).stat(Stat.ATTACK, -1);
        if (!def.hasType(Type.ELECTRIC)) {
            def.setCondition(new Effect().attack(0.25).turns(1).stat(Stat.SPEED, -2));
        }
    }
}
