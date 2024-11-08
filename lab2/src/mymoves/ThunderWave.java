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
        if (!def.hasType(Type.ELECTRIC)) {
            Effect.paralyze(def);
        } else {
            System.out.println("Electric покемон не может быть парализован");
        }
    }
}
