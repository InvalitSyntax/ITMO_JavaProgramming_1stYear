package mymoves;
import ru.ifmo.se.pokemon.*;

public class Pound extends PhysicalMove {
    public Pound (double pow, double acc){
        super(Type.ICE, pow, acc);
    }

    @Override
    protected String describe() {
        return "использует Pound";
    }
}
