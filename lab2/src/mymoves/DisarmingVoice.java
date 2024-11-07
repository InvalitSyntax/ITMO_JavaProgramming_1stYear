package mymoves;
import ru.ifmo.se.pokemon.*;

public final class DisarmingVoice extends SpecialMove{
    public DisarmingVoice (double pow, double acc){
        super(Type.FAIRY, pow, acc);
    }

    @Override
    protected String describe() {
        return "использует Disarming Voice";
    }
}