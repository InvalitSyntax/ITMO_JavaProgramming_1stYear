package mymoves;
import ru.ifmo.se.pokemon.*;

public class Sing extends StatusMove {
    public Sing(double pow, double acc){
        super(Type.NORMAL, pow, acc);
    }

    @Override
    protected String describe() {
        return "использует Sing";
    }

    @Override
    protected void applyOppEffects(Pokemon def){
        Effect.sleep(def);
    }

}
