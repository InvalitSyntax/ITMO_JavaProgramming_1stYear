package mymoves;
import ru.ifmo.se.pokemon.*;
public final class IceShard extends PhysicalMove{
    public IceShard(double pow, double acc, int priority, int hits){
        super(Type.ICE, pow, acc, priority, hits);
    }

    @Override
    protected String describe() {
        return "использует Ice Shard";
    }
}
