package mymoves;
import static utils.Util.chance;
import ru.ifmo.se.pokemon.*;

public final class Psychic extends SpecialMove{
    public Psychic(double pow, double acc){
        super(Type.PSYCHIC, pow, acc);
    }

    @Override
    protected String describe() {
        return "использует Psychic";
    }

    @Override
    protected void applyOppEffects(Pokemon def){
        if (chance(0.1)) {
            def.setMod(Stat.SPECIAL_DEFENSE, -1);
        }
    }
}
