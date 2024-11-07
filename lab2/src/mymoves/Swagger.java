package mymoves;
import ru.ifmo.se.pokemon.*;

public final class Swagger extends StatusMove {
    public Swagger(double pow, double acc){
        super(Type.NORMAL, pow, acc);
    }

    @Override
    protected String describe() {
        return "использует Swagger";
    }

    @Override
    protected void applyOppEffects(Pokemon def){
        def.confuse();
        def.setMod(Stat.ATTACK, 2);
    }
}
