package interfaces;

import exeptions.IllegalArrestException;
import objects.alive.LittleMan;

public interface CanArrest {
    void arrest(LittleMan littleMan) throws IllegalArrestException;
}
